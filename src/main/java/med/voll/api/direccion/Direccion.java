package med.voll.api.direccion;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Direccion {

   private Long id;
   private String calle;
   private String numero;
   private String complemento;
   private String barrio;
   private String codigo_postal;
   private String estado;

}
