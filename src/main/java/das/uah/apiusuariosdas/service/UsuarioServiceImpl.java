package das.uah.apiusuariosdas.service;

import das.uah.apiusuariosdas.dao.IUsuarioDao;
import das.uah.apiusuariosdas.dto.RegistroUsuarioDtoIn;
import das.uah.apiusuariosdas.jwt.ContextUtil;
import das.uah.apiusuariosdas.model.Rol;
import das.uah.apiusuariosdas.model.Usuario;
import das.uah.apiusuariosdas.util.ConstantsHelper;
import das.uah.apiusuariosdas.util.ErrorHelper;
import das.uah.apiusuariosdas.util.ResponseHelper;
import das.uah.apiusuariosdas.util.ValidationsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Optional<Usuario> getByCorreoAndEstadoAndPassword(String eCorreo, Integer eEstado, String password) {
        password = passwordEncoder.encode(password);
        return usuarioDao.getByCorreoAndEstadoAndPassword(eCorreo, eEstado, password);
    }

    @Override
    public Usuario getById(long eId) {
        return usuarioDao.getById(eId).orElse(null);
    }

    @Override
    public List<Usuario> getAll() {
        return usuarioDao.getAll();
    }

    @Override
    public ResponseHelper delete(long eId) {
        ResponseHelper result = new ResponseHelper();
        Usuario usuario = this.getById(eId);
        if(usuario != null) {
            long timestamp = System.currentTimeMillis();
            usuario.setEstado(0);
            usuario.setCorreo("del_" + Long.toString(timestamp) + "_" + usuario.getCorreo());
            usuarioDao.save(usuario);
        } else {
            result.setStatus(ConstantsHelper.FAILURE);
            result.getErrors().add(new ErrorHelper("entity", "Error al eliminar usuario"));
        }
        return result;
    }

    @Override
    public List<Usuario> getAllOnlyActive() {
        return usuarioDao.getAllOnlyActive();
    }

    @Override
    public Usuario getUserLogin() {
        return this.getByCorreoAndEstado(ContextUtil.getCurrentUsername(), 1).orElse(null);
    }

    @Override
    public ResponseHelper updatePerfil(RegistroUsuarioDtoIn eUsuario) {
        ResponseHelper result = new ResponseHelper();
        validatePerfil(eUsuario, result);
        if (result.getStatus().compareTo(ConstantsHelper.SUCCESS) == 0) {
            Usuario userLogin = this.getUserLogin();
            if (userLogin != null) {
                userLogin.setNombre(eUsuario.getNombre());
                userLogin.setApellido(eUsuario.getApellido());
                userLogin.setCorreo(eUsuario.getCorreo());
                if (!eUsuario.getPassword().isEmpty()) {
                    userLogin.setPassword(passwordEncoder.encode(eUsuario.getPassword()));
                }
                usuarioDao.save(userLogin);
            } else {
                result.setStatus(ConstantsHelper.FAILURE);
                result.getErrors().add(new ErrorHelper("id", "Usuario no encontrado"));
            }
        }
        return result;
    }

    @Override
    public ResponseHelper create(RegistroUsuarioDtoIn eUsuario) {
        ResponseHelper result = new ResponseHelper();

        // Rol por defecto
        String roleValue = eUsuario.getRole() != null && !eUsuario.getRole().isEmpty() ? eUsuario.getRole() : "USER";
        ResponseHelper rolRH = rolService.getByCode(roleValue);

        validateRegistro(eUsuario, result);
        if(result.getStatus().compareTo(ConstantsHelper.SUCCESS) != 0) return result;

        if (rolRH.getStatus().compareTo(ConstantsHelper.SUCCESS) == 0) {
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

            return result;
        } else {
            // Si hay errores al cargar el rol por defecto
            result.setStatus(rolRH.getStatus());
            result.getErrors().add(new ErrorHelper("rol", "El rol no existe!!"));
            result.getErrors().addAll(rolRH.getErrors());
        }
        return result;
    }

    @Override
    public ResponseHelper update(RegistroUsuarioDtoIn eUsuario) {
        ResponseHelper result = new ResponseHelper();

        // Rol por defecto
        String roleValue = eUsuario.getRole() != null && !eUsuario.getRole().isEmpty() ? eUsuario.getRole() : "USER";
        ResponseHelper rolRH = rolService.getByCode(roleValue);

        validateRegistro(eUsuario, result);
        if(result.getStatus().compareTo(ConstantsHelper.SUCCESS) != 0) return result;

        // Validación de usuario a editar
        Usuario usuario = this.getById(eUsuario.getId());
        if (usuario == null) {
            result.setStatus(ConstantsHelper.FAILURE);
            result.getErrors().add(new ErrorHelper("usuario", "El usuario no existe!!"));
            return result;
        }

        if (rolRH.getStatus().compareTo(ConstantsHelper.SUCCESS) == 0) {
            usuario.setNombre(eUsuario.getNombre());
            usuario.setApellido(eUsuario.getApellido());
            usuario.setCorreo(eUsuario.getCorreo());
            usuario.setPassword(eUsuario.getPassword());
            usuario.setEstado(1); // activo
            Rol rol = rolService.getById(rolRH.getIdData());
            usuario.getRoles().clear(); // eliminamos todos los roles existentes [SOLO UN ROL POR PERSONA]
            usuario.getRoles().add(rol);
            usuarioDao.save(usuario);
            return result;
        } else {
            // Si hay errores al cargar el rol por defecto
            result.setStatus(rolRH.getStatus());
            result.getErrors().add(new ErrorHelper("rol", "El rol no existe!!"));
            result.getErrors().addAll(rolRH.getErrors());
        }
        return result;
    }

    private void validateRegistro(RegistroUsuarioDtoIn eEntDao, ResponseHelper result) {
        Usuario user = this.getById(eEntDao.getId());
        // validate nombre
        if (eEntDao.getNombre()== null || eEntDao.getNombre().isEmpty()) {
            result.setStatus(ConstantsHelper.FAILURE);
            result.getErrors().add(new ErrorHelper("nombre","Debe ingresar un nombre"));
        }

        // validate apellido
        if (eEntDao.getApellido()== null || eEntDao.getApellido().isEmpty()) {
            result.setStatus(ConstantsHelper.FAILURE);
            result.getErrors().add(new ErrorHelper("apellido","Debe ingresar un apellido"));
        }

        // validar correo
        if(!ValidationsHelper.emailIsValid(eEntDao.getCorreo())) {
            result.setStatus(ConstantsHelper.FAILURE);
            result.getErrors().add(new ErrorHelper("correo", "Debe ingresar un correo valido"));
        }else if(user != null) {
            if (user.getCorreo().compareToIgnoreCase(eEntDao.getCorreo()) != 0 &&
                    usuarioDao.existsByCorreo(eEntDao.getCorreo())
            ) {
                result.setStatus(ConstantsHelper.FAILURE);
                result.getErrors().add(new ErrorHelper("correo", "Ya existe un usuario con ese correo"));
            }
        }else if(usuarioDao.existsByCorreo(eEntDao.getCorreo())) {
            result.setStatus(ConstantsHelper.FAILURE);
            result.getErrors().add(new ErrorHelper("correo", "Ya existe un usuario con ese correo"));
        }

        // validate password
        if((eEntDao.getPassword() == null || eEntDao.getPassword().isEmpty())) {
            result.setStatus(ConstantsHelper.FAILURE);
            result.getErrors().add(new ErrorHelper("password", "El password no puede ser vacio"));
        } else  {
            eEntDao.setPassword(passwordEncoder.encode((eEntDao.getPassword())));
        }
        result.setIdData(0);

        if (!result.getErrors().isEmpty()) result.setStatus(ConstantsHelper.FAILURE);
    }

    /**
     *
     * @param eEntDao
     * @param _result
     */
    private void validatePerfil(RegistroUsuarioDtoIn eEntDao, ResponseHelper _result) {
        Usuario userLogin = this.getUserLogin();
        // validate nombre
        if (eEntDao.getNombre()== null || eEntDao.getNombre().isEmpty()) {
            _result.setStatus(ConstantsHelper.FAILURE);
            _result.getErrors().add(new ErrorHelper("nombre","Debe ingresar un nombre"));
        }

        // validate apellido
        if (eEntDao.getApellido()== null || eEntDao.getApellido().isEmpty()) {
            _result.setStatus(ConstantsHelper.FAILURE);
            _result.getErrors().add(new ErrorHelper("apellido","Debe ingresar un apellido"));
        }

        // validar correo
        if(!ValidationsHelper.emailIsValid(eEntDao.getCorreo())) {
            _result.setStatus(ConstantsHelper.FAILURE);
            _result.getErrors().add(new ErrorHelper("correo", "Debe ingresar un correo valido"));
        }else if(userLogin != null) {
            if (userLogin.getCorreo().compareToIgnoreCase(eEntDao.getCorreo()) != 0 &&
                    usuarioDao.existsByCorreo(eEntDao.getCorreo())
            ) {
                _result.setStatus(ConstantsHelper.FAILURE);
                _result.getErrors().add(new ErrorHelper("correo", "Ya existe un usuario con ese correo"));
            }
        }else if(usuarioDao.existsByCorreo(eEntDao.getCorreo())) {
            _result.setStatus(ConstantsHelper.FAILURE);
            _result.getErrors().add(new ErrorHelper("correo", "Ya existe un usuario con ese correo"));
        }
    }
}
