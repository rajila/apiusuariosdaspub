package das.uah.apiusuariosdas.service;

import das.uah.apiusuariosdas.dto.RegistroUsuarioDtoIn;
import das.uah.apiusuariosdas.model.Usuario;
import das.uah.apiusuariosdas.util.ResponseHelper;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> getByCorreoAndEstado(String eCorreo, Integer eEstado);
    ResponseHelper create(RegistroUsuarioDtoIn eUsuario);
    ResponseHelper update(RegistroUsuarioDtoIn eUsuario);
    Usuario getById(long eId);
    Usuario getUserLogin();
    ResponseHelper updatePerfil(RegistroUsuarioDtoIn eUsuario);
    List<Usuario> getAll();
    ResponseHelper delete(long eId);
    List<Usuario> getAllOnlyActive();
}
