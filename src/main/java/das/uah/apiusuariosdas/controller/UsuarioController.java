package das.uah.apiusuariosdas.controller;

import das.uah.apiusuariosdas.model.Usuario;
import das.uah.apiusuariosdas.service.IUsuarioService;
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

    @GetMapping({"", "/"})
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @CrossOrigin
    @GetMapping({"/{eId}"})
    public ResponseEntity<String> getById(@PathVariable("eId") Integer eId) {
//        Usuario _usuario = service.getById(eId);
//        return (_usuario == null) ?
//                ResponseEntity.notFound().build() : ResponseEntity.ok(_usuario);
        return ResponseEntity.ok("busqueda x UserId");
    }

    @CrossOrigin
    @DeleteMapping("/{eId}")
    public ResponseEntity<String> delete(@PathVariable("eId") Integer eId) {
//        ResponseHelper _response = service.delete(eId);
//        return ResponseEntity.ok(_response);
        return ResponseEntity.ok("delete x UserId");
    }
}
