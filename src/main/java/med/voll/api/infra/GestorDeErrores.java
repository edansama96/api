package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestorDeErrores {

//Clase para manejar errores
    //Anotación para errores
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity gestionarError404(){
        return ResponseEntity.notFound().build();
    }

    //Método para gestionar error de parametros faltantes
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity gestionarError400(MethodArgumentNotValidException ex){
        var errores = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream().map(DatosErrorValidacion::new).toList());
    }
    //Se crea un rcord para manejar un dto con los errores
    //debido a que el dto no se utilizara para otra cosa
    public record DatosErrorValidacion(String campo, String mensaje){

        public DatosErrorValidacion(FieldError error){
            this(
                    error.getField(),
                    error.getDefaultMessage()
            );
        }
    }



}
