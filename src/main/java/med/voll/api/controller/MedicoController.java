package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroMedico datos, UriComponentsBuilder uriComponentsBuilder) {
     var medico = new Medico(datos);
     repository.save(medico);
        //Se establece el uso de la instnacia de la clase  UriComponentsBuilder para entender y usar ciertos datos del servidor
     var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
     //De vovler el código, el body y el header location, cuando se devuelve los elmenetos
        //no se devuelve un jpa si no un dto
     return  ResponseEntity.created(uri).body(new DatosDetalleMedico(medico));
    }

    //Método para listar los médicos
    //Ya no se utilizara el Lista para  manejar la infromación
    // debido a que ahora lo que se hara es usar Pageable
    // lo que hace que se devuelva un elemento de Tipo page
    // el cual tendra la lista de la información y la dividira en páginas
    // se quito el tolist por que ya no se devuelve dicha información
    // y page tiene problemas con stream no funciona
    @GetMapping
    public ResponseEntity<Page<DatosListaMedico>> listarMedicos(@PageableDefault(size = 10,sort ={"nombre"}) Pageable paginacion){

        var page = repository.findAllByActivoTrue(paginacion).map(DatosListaMedico::new);

        return ResponseEntity.ok(page);
    }

    //Método para buscar por id medicos

    @GetMapping("/{id}")
    public ResponseEntity detallarMedico(@PathVariable Long id){
    var medico = repository.getReferenceById(id);
     return  ResponseEntity.ok(new DatosDetalleMedico( medico));
    }

    //Método para actualizar algunos elmentos
    @Transactional
    @PutMapping
    public ResponseEntity actualizaMedico(@RequestBody @Valid DatosActualizacinMedico datos){
            //Obtener el medico por id
        var medico = repository.getReferenceById(datos.id());
        medico.actualizarInformaciones(datos);
        return ResponseEntity.ok(new DatosDetalleMedico(medico));
    }

    //Método para eliminar un medico o deshabilitar de la base para este caso
    @Transactional
    @DeleteMapping("/{id}")
    //Se cambiara el void debido a que este siempre devuelve un código 200 ok
    // por la calse ResponseEntity que devuleve otro tipo de códigos http
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        //Eliminar el elemento de la base de datos
       //repository.deleteById(id);
        var medico = repository.getReferenceById(id);
            medico.eliminarMedico();
            // Se observara la nueva respuesta a recibir
        return ResponseEntity.noContent().build();
    }


}
