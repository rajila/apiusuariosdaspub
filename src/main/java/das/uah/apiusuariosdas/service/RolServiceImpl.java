package das.uah.apiusuariosdas.service;

import das.uah.apiusuariosdas.dao.IRolDao;
import das.uah.apiusuariosdas.model.Rol;
import das.uah.apiusuariosdas.util.ConstantsHelper;
import das.uah.apiusuariosdas.util.ErrorHelper;
import das.uah.apiusuariosdas.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements IRolService{
    private final IRolDao rolDao;

    @Autowired
    public RolServiceImpl(IRolDao rolDao) {
        this.rolDao = rolDao;
    }

    @Override
    public Rol getById(int eId) {
        return rolDao.getById(eId).orElse(null);
    }

    @Override
    public ResponseHelper getByCode(String eCode) {
        ResponseHelper _result = new ResponseHelper();
        Rol rol = rolDao.getByCode(eCode);
        if(rol == null) {
            _result.setStatus(ConstantsHelper.FAILURE);
            _result.getErrors().add(new ErrorHelper("entity", "Error al cargar rol"));
        } else _result.setIdData(Integer.parseInt(rol.getId().toString()));
        return _result;
    }

    @Override
    public List<Rol> getAll() {
        return rolDao.getAll();
    }
}