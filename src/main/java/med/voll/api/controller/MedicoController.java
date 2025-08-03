package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DatosListaMedico;
import med.voll.api.medico.DatosRegistroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//indica que es una clase de control
@RestController
//Indicar la URI a manejar
@RequestMapping("/medicos")
public class MedicoController {
    //Se establece una instnacia de la  interfaz repositorio de la clase medico
   @Autowired
    private MedicoRepository repository;

   //Anotación para realizar modificaciones a la base de datos
    @Transactional
    //Método para usar un verbo de http en espécifico,
    //para realizar una acción puntual
    //anotación para indicar que se registraran los medicos
    @PostMapping
    //Proceso para recibir los datos
    public void registrar(@RequestBody @Valid DatosRegistroMedico datos) {
        repository.save(new Medico(datos));

    }

    //Método para listar los médicos
    //Ya no se utilizara el Lista para  manejar la infromación
    // debido a que ahora lo que se hara es usar Pageable
    // lo que hace que se devuelva un elemento de Tipo page
    // el cual tendra la lista de la información y la dividira en páginas
    @GetMapping
    public Page<DatosListaMedico> listarMedicos(Pageable paginacion){

        return repository.findAll(paginacion).stream().map( DatosListaMedico:: new).toList();
    }


}
