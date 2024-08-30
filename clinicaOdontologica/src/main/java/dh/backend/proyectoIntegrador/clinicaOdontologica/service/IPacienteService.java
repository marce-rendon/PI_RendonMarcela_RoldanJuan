package dh.backend.proyectoIntegrador.clinicaOdontologica.service;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    List<Paciente> buscarTodosLosPacientes();

    Optional<Paciente> buscarPacientePorId(Integer id);

    Paciente guardarPaciente(Paciente paciente);

    void modificarPaciente(Paciente paciente);

    void eliminarPaciente(Integer id);

    List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre);

    List<Paciente> buscarPorUnaParteApellido(String parte);
}