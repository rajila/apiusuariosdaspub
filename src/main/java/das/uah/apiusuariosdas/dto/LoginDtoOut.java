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
    public String status;
    public String error;
    public long id;

    public LoginDtoOut(long id, String accessToken, String nombres, String rol) {
        this.id = id;
        this.accessToken = accessToken;
        this.nombres = nombres;
        this.rol = rol;
    }
}
