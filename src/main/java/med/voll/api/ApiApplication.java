package med.voll.api;

import jakarta.validation.Valid;
import med.voll.api.paciente.DatosActualizacionPaciente;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;

//Clase de funcionamiento del proyecto
@SpringBootApplication
public class ApiApplication {
//Método para inicializar el proyecto
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}


}
