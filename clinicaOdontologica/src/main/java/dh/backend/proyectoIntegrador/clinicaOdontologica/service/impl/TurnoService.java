package dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.TurnoModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.TurnoRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.OdontologoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.PacienteResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.TurnoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Turno;
import dh.backend.proyectoIntegrador.clinicaOdontologica.exception.ResourceNotFoundException;
import dh.backend.proyectoIntegrador.clinicaOdontologica.repository.ITurnoRepository;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IOdontologoService;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IPacienteService;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    static final Logger logger = LoggerFactory.getLogger(TurnoService.class);
    @Autowired
    private ITurnoRepository turnoRepository;

    @Autowired
    private IPacienteService pacienteService;

    @Autowired
    private IOdontologoService odontologoService;

    @Autowired
    private ModelMapper modelMapper;

    // Cuando se usa @Autowired, ya no se requiere el constructo para inyectar la dependencia ITurnoRepository,
    // IPacienteRepository y IOdontologoService
    /*
    public TurnoService(ITurnoRepository turnoRepository, IPacienteService pacienteService, IOdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }
    */

    @Override
    public List<TurnoResponseDto> buscarTodosLosTurnos() {
        List<Turno> turnosDesdeBD = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();

        for(Turno t: turnosDesdeBD){
            logger.info("buscarTodosLosTurnos -> Turno " + t.getId() + " encontrado.");

            // Se arma el turnoResponseDto desde el turno obtenido de la base de datos
            // De forma manual
            //turnosRespuesta.add(obtenerTurnoResponse(t));
            // De forma automática con modelmapper
            turnosRespuesta.add(convertirTurnoEnResponse(t));

            //System.out.println(t.toString());
            //System.out.println(t);
        }
        return turnosRespuesta;
    }

    @Override
    public Optional<TurnoResponseDto> buscarTurnoPorId(Integer id) {
        Optional<Turno> turnoEncontrado = turnoRepository.findById(id);
        if(turnoEncontrado.isPresent()){
            logger.info("buscarTurnoPorId -> Turno " + turnoEncontrado.get().getId() + " encontrado.");
            TurnoResponseDto turnoRespuesta = convertirTurnoEnResponse(turnoEncontrado.get());
            return Optional.of(turnoRespuesta);
        } else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto){
        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoPorId(turnoRequestDto.getOdontologo_id());
        Turno turno = new Turno();
        Turno turnoDesdeBD = null;
        TurnoResponseDto turnoResponseDto = null;
        if(paciente.isPresent() && odontologo.isPresent()){
            // Se arma el turno a persistir en la base de datos obteniendo la información
            // desde el turnoRequestDto
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));

            // Se obtiene el turno persistido en la base de datos con el id generado automaticamente
            turnoDesdeBD = turnoRepository.save(turno);
            logger.info("guardarTurno -> El turnoDesdeBD " + turnoDesdeBD.getId() + " se guardó correctamente en la base de datos.");

            // Se arma el turnoResponseDto desde el turno obtenido de la base de datos
            // De forma manual
            // turnoResponseDto = obtenerTurnoResponse(turnoDesdeBD);
            // De forma automática con modelmapper
            turnoResponseDto = convertirTurnoEnResponse(turnoDesdeBD);
        }

        return turnoResponseDto;
    }

    @Override
    public void modificarTurno(TurnoModifyDto turnoModifyDto) {
        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(turnoModifyDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoPorId(turnoModifyDto.getOdontologo_id());
        if(paciente.isPresent() && odontologo.isPresent()){
            Turno turno = new Turno(
                    turnoModifyDto.getId(),
                    paciente.get(), odontologo.get(), LocalDate.parse(turnoModifyDto.getFecha())
            );
            turnoRepository.save(turno);
            logger.info("modificarTurno -> El turno " + turno.getId() + " se modificó correctamente.");
        }
    }

    @Override
    public Optional<TurnoResponseDto> buscarTurnosPorPaciente(String pacienteApellido) {
        Optional<Turno> turno = turnoRepository.buscarPorApellidoPaciente(pacienteApellido);
        TurnoResponseDto turnoParaResponder = null;
        if(turno.isPresent()) {
            turnoParaResponder = convertirTurnoEnResponse(turno.get());
        }
        return Optional.ofNullable(turnoParaResponder);
    }

    @Override
    public void eliminarTurno(Integer id){
        Optional<TurnoResponseDto> turnoEncontrado = buscarTurnoPorId(id);
        turnoRepository.deleteById(id);
        logger.info("eliminarTurno -> El turno " + id + " se eliminó de la base de datos.");
    }

    // Se arma el turnoResponseDto desde el turno obtenido de la base de datos
    // De forma manual
    private TurnoResponseDto obtenerTurnoResponse(Turno turnoDesdeBD){
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(
                turnoDesdeBD.getOdontologo().getId(), turnoDesdeBD.getOdontologo().getNroMatricula(),
                turnoDesdeBD.getOdontologo().getApellido(), turnoDesdeBD.getOdontologo().getNombre()
        );
        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(
                turnoDesdeBD.getPaciente().getId(), turnoDesdeBD.getPaciente().getApellido(),
                turnoDesdeBD.getPaciente().getNombre(), turnoDesdeBD.getPaciente().getDni()
        );
        TurnoResponseDto turnoResponseDto = new TurnoResponseDto(
                turnoDesdeBD.getId(),
                pacienteResponseDto, odontologoResponseDto,
                turnoDesdeBD.getFecha().toString()
        );
        logger.info("El turnoResponseDto " + turnoResponseDto.getId() + " se armó desde el turno obtenido de la base de datos de forma manual.");
        return turnoResponseDto;
    }

    // Se arma el turnoResponseDto desde el turno obtenido de la base de datos
    // De forma automática con modelmapper
    private TurnoResponseDto convertirTurnoEnResponse(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));

        logger.info("convertirTurnoEnResponse -> El turnoResponseDto " + turnoResponseDto.getId() + " se armó desde el turno obtenido de la base de datos forma automática con modelmapper.");
        return turnoResponseDto;
    }
}