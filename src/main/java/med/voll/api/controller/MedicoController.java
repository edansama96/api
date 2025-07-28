package med.voll.api.controller;

import med.voll.api.medico.DatosRegistroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//indica que es una clase de control
@RestController
//Indicar la URI a manejar
@RequestMapping("/medicos")
public class MedicoController {
    //Se establece una instnacia de la  interfaz repositorio de la clase medico
   @Autowired
    private MedicoRepository repository;

    //Método para usar un verbo de http en espécifico,
    //para realizar una acción puntual
    //anotación para indicar que se registraran los medicos
    @PostMapping
    //Proceso para recibir los datos
    public void registrar(@RequestBody DatosRegistroMedico datos) {
        repository.save(new Medico(datos));

    }


}
