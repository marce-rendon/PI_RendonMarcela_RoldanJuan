package dh.backend.proyectoIntegrador.clinicaOdontologica.service;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Turno;
import java.util.List;
import java.util.Optional;

public interface ITurnoService {

    List<Turno> buscarTodosLosTurnos();

    Optional<Turno> buscarTurnoPorId(Integer id);

    Turno guardarTurno(Turno turno);

    void modificarTurno(Turno turno);

    void eliminarTurno(Integer id);

}