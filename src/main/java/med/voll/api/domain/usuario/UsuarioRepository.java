package med.voll.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    //Método para devolver un UserDetails y recibe el elemento login
    UserDetails findByLogin(String login);
}
