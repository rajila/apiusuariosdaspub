package das.uah.apiusuariosdas.service;

import das.uah.apiusuariosdas.model.Rol;
import das.uah.apiusuariosdas.util.ResponseHelper;

import java.util.List;

public interface IRolService {
    Rol getById(int eId);
    ResponseHelper getByCode(String eCode);
    List<Rol> getAll();
}
