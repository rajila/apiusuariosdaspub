package das.uah.apiusuariosdas.repository;

import das.uah.apiusuariosdas.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepository extends JpaRepository<Rol, Long> {
  Rol findByCodigo(String eCodigo);
}