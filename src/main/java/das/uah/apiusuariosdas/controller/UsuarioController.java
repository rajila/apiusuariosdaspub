package das.uah.apiusuariosdas.controller;

import das.uah.apiusuariosdas.dto.RegistroUsuarioDtoIn;
import das.uah.apiusuariosdas.model.Usuario;
import das.uah.apiusuariosdas.service.IUsuarioService;
import das.uah.apiusuariosdas.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final IUsuarioService service;

    @Autowired
    public UsuarioController(IUsuarioService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping(value = {"", "/"})
    public ResponseEntity<ResponseHelper> create(@RequestBody RegistroUsuarioDtoIn eDataInput) {
        ResponseHelper response = service.create(eDataInput);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @PutMapping(value = {"", "/"})
    public ResponseEntity<ResponseHelper> update(@RequestBody RegistroUsuarioDtoIn eDataInput) {
        ResponseHelper response = service.update(eDataInput);
        return ResponseEntity.ok(response);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<Usuario>> getAllOnlyActive() {
        return ResponseEntity.ok(service.getAllOnlyActive());
    }

    @GetMapping({ "/all"})
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @CrossOrigin
    @GetMapping({"/{eId}"})
    public ResponseEntity<Usuario> getById(@PathVariable("eId") long eId) {
        Usuario usuario = service.getById(eId);
        return (usuario == null) ?
                ResponseEntity.notFound().build() : ResponseEntity.ok(usuario);
    }

    @CrossOrigin
    @DeleteMapping("/{eId}")
    public ResponseEntity<ResponseHelper> delete(@PathVariable("eId") long eId) {
        ResponseHelper response = service.delete(eId);
        return ResponseEntity.ok(response);
    }
}
