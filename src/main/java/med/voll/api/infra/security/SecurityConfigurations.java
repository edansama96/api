package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración para personalizar el comportamiento de Spring Security.
 *
 * Spring Security, por defecto, agrega autenticación básica y protección contra ataques CSRF.
 * Aquí deshabilitamos ciertas configuraciones por defecto y definimos un manejo de sesión
 * orientado a APIs REST (sin estado).
 */

//Anotación para indicar que la clase pertenece a la configuración
@Configuration // Indica que esta clase contiene definiciones de beans y configuraciones para el contexto de Spring.
//Anotación  para indicar que se modificara la configuración de spring security
@EnableWebSecurity // Habilita y permite sobreescribir la configuración de seguridad web de Spring Security.
public class SecurityConfigurations {
    /**
     * Define el bean SecurityFilterChain, que indica a Spring Security cómo proteger
     * las rutas HTTP y qué configuraciones de seguridad aplicar.
     *
     * @param http Objeto que permite construir y personalizar la configuración de seguridad HTTP.
     * @return Un SecurityFilterChain con las configuraciones especificadas.
     * @throws Exception Si ocurre un error durante la configuración.
     */

    //Se crea un método para sacar las confirudaciones por defecto de
    //spring security
    //Anotación para que la clase Security se carge e indiqué a spring que la puede utilizar
    @Bean // Indica a Spring que este método devuelve un objeto que debe registrarse en el contexto como bean.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        return http
                // 🔹 Deshabilita la protección CSRF (Cross-Site Request Forgery).
                //    En APIs REST basadas en tokens (JWT, OAuth2, etc.), no es necesario CSRF.
                .csrf(csrf -> csrf.disable())
                // 🔹 Configura el manejo de sesión como STATELESS.
                //    Esto significa que Spring Security NO almacenará el estado de la sesión entre peticiones.
                //    Es ideal para APIs donde cada petición lleva sus credenciales (por ejemplo, un token en el header).
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 🔹 Construye y retorna el SecurityFilterChain final.
                .build();

    }

}
