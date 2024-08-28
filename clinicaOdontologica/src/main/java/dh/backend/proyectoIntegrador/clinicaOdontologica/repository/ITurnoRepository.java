package dh.backend.proyectoIntegrador.clinicaOdontologica.repository;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Integer> {

}