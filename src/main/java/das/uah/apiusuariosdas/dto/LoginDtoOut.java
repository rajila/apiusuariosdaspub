package das.uah.apiusuariosdas.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDtoOut {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String rol;
    private String nombres;

    public LoginDtoOut(String accessToken, String nombres, String rol) {
        this.accessToken = accessToken;
        this.nombres = nombres;
        this.rol = rol;
    }
}
