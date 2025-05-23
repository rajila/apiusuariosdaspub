package das.uah.apiusuariosdas.service;

import das.uah.apiusuariosdas.dao.IUsuarioDao;
import das.uah.apiusuariosdas.dto.RegistroUsuarioDtoIn;
import das.uah.apiusuariosdas.model.Rol;
import das.uah.apiusuariosdas.model.Usuario;
import das.uah.apiusuariosdas.util.ConstantsHelper;
import das.uah.apiusuariosdas.util.ErrorHelper;
import das.uah.apiusuariosdas.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
    private final IUsuarioDao usuarioDao;
    private final IRolService rolService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(IUsuarioDao usuarioDao, IRolService rolService, PasswordEncoder passwordEncoder) {
        this.usuarioDao = usuarioDao;
        this.rolService = rolService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Usuario> getByCorreoAndEstado(String eCorreo, Integer eEstado) {
        return usuarioDao.getByCorreoAndEstado(eCorreo, eEstado);
    }

    @Override
    public ResponseHelper create(RegistroUsuarioDtoIn eUsuario) {
        ResponseHelper _result = new ResponseHelper();

        // Rol por defecto
        ResponseHelper rolRH = rolService.getByCode("USER");

        if (rolRH.getStatus().compareTo(ConstantsHelper.SUCCESS) == 0) {
            // encripta el passsword
            validateRegistro(eUsuario, _result);

            Usuario usuario = new Usuario();
            usuario.setId(null);
            usuario.setNombre(eUsuario.getNombre());
            usuario.setApellido(eUsuario.getApellido());
            usuario.setCorreo(eUsuario.getCorreo());
            usuario.setPassword(eUsuario.getPassword());
            usuario.setEstado(1); // activo
            Rol rol = rolService.getById(rolRH.getIdData());
            usuario.getRoles().add(rol);

            usuarioDao.save(usuario);

            return _result;
        } else {
            // Si hay errores al cargar el rol por defecto
            _result.setStatus(rolRH.getStatus());
            _result.getErrors().add(new ErrorHelper("rol", "El rol no existe!!"));
            _result.getErrors().addAll(rolRH.getErrors());
        }
        return _result;
    }

    private void validateRegistro(RegistroUsuarioDtoIn eEntDao, ResponseHelper _result) {
        // validate password
        if((eEntDao.getPassword() == null || eEntDao.getPassword().isEmpty())) {
            _result.setStatus(ConstantsHelper.FAILURE);
            _result.getErrors().add(new ErrorHelper("password", "El password no puede ser vacio"));
        } else  {
            eEntDao.setPassword(passwordEncoder.encode((eEntDao.getPassword())));
        }
        _result.setIdData(0);

        if (!_result.getErrors().isEmpty()) _result.setStatus(ConstantsHelper.FAILURE);
    }
}
