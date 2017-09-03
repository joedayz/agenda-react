package pe.joedayz.agenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.joedayz.agenda.dao.ReunionDAO;
import pe.joedayz.agenda.domain.Reunion;


import java.util.List;

/**
 * Meeting service.
 *
 * @author joe
 */
@Transactional
@Service("reunionService")
public class ReunionService {

    @Autowired
    private ReunionDAO dao;

    public List<Reunion> list() {
        return dao.list();
    }

    public Reunion findById(Integer id) {
        return dao.findById(id);
    }

    public Boolean saveReunion(Reunion reunion) {
        Boolean result = dao.save(reunion);
        return result;
    }

    public Boolean updateReunion(Reunion reunion) {
        Reunion entity = dao.findById(reunion.getId());
        if (entity != null) {
            entity.setNombre(reunion.getNombre());
            entity.setDescripcion(reunion.getDescripcion());
            entity.setDepartamentos(reunion.getDepartamentos());
            dao.update(entity);
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteById(Integer id) {
        Reunion entity = dao.findById(id);
        if (entity != null) {
            dao.delete(entity);
            return true;
        } else {
            return false;
        }
    }
}