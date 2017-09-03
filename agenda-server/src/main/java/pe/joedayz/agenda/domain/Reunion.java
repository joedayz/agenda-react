package pe.joedayz.agenda.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Reunion Entity.
 * 
 * @author joe
 *
 */
@Entity
@Table(name = "REUNION")

public class Reunion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="REUNION_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	private Integer id;
	
	@NotEmpty
    @Column(name="NOMBRE", nullable=false)
	private String nombre;
	
    @Column(name="DESCRIPCION")
	private String descripcion;
    

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "DEPARTAMENTO_REUNION", joinColumns = { @JoinColumn(name = "REUNION_ID") },
			inverseJoinColumns = { @JoinColumn(name = "DEPARTAMENTO_ID") })
    @Fetch (FetchMode.SELECT)
	private List<Departamento> departamentos;
	
    public Reunion() {}
    
	public Reunion(Integer id, String nombre, String descripcion, List<Departamento> departamentos) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.departamentos = departamentos;
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

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reunion reunion = (Reunion) o;

        if (id != null ? !id.equals(reunion.id) : reunion.id != null) return false;
        if (nombre != null ? !nombre.equals(reunion.nombre) : reunion.nombre != null) return false;
        if (descripcion != null ? !descripcion.equals(reunion.descripcion) : reunion.descripcion != null) return false;
        return departamentos != null ? departamentos.equals(reunion.departamentos) : reunion.departamentos == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (departamentos != null ? departamentos.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Reunion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", departamentos=" + departamentos +
                '}';
    }
}