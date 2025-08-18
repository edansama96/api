package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


// Esta anotación marca la clase como un "Service" dentro del contexto de Spring.
// Indica que esta clase es un componente de servicio que puede ser inyectado en otros lugares con @Autowired.
@Service
public class TokenService {
        //variable para manejar un sectro del servidor para la validación
    @Value("${api.security.token.secret}")
    private  String secret;
    // Método para generar un token JWT basado en un usuario que se loguea en el sistema.
    // Recibe un objeto "Usuario" y devuelve un String con el token generado.


    //Método para generar el token una vez el usuario ingrese
    public String generarToken(Usuario usuario){
        try {
            // Se define el algoritmo de firma para el token.
            // HMAC256 es un algoritmo de hashing seguro que requiere una clave secreta (aquí "12345678").
            var algoritmo = Algorithm.HMAC256(secret);
            // Construcción del token con la librería JWT.
            return JWT.create()
                    // Define quién emite el token (issuer). Puede ser tu API, sistema o empresa.
                    .withIssuer("Api Voll.med")
                    // Define el "subject", normalmente el login/username del usuario autenticado.
                   .withSubject(usuario.getLogin())
                    // Define la fecha de expiración del token (en este caso 2 horas después de la emisión).
                   .withExpiresAt(fechaExpiracion())
                    // Finalmente firma el token con el algoritmo definido.
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            // Si ocurre un error al crear el token (por configuración incorrecta, clave inválida, etc.)
            // se lanza una RuntimeException personalizada.
            throw  new RuntimeException("Error al generar el token JWT", exception);
        }

    }

    // Método privado que calcula la fecha y hora de expiración del token.
    private Instant fechaExpiracion() {
        // Toma la fecha y hora actual (LocalDateTime.now())
        // y le suma 2 horas (.plusHours(2)).
        // Luego lo convierte a un objeto Instant teniendo en cuenta la zona horaria definida.
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-05:00")); // <- zona horaria usada en este ejemplo
    }

    //Método para obtener el usaurio que ingresa
    public  String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    // specify any specific claim validations
                    .withIssuer("Api Voll.med")
                    // reusable verifier instance
                    .build()
                    .verify(tokenJWT)
                    .getSubject();


        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            throw  new RuntimeException("Token JWT invalido o expirado!!");
        }
    }

}
