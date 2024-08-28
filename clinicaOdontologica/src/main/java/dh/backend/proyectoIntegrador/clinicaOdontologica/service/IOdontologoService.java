package dh.backend.proyectoIntegrador.clinicaOdontologica.service;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import java.util.Optional;

public interface IOdontologoService {

    Optional<Odontologo> buscarOdontologoPorId(Integer id);

    Odontologo guardarOdontologo(Odontologo odontologo);

}