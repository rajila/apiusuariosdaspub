package das.uah.apiusuariosdas.dao;

import das.uah.apiusuariosdas.model.Rol;

import java.util.List;
import java.util.Optional;

public interface IRolDao {
    Optional<Rol> getById(long eId);
    Rol getByCode(String eCode);
    List<Rol> getAll();
}
