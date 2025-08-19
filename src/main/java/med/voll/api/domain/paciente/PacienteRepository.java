package med.voll.api.domain.paciente;


import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    //Método para buscar los pacientes activos
    Page<Paciente> findAllByActivoTrue(Pageable paginacion);

    Optional<Paciente> findByIdAndActivoTrue(@NotNull Long id);



//    //Método para buscar por id y que el paciente se encuentre activo
//    Optional<Paciente> findByIdAndByActivoTrue(@NotNull Long id);
}
