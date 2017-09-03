package pe.joedayz.agenda.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Empleado Entity.
 * 
 * @author joe
 *
 */
@Entity
@Table(name = "EMPLEADO")

public class Empleado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EMPLEADO_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty
    @Column(name="NOMBRE", nullable=false)
    private String nombre;
	
	@NotEmpty
    @Column(name="APELLIDO", nullable=false)
    private String apellido;
	
	@NotNull
    @Column(name="SALARIO", nullable=false)
    private Double salario;
	
    @Column(name="DEPARTAMENTO_ID")
	private Integer departamentoId;
	

    
    public Empleado() {}
    
	public Empleado(Integer id, String nombre, String apellido, Double salario, Integer departamentoId) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.salario = salario;
		this.departamentoId = departamentoId;
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Integer getDepartamentoId() {
		return departamentoId;
	}

	public void setDepartamentoId(Integer departamentoId) {
		this.departamentoId = departamentoId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Empleado empleado = (Empleado) o;

		if (id != null ? !id.equals(empleado.id) : empleado.id != null) return false;
		if (nombre != null ? !nombre.equals(empleado.nombre) : empleado.nombre != null) return false;
		if (apellido != null ? !apellido.equals(empleado.apellido) : empleado.apellido != null) return false;
		if (salario != null ? !salario.equals(empleado.salario) : empleado.salario != null) return false;
		return departamentoId != null ? departamentoId.equals(empleado.departamentoId) : empleado.departamentoId == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
		result = 31 * result + (apellido != null ? apellido.hashCode() : 0);
		result = 31 * result + (salario != null ? salario.hashCode() : 0);
		result = 31 * result + (departamentoId != null ? departamentoId.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Empleado{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", apellido='" + apellido + '\'' +
				", salario=" + salario +
				", departamentoId=" + departamentoId +
				'}';
	}
}