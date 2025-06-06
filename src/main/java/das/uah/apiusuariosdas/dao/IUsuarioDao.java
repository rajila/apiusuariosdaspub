package das.uah.apiusuariosdas.dao;

import das.uah.apiusuariosdas.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioDao {
    Optional<Usuario> getByCorreoAndEstado(String eCorreo, Integer eEstado);
    Usuario save(Usuario eUsuario);
    Optional<Usuario> getById(long eId);
    Boolean existsByCorreo(String eCorreo);
    List<Usuario> getAll();
}
