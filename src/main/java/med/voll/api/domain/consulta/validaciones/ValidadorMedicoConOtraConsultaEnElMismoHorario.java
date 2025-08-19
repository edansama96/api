package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConOtraConsultaEnElMismoHorario implements  ValidadorDeConsultas {
    @Autowired
    private ConsultaRepository repository;
    public void validar (DatosReservaConsulta datos){
        var medicoTieneOtraConsultaEnElMismoHorario = repository.existsByMedicoIdAndFecha(datos.idMedico(),datos.fecha());
        if(medicoTieneOtraConsultaEnElMismoHorario){
            throw  new ValidacionException("Medioco ya tiene otra consulta en esa misma fecha y hora");
        }

    }
}
