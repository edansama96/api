package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DatosAutenticacion;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DatosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controlador para manejar el inicio de sesión de usuarios.
 *
 * Flujo general:
 * 1. El cliente envía credenciales (login y contraseña) en el cuerpo de la petición POST.
 * 2. Se construye un token de autenticación con esas credenciales.
 * 3. El AuthenticationManager de Spring Security valida el usuario y contraseña
 *    contra la base de datos (usando UserDetailsService y PasswordEncoder configurados).
 * 4. Si las credenciales son correctas, se devuelve una respuesta exitosa (200 OK).
 *    En una implementación más avanzada, aquí se generaría un token JWT para el cliente.
 */
@RestController // Indica que esta clase es un controlador REST (devuelve datos en JSON por defecto).
@RequestMapping("/login")// Define la URL base para este controlador: http://localhost:8080/login
public class AutenticationController {
    /**
     * AuthenticationManager es el componente central de Spring Security encargado
     * de procesar la autenticación. Verifica las credenciales recibidas usando
     * los proveedores configurados (UserDetailsService, autenticación en BD, etc.).
     */

    //mienbro del token service instnacias de Token service
    @Autowired
    private TokenService tokenService;
    //Para la validación y busqueda de que el usaurio este en la base de datos
    //se usara una clase de Spring security para realizar esto
    @Autowired
    private AuthenticationManager manager;
    /**
     * Endpoint POST para iniciar sesión.
     *
     * @param datos Objeto con login y contraseña (validado con @Valid).
     * @return 200 OK si la autenticación es exitosa, o un error 401/403 si falla.
     */

    @PostMapping
    public ResponseEntity iniciarSesion(@RequestBody  @Valid DatosAutenticacion datos){
        //varible para traducir y manejar el token

        // 1️⃣ Se crea un token de autenticación con el usuario y contraseña enviados.
        //    Este token NO es un JWT todavía, es una clase interna de Spring Security
        //    que encapsula las credenciales para que AuthenticationManager las procese.
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.login(),datos.contrasena());

        // 2️⃣ Se envía el token al AuthenticationManager para autenticarlo.
        //    Aquí Spring Security buscará el usuario en la BD y validará la contraseña.
        //Se crea una varaible para guardar la información manejado por medio de la instancia
        var autenticacion = manager.authenticate(authenticationToken);
        // 3️⃣ Si llega aquí sin excepción, significa que las credenciales son correctas.
        //    Por ahora solo respondemos con 200 OK, pero en un sistema real devolveríamos
        //    un JWT o algún otro token de sesión para el cliente.

        //variable para el manejo de la creación del token y manejar una clase dto con esa información
        var tokenJWT = tokenService.generarToken((Usuario) autenticacion.getPrincipal());
        //se devuelve el token por medio de una clase DTO
        return  ResponseEntity.ok(new DatosTokenJWT(tokenJWT));

    }
}
