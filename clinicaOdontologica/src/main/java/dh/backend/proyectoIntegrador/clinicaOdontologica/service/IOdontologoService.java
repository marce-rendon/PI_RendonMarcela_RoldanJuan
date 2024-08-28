package dh.backend.proyectoIntegrador.clinicaOdontologica.service;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import java.util.List;
import java.util.Optional;

public interface IOdontologoService {

    List<Odontologo> buscarTodosLosOdontologos();

    Optional<Odontologo> buscarOdontologoPorId(Integer id);

    Odontologo guardarOdontologo(Odontologo odontologo);

    void modificarOdontologo(Odontologo odontologo);

    void eliminarOdontologo(Integer id);

}