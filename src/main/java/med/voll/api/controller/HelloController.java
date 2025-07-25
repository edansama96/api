package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//El ser una aplicación rest se utiliza la anotación RestController  si fuera una aplicación
// normal se usaria Controller
//uso de Mapping, para indicar el map de escucha
@RestController
@RequestMapping("/hello")
public class HelloController {

    // se indica el verbo de repuesta en este caso GetMapping
    @GetMapping
    //Método que acerpte un request
    public String hello(){
        return "Hello world 1";
    }

}
