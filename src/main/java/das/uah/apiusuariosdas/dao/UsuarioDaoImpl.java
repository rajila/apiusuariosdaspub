package das.uah.apiusuariosdas.dao;

import das.uah.apiusuariosdas.model.Usuario;
import das.uah.apiusuariosdas.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDaoImpl implements IUsuarioDao {
    public final IUsuarioRepository repository;

    @Autowired
    public UsuarioDaoImpl(IUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Usuario> getByCorreoAndEstado(String eCorreo, Integer eEstado) {
        return repository.findByCorreoAndEstado(eCorreo, eEstado);
    }

    @Override
    public Optional<Usuario> getByCorreoAndEstadoAndPassword(String eCorreo, Integer eEstado, String password) {
        return repository.findByCorreoAndEstadoAndPassword(eCorreo, eEstado, password);
    }

    @Override
    public Usuario save(Usuario eUsuario) {
        return repository.save(eUsuario);
    }

    @Override
    public Optional<Usuario> getById(long eId) {
        return repository.findById(eId);
    }

    @Override
    public Boolean existsByCorreo(String eCorreo) {
        return repository.existsByCorreo(eCorreo);
    }

    @Override
    public List<Usuario> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Usuario> getAllOnlyActive() {
        return repository.findAllOnlyActive();
    }
}
