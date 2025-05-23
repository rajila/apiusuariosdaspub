package das.uah.apiusuariosdas.dao;

import das.uah.apiusuariosdas.model.Usuario;

import java.util.Optional;

public interface IUsuarioDao {
    Optional<Usuario> getByCorreoAndEstado(String eCorreo, Integer eEstado);
    Usuario save(Usuario eUsuario);
}
