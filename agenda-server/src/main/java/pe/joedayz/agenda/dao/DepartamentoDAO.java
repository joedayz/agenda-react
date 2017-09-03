package pe.joedayz.agenda.dao;

import org.springframework.stereotype.Repository;


import pe.joedayz.agenda.domain.Departamento;

/**
 * Department DAO.
 * 
 * @author joe
 *
 */
@Repository("departamentoDAO")
public class DepartamentoDAO extends AbstractDao<Integer, Departamento> {
 
    public Departamento findById(Integer id) {
    	Departamento departamento = getByKey(id);
        return departamento;
    }
 
    public Boolean save(Departamento departamento) {
		Boolean result = persist(departamento);
		return result;
    }
}