package med.voll.api.infra.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       //Obtener el token del cliente, se usara un método para obtener el token del envabezado de la request
        var tokenJWT = recuperarToken(request);
        System.out.println(tokenJWT);
        //Se continua con otro filtro
        filterChain.doFilter(request,response);
    }

    private String recuperarToken(HttpServletRequest request) {
    //se onbtendra el token
        var authorizationHeader = request.getHeader("Authorization");
       if( authorizationHeader == null){
           throw  new RuntimeException("Token JWT no envíado en el encabezado Authorization");
       }
        //Se procedera a quitar el prefijo con el cual se obtiene el token
        return authorizationHeader.replace("Bearer", " ");
    }
}
