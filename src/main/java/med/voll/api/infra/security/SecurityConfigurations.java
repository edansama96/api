package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

//Instancia para establecer el orden de los filtros
    @Autowired
    private SecurityFilter securityFilter;
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
                //url disponibles y cuales no dependiendo del usauri
                .authorizeHttpRequests(requ -> {
                    //por medio de una lambda se indica que post tiene acceso para intentar ingresar
                    requ.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    //se bloquear las otras rutas o uris a menos que este autenticado
                    requ.anyRequest().authenticated();
                })
                //Se indicara que primero se use el filtro que se creo y después se use el otro filtro creado por spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                // 🔹 Construye y retorna el SecurityFilterChain final.
                .build();

    }



    /**
     * Método que expone un AuthenticationManager como un bean de Spring.
     *
     * ¿Por qué es necesario?
     * - En Spring Security, AuthenticationManager es el componente central que gestiona
     *   el proceso de autenticación (validar usuario y contraseña).
     * - Spring Boot lo crea internamente, pero si queremos inyectarlo (@Autowired) en otras
     *   clases como controladores o servicios, debemos declararlo como un bean.
     *
     * @param configuration Objeto que contiene la configuración de autenticación definida
     *                      en la aplicación (UserDetailsService, PasswordEncoder, etc.).
     * @return Una instancia de AuthenticationManager lista para usarse en cualquier parte
     *         del proyecto.
     * @throws Exception Si ocurre un problema al obtener el AuthenticationManager.
     */
        //Método que devuleva un autenticationmanager
    @Bean // Indica que este método devuelve un objeto que será administrado por el contenedor de Spring.
    public AuthenticationManager  authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Retorna el AuthenticationManager configurado internamente por Spring Security
        // a partir de la AuthenticationConfiguration.
        return configuration.getAuthenticationManager();

    }

    //Método para que spring security sepa que existe un método
    // que devulve el tipo de hashing
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
