package dh.backend.proyectoIntegrador.clinicaOdontologica.service;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.TurnoModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.TurnoRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.TurnoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Turno;
import java.util.List;
import java.util.Optional;

public interface ITurnoService {

    List<TurnoResponseDto> buscarTodosLosTurnos();

    Optional<Turno> buscarTurnoPorId(Integer id);

    TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto);

    void modificarTurno(TurnoModifyDto turnoModifyDto);

    void eliminarTurno(Integer id);

    Optional<Turno> buscarTurnosPorPaciente(String pacienteApellido);
}