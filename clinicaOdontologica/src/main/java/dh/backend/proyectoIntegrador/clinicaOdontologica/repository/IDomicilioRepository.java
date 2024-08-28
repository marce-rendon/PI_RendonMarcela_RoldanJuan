package dh.backend.proyectoIntegrador.clinicaOdontologica.repository;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDomicilioRepository extends JpaRepository<Domicilio, Integer> {

}