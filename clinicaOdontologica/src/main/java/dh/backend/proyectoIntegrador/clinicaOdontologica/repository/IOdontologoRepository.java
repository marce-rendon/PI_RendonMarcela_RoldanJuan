package dh.backend.proyectoIntegrador.clinicaOdontologica.repository;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {

    List<Odontologo> findByNombreLike(String nombre);

    List<Odontologo> findByApellidoLike(String apellido);

    @Query("from Odontologo o where o.nroMatricula = :matricula")
    Odontologo buscarMatricula(String matricula);

}