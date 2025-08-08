package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;


    //Método para registrar pacientes
    @Transactional
    @PostMapping
    public void registrar(@RequestBody @Valid DatosRegistroPaciente datos) {
       repository.save(new Paciente(datos));
    }

    //Método para listar los pacientes
    @GetMapping
    public Page<DatosListaPaciente> listarPacientes(@PageableDefault(page = 0, size = 10, sort = {"nombre"} ) Pageable paginacion){
        return repository.findAllByActivoTrue(paginacion).map(DatosListaPaciente::new);
    }

    //Método para actualizar información del paciente
    @Transactional
    @PutMapping
    public void actualizarPaciente(@RequestBody @Valid DatosActualizacionPaciente datos){

        Optional<Paciente> optionalPaciente = repository.findByIdAndActivoTrue(datos.id());

        if(optionalPaciente.isPresent()){
            var paciente = optionalPaciente.get();
            paciente.actualizarInformacion(datos);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }


    }


    //Método para eliminar o deshabilitar por id
    @Transactional
    @DeleteMapping("/{id}")
    public void eliminarPaciente(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.desactivarPaciente();
    }




}
