package med.voll.api.domain.usuario;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Usuario")
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String contrasena;

}
