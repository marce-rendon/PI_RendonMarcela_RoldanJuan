package dh.backend.proyectoIntegrador.clinicaOdontologica.repository;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {

}