package med.voll.api.domain.consulta.validaciones;


import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.infra.exceptions.ValidacionException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorFueraDelHorarioConsultas implements  ValidadorDeConsultas {

    public void validar (DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAntesDeAperturaClinica = fechaConsulta.getHour() < 7;
        var horarioDepuesDeCierreClinica = fechaConsulta.getHour() > 18;
        if( domingo || horarioAntesDeAperturaClinica || horarioDepuesDeCierreClinica){
            throw  new ValidacionException("Horario Seleccionado fuera del horario de atendimiento de la clínica:");

        }
    }

}
