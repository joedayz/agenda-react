package pe.joedayz.agenda.dao;

import org.springframework.stereotype.Repository;

import pe.joedayz.agenda.domain.Empleado;

/**
 * Empleado DAO.
 * 
 * @author joe
 *
 */
@Repository("empleadoDAO")
public class EmpleadoDAO extends AbstractDao<Integer, Empleado> {
 
    public Empleado findById(Integer id) {
    	Empleado empleado = getByKey(id);
        return empleado;
    }
 
    public Boolean save(Empleado empleado) {
		Boolean result = persist(empleado);
		return result;
    }
}