package med.voll.api.infra.exceptions;

public class ValidacionException extends RuntimeException{

    public ValidacionException(String mensaje) {
        super(mensaje);
    }

}
