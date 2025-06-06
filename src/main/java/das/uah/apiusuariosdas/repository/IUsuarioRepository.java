package das.uah.apiusuariosdas.repository;

import das.uah.apiusuariosdas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByCorreoAndEstado(String eEmail, Integer eEstado);
  Boolean existsByCorreo(String eCorreo);
}