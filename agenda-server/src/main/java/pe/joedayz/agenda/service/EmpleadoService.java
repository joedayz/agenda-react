package pe.joedayz.agenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.joedayz.agenda.dao.EmpleadoDAO;
import pe.joedayz.agenda.domain.Empleado;

/**
 * Empleado service.
 * 
 * @author joe
 *
 */
@Transactional
@Service("empleadoService")
public class EmpleadoService {
 
    @Autowired
    private EmpleadoDAO dao;
 
    public List<Empleado> list() {
        return dao.list();
    }
    
    public Empleado findById(Integer id) {
        return dao.findById(id);
    }
 
    public Boolean saveEmpleado(Empleado empleado) {
        Boolean result = dao.save(empleado);
        return result;
    }
 
    public Boolean updateEmpleado(Empleado empleado) {
    	Empleado entity = dao.findById(empleado.getId());
        if(entity != null){
            entity.setNombre(empleado.getNombre());
            entity.setApellido(empleado.getApellido());
            entity.setSalario(empleado.getSalario());
            entity.setDepartamentoId(empleado.getDepartamentoId());
            dao.update(entity);
            return true;
        }else{
        	return false;
        }
    }
    
    public Boolean deleteById(Integer id) {
    	Empleado entity = dao.findById(id);
        if(entity != null){
        	dao.delete(entity);
        	return true;
        }else{
        	return false;
        }    	
    }
}