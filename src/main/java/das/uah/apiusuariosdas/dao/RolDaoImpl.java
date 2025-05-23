package das.uah.apiusuariosdas.dao;

import das.uah.apiusuariosdas.model.Rol;
import das.uah.apiusuariosdas.repository.IRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RolDaoImpl implements IRolDao{
    private final IRolRepository repository;

    @Autowired
    public RolDaoImpl(IRolRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Rol> getById(long eId) {
        return repository.findById(eId);
    }

    @Override
    public Rol getByCode(String eCode) {
        return repository.findByCodigo(eCode);
    }

    @Override
    public List<Rol> getAll() {
        return repository.findAll();
    }
}