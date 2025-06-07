package das.uah.apiusuariosdas.controller;

import das.uah.apiusuariosdas.dto.LoginDtoIn;
import das.uah.apiusuariosdas.dto.LoginDtoOut;
import das.uah.apiusuariosdas.dto.RegistroUsuarioDtoIn;
import das.uah.apiusuariosdas.jwt.GeneratorJwt;
import das.uah.apiusuariosdas.jwt.UserLoginJwt;
import das.uah.apiusuariosdas.model.Usuario;
import das.uah.apiusuariosdas.service.IUsuarioService;
import das.uah.apiusuariosdas.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final GeneratorJwt generatorJwt;
    private final IUsuarioService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          GeneratorJwt generatorJwt,
                          IUsuarioService userService) {
        this.authenticationManager = authenticationManager;
        this.generatorJwt = generatorJwt;
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<String> getAll() {
        return ResponseEntity.ok("Hola desde api usuariosdas...");
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<LoginDtoOut> login(@RequestBody LoginDtoIn loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserLoginJwt user = (UserLoginJwt) authentication.getPrincipal();
        String token = generatorJwt.generateToken(authentication);
        return new ResponseEntity<>(new LoginDtoOut(token, user.getNombres(), user.getRol()), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/registro")
    public ResponseEntity<ResponseHelper> register(@RequestBody RegistroUsuarioDtoIn eUsuarioDtoIn) {
        ResponseHelper response = userService.create(eUsuarioDtoIn);
        if (response != null) return ResponseEntity.ok(response);
        return new ResponseEntity<>(null, HttpStatus.FAILED_DEPENDENCY);
    }

    @CrossOrigin
    @GetMapping( "/perfil")
    public ResponseEntity<Usuario> perfil() {
        Usuario usuario = userService.getUserLogin();
        return (usuario == null) ?
                ResponseEntity.notFound().build() : ResponseEntity.ok(usuario);
    }

    @CrossOrigin
    @PostMapping( "/perfil")
    public ResponseEntity<ResponseHelper> perfilUpdate(@RequestBody RegistroUsuarioDtoIn eUsuarioDtoIn) {
        ResponseHelper response = userService.updatePerfil(eUsuarioDtoIn);
        if (response != null) return ResponseEntity.ok(response);
        return new ResponseEntity<>(null, HttpStatus.FAILED_DEPENDENCY);
    }

    @CrossOrigin
    @DeleteMapping("/perfil")
    public ResponseEntity<ResponseHelper> deletePerfil() {
        Usuario usuarioLogin = userService.getUserLogin();
        ResponseHelper response = userService.delete(usuarioLogin.getId());
        return ResponseEntity.ok(response);
    }
}
