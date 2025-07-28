package med.voll.api.direccion;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor // se comento debido a que por algun motivo la creación del constructor vacio por medio de lombok no funciona
@AllArgsConstructor
@Embeddable
public class Direccion {

   private String calle;
   private String numero;
   private String complemento;
   private String barrio;
   private String ciudad;
   private String codigo_postal;
   private String estado;

   public Direccion() {



   }

   public Direccion(DatosDireccion datosDireccion) {
      this.calle = datosDireccion.calle();
      this.numero = datosDireccion.numero();
      this.complemento = datosDireccion.complemento();
      this.barrio = datosDireccion.barrio();
      this.ciudad = datosDireccion.ciudad();
      this.codigo_postal = datosDireccion.codigo_postal();
      this.estado = datosDireccion.estado();


   }
}
