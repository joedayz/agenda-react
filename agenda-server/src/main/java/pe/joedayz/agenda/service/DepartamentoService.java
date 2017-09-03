package pe.joedayz.agenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.joedayz.agenda.dao.DepartamentoDAO;
import pe.joedayz.agenda.domain.Departamento;


/**
 * Departamento service.
 * 
 * @author joe
 *
 */
@Transactional
@Service("departamentoService")
public class DepartamentoService {
 
    @Autowired
    private DepartamentoDAO dao;
 
    public List<Departamento> list() {
        return dao.list();
    }    
    
    public Departamento findById(Integer id) {
        return dao.findById(id);
    }
 
    public Boolean saveDepartamento(Departamento departmento) {
        Boolean result = dao.save(departmento);
        return result;
    }
 
    public Boolean updateDepartamento(Departamento departamento) {
    	Departamento entity = dao.findById(departamento.getId());
        if(entity != null){
        	entity.setNombre(departamento.getNombre());
        	entity.setDescripcion(departamento.getDescripcion());
        	entity.setEmpleados(departamento.getEmpleados());

        	dao.update(entity);
        	return true;
        }else{
        	return false;
        }
    }
    
    public Boolean deleteById(Integer id) {
    	Departamento entity = dao.findById(id);
        if(entity != null){
        	dao.delete(entity);
        	return true;
        }else{
        	return false;
        }
    }
}