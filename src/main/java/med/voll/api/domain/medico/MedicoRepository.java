package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MedicoRepository  extends JpaRepository<Medico , Long> {

        //Método para buscar por elementos activos
    Page<Medico> findAllByActivoTrue(Pageable paginacion);
}
