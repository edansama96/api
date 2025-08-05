package med.voll.api.medico;




//Nuevo DTO para realizar la lista de los médicos
public record DatosListaMedico(
        Long id,
        String nombre,
        String email,
        String documento,
        Especialidad especialidad,
        String calle,
        String numero,
        String complemento,
        String barrio,
        String ciudad,
        String codigo_postal,
        String estado
) {

    public DatosListaMedico(Medico medico) {
        this(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getDocumento(),
                medico.getEspecialidad(),
                medico.getDireccion().getCalle(),
                medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento(),
                medico.getDireccion().getBarrio(),
                medico.getDireccion().getCiudad(),
                medico.getDireccion().getCodigo_postal(),
                medico.getDireccion().getEstado());
    }

}
