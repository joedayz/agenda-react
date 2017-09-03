package pe.joedayz.agenda.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Departamento Entity.
 * 
 * @author joe
 *
 */
@Entity
@Table(name = "DEPARTAMENTO")

public class Departamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="DEPARTAMENTO_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@NotEmpty
    @Column(name="NOMBRE", nullable=false)
	private String nombre;
	
    @Column(name="DESCRIPCION")
	private String descripcion;
	
    @OneToMany(mappedBy = "departamentoId", fetch = FetchType.EAGER)

    @Fetch(value = FetchMode.SELECT)    
	private List<Empleado> empleados;
	

    public Departamento() {}
    
	public Departamento(Integer id, String nombre, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Departamento that = (Departamento) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        return empleados != null ? empleados.equals(that.empleados) : that.empleados == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (empleados != null ? empleados.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", empleados=" + empleados +
                '}';
    }
}