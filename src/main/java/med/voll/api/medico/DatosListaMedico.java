package med.voll.api.medico;




//Nuevo DTO para realizar la lista de los médicos
public record DatosListaMedico(
        String nombre,
        String email,
        String documento,
        Especialidad especialidad
) {

    public DatosListaMedico(Medico medico) {
        this(medico.getNombre(), medico.getEmail(), medico.getDocumento(), medico.getEspecialidad());
    }

}
