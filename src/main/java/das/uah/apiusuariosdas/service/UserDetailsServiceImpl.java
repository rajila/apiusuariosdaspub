package das.uah.apiusuariosdas.service;

import das.uah.apiusuariosdas.jwt.UserLoginJwt;
import das.uah.apiusuariosdas.model.Rol;
import das.uah.apiusuariosdas.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUsuarioService service;

    @Autowired
    public UserDetailsServiceImpl(IUsuarioService service){
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = service.getByCorreoAndEstado(username,1).orElseThrow(() -> new UsernameNotFoundException("Username or Email not found"));
        return new UserLoginJwt(user.getCorreo(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles().stream().toList()),
                "",
                user.getNombre() + " " + user.getApellido(),
                !user.getRoles().isEmpty() ? user.getRoles().stream().toList().getFirst().getCodigo() : "",
                user.getCorreo()
        );
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Rol> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getCodigo())).collect(Collectors.toList());
    }
}