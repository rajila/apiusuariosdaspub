package das.uah.apiusuariosdas.controller;

import das.uah.apiusuariosdas.dto.RegistroUsuarioDtoIn;
import das.uah.apiusuariosdas.model.Usuario;
import das.uah.apiusuariosdas.service.IUsuarioService;
import das.uah.apiusuariosdas.util.ResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios [ADMIN]")
public class UsuarioController {
    private final IUsuarioService service;

    @Autowired
    public UsuarioController(IUsuarioService service) {
        this.service = service;
    }

    @Operation(
            summary = "Creación de usuario",
            description = "Permite crear un usuario",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @CrossOrigin
    @PostMapping(value = {""})
    public ResponseEntity<ResponseHelper> create(@RequestBody RegistroUsuarioDtoIn eDataInput) {
        ResponseHelper response = service.create(eDataInput);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Actualización de usuario",
            description = "Permite actualizar un usuario",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @CrossOrigin
    @PutMapping(value = {""})
    public ResponseEntity<ResponseHelper> update(@RequestBody RegistroUsuarioDtoIn eDataInput) {
        ResponseHelper response = service.update(eDataInput);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtiene un listado de los usuarios activos",
            description = "Obtiene un listado de los usuarios activos [estado = 1]",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping({""})
    public ResponseEntity<List<Usuario>> getAllOnlyActive() {
        return ResponseEntity.ok(service.getAllOnlyActive());
    }

    @Operation(
            summary = "Obtiene un listado de los usuarios del sistema",
            description = "Obtiene un listado de los usuarios del sistema [estado = 1 | 0 ]",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping({ "/all"})
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(
            summary = "Obtiene un usuario por ID",
            description = "Obtiene un usuario por ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @CrossOrigin
    @GetMapping({"/{eId}"})
    public ResponseEntity<Usuario> getById(@PathVariable("eId") long eId) {
        Usuario usuario = service.getById(eId);
        return (usuario == null) ?
                ResponseEntity.notFound().build() : ResponseEntity.ok(usuario);
    }

    @Operation(
            summary = "Elimina un usuario por ID",
            description = "Elimina un usuario por ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @CrossOrigin
    @DeleteMapping("/{eId}")
    public ResponseEntity<ResponseHelper> delete(@PathVariable("eId") long eId) {
        ResponseHelper response = service.delete(eId);
        return ResponseEntity.ok(response);
    }
}
