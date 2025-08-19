package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteActivo implements  ValidadorDeConsultas {
    //intancias del repositorio de paciente
    @Autowired
    private PacienteRepository repository;

    public void validar (DatosReservaConsulta datos){
       var pacienteEstado = repository.findByIdAndActivoTrue(datos.idPaciente());
        if(!pacienteEstado.isPresent() ){
            throw new ValidacionException("Consulta no puede ser reservada con los pacientes excluidos");
        }
    }
}
