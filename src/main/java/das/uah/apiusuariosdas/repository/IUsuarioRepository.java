package das.uah.apiusuariosdas.repository;

import das.uah.apiusuariosdas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByCorreoAndEstado(String eEmail, Integer eEstado);
  @Query("select u from Usuario u where u.correo = ?1 and u.estado= ?2 and u.password = '?3'")
  Optional<Usuario> findByCorreoAndEstadoAndPassword(String correo, Integer estado, String password);
  Boolean existsByCorreo(String eCorreo);
  @Query("select u from Usuario u where u.estado = 1")
  List<Usuario> findAllOnlyActive();
}