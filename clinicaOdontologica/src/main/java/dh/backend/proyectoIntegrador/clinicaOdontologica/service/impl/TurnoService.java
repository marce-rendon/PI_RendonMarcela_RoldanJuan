package dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Turno;
import dh.backend.proyectoIntegrador.clinicaOdontologica.repository.ITurnoRepository;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IOdontologoService;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IPacienteService;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.ITurnoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private ITurnoRepository turnoRepository;
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;

    public TurnoService(ITurnoRepository turnoRepository, IPacienteService pacienteService, IOdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public List<Turno> buscarTodosLosTurnos() {
        return turnoRepository.findAll();
    }

    @Override
    public Optional<Turno> buscarTurnoPorId(Integer id) {
        return turnoRepository.findById(id);
    }

    @Override
    public Turno guardarTurno(Turno turno){
        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());
        Turno turnoARetornar = null;
        if(paciente.isPresent() && odontologo.isPresent()){
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turnoARetornar = turnoRepository.save(turno);
        }
        return turnoARetornar;
    }

    @Override
    public void modificarTurno(Turno turno) {
        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());
        if(paciente.isPresent() && odontologo.isPresent()){
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turnoRepository.save(turno);
        }
    }

    @Override
    public void eliminarTurno(Integer id){
        turnoRepository.deleteById(id);
    }

}