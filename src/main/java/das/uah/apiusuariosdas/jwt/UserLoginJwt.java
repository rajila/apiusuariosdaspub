package das.uah.apiusuariosdas.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserLoginJwt extends User {
    private String token;
    private String nombres;
    private String rol;
    private String correo;

    public UserLoginJwt(String username, String password,
                        Collection<? extends GrantedAuthority> authorities,
                        String token, String nombres, String rol,
                        String correo) {
        super(username, password, authorities);
        this.token = token;
        this.nombres = nombres;
        this.rol = rol;
        this.correo = correo;
    }

    public UserLoginJwt(String username, String password, boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities, String token,
                        String nombres, String rol,
                        String correo) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.token = token;
        this.nombres = nombres;
        this.rol = rol;
        this.correo = correo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}