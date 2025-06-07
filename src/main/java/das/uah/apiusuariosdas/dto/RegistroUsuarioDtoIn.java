package das.uah.apiusuariosdas.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroUsuarioDtoIn {
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private long id;
    private String role;
}
