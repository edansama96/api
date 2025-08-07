package med.voll.api.medico;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.direccion.DatosDireccion;

public record DatosActualizacinMedico(
        @NotNull  Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion

) {
}
