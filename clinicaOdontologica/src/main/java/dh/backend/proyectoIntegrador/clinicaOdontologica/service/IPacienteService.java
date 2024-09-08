package dh.backend.proyectoIntegrador.clinicaOdontologica.service;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.PacienteModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.PacienteRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.PacienteResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    List<PacienteResponseDto> buscarTodosLosPacientes();

    Optional<PacienteResponseDto> buscarPacientePorId(Integer id);

    PacienteResponseDto guardarPaciente(PacienteRequestDto pacienteRequestDto);

    void modificarPaciente(PacienteModifyDto pacienteModifyDto);

    void eliminarPaciente(Integer id);

    List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre);

    List<Paciente> buscarPorUnaParteApellido(String parte);

    Paciente convertirPacienteResponseDtoEnPaciente(PacienteResponseDto pacienteResponseDto);

}