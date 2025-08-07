package med.voll.api.paciente;

import med.voll.api.medico.Especialidad;

public record DatosListaPaciente(
        Long id,
        String nombre,
        String email,
        String  documento_identidad ,
        String telefono,
        String calle,
        String numero,
        String complemento,
        String barrio,
        String ciudad,
        String codigo_postal,
        String estado
) {

    public DatosListaPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getDocumento_identidad(),
                paciente.getTelefono(),
                paciente.getDireccion().getCalle(),
                paciente.getDireccion().getNumero(),
                paciente.getDireccion().getComplemento(),
                paciente.getDireccion().getBarrio(),
                paciente.getDireccion().getCiudad(),
                paciente.getDireccion().getCodigo_postal(),
                paciente.getDireccion().getEstado()
        );

    }
}
