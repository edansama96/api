package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.infra.exceptions.ValidacionException;
import org.springframework.stereotype.Component;


import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorConsultaConAnticipacion implements  ValidadorDeConsultas {

    public void validar (DatosReservaConsulta datos){
       var fechaConsulta = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaEnMinutos = Duration.between( ahora , fechaConsulta).toMinutes();
        if(diferenciaEnMinutos < 30){
            throw new ValidacionException("Horario seleccionado menor que 30 minutos de antelación");

        }
    }
}
