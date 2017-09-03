package pe.joedayz.agenda.dao;

import org.springframework.stereotype.Repository;


import pe.joedayz.agenda.domain.Reunion;

/**
 * Reunion DAO.
 * 
 * @author joe
 *
 */
@Repository("reunionDAO")
public class ReunionDAO extends AbstractDao<Integer, Reunion> {
 
    public Reunion findById(Integer id) {
        Reunion reunion = getByKey(id);
        return reunion;
    }
 
    public Boolean save(Reunion reunion) {
		Boolean result = persist(reunion);
		return result;
    }
}