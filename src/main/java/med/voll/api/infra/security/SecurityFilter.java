package med.voll.api.infra.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    //Se creara una instnacia del repositorio para poder obtener el usario que intenta ingresar
    @Autowired
    private UsuarioRepository repository;

    //instancia de la clase de token que se creo
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       //Obtener el token del cliente, se usara un método para obtener el token del envabezado de la request
        var tokenJWT = recuperarToken(request);
        System.out.println("hace algo: "+tokenJWT);
        System.out.println("el token service es: " + tokenService);
        //sE UTILIZARA el token service creado
        if(tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            System.out.println("hola " + subject);
            var usuario = repository.findByLogin(subject);
            //obtener el authentication
            var authentication = new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities());
            // se autenticara el usuario
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("usuario logueado!!");
        }
        filterChain.doFilter(request,response);
    }

    private String recuperarToken(HttpServletRequest request) {
    //se onbtendra el token
        var authorizationHeader = request.getHeader("Authorization");
       if( authorizationHeader != null ){
           //Se procedera a quitar el prefijo con el cual se obtiene el token
           return authorizationHeader.substring(7);
       }
       return null;

    }
}
