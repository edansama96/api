package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;


    //Método para registrar pacientes
    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroPaciente datos, UriComponentsBuilder uriComponentsBuilder) {
       var paciente =  new Paciente(datos);
        repository.save(paciente);
        //Se establece el uso de la instnacia de la clase  UriComponentsBuilder para entender y usar ciertos datos del servidor
        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        //se devuleve el elemento guarado en donde el emeneto es DTO y no jpa
        return  ResponseEntity.created(uri).body(new DatosDetallePaciente(paciente));

    }

    //Método para listar los pacientes
    @GetMapping
    public ResponseEntity <Page<DatosListaPaciente>> listarPacientes(@PageableDefault(page = 0, size = 10, sort = {"nombre"} ) Pageable paginacion){
        var paciente = repository.findAllByActivoTrue(paginacion).map(DatosListaPaciente::new);

        return ResponseEntity.ok(paciente);
    }

    //Método para buscar Paciente por id
    @GetMapping("/{id}")
    public ResponseEntity detallePaciente(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        return  ResponseEntity.ok(new DatosDetallePaciente(paciente));
    }


    //Método para actualizar información del paciente
    @Transactional
    @PutMapping
    public ResponseEntity actualizarPaciente(@RequestBody @Valid DatosActualizacionPaciente datos){

        Optional<Paciente> optionalPaciente = repository.findByIdAndActivoTrue(datos.id());

        if(optionalPaciente.isPresent()){
            var paciente = optionalPaciente.get();
            paciente.actualizarInformacion(datos);
            return ResponseEntity.ok(new DatosDetallePaciente(paciente));
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }


    }


    //Método para eliminar o deshabilitar por id
    //Se procede a usar el ResponseEntity para obener un código de acuerdo a lo que se debe obtener
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.desactivarPaciente();

        return  ResponseEntity.noContent().build();
    }




}
