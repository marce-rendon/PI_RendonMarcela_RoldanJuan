package dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.TurnoModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.TurnoRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.*;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Turno;
import dh.backend.proyectoIntegrador.clinicaOdontologica.exception.BadRequestException;
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
        List<TurnoResponseDto> listTurnosResponseDto = new ArrayList<>();

        for(Turno turno : turnosDesdeBD){
            logger.info("buscarTodosLosTurnos -> Turno " + turno.getId() + " encontrado.");

            // Se arma el turnoResponseDto desde el turno obtenido de la base de datos
            // De forma manual
            //turnosRespuesta.add(obtenerTurnoResponse(t));
            // De forma automática con modelmapper
            TurnoResponseDto turnoResponseDto = convertirTurnoEnTurnoResponseDto(turno);
            logger.info("buscarTodosLosTurnos -> TurnoResponseDto: " + turnoResponseDto);

            listTurnosResponseDto.add(turnoResponseDto);
        }

        return listTurnosResponseDto;
    }

    @Override
    public Optional<TurnoResponseDto> buscarTurnoPorId(Integer id) {
        Optional<Turno> turnoEncontrado = turnoRepository.findById(id);

        // Validamos que el turno exista
        if(turnoEncontrado.isPresent()){
            logger.info("buscarTurnoPorId -> Turno " + turnoEncontrado.get().getId() + " encontrado.");

            // Convertimos de Entity a DTO el turno
            TurnoResponseDto turnoResponseDto = convertirTurnoEnTurnoResponseDto(turnoEncontrado.get());
            logger.info("buscarTurnoPorId -> TurnoResponseDto: " + turnoResponseDto);

            return Optional.of(turnoResponseDto);
        } else {
            logger.info("buscarTurnoPorId -> El turno " + id + " no existe en la base de datos.");
            throw new ResourceNotFoundException("El turno " + id + " no existe en la base de datos.");
        }
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto){
        try {
            // Buscamos el paciente y el odontólogo en la base de datos
            // y validamos que existan
            Optional<PacienteResponseDto> pacienteResponseDto = pacienteService.buscarPacientePorId(turnoRequestDto.getPaciente_id());
            Optional<OdontologoResponseDto> odontologoResponseDto = odontologoService.buscarOdontologoPorId(turnoRequestDto.getOdontologo_id());

            // Convertimos de DTO a Entity el paciente y el odontólgo
            Paciente paciente = pacienteService.convertirPacienteResponseDtoEnPaciente(pacienteResponseDto.get());
            Odontologo odontologo = odontologoService.convertirOdontologoResponseDtoEnOdontologo(odontologoResponseDto.get());

            // Creamos el turno a guardar
            Turno turno = Turno.builder()
                    .paciente(paciente)
                    .odontologo(odontologo)
                    .fecha(LocalDate.parse(turnoRequestDto.getFecha()))
                    .build();

            // Se guarda y obtiene el turno persistido en la base de datos con el id generado automaticamente
            Turno turnoDesdeBD = turnoRepository.save(turno);
            logger.info("guardarTurno -> El turnoDesdeBD " + turnoDesdeBD.getId() + " se guardó correctamente en la base de datos.");

            // Se arma el turnoResponseDto desde el turno obtenido de la base de datos
            // De forma manual
            // turnoResponseDto = obtenerTurnoResponse(turnoDesdeBD);
            // De forma automática con modelmapper
            //TurnoResponseDto turnoResponseDto = null;
            TurnoResponseDto turnoResponseDto = convertirTurnoEnTurnoResponseDto(turnoDesdeBD);
            logger.info("guardarTurno -> TurnoResponseDto: " + turnoResponseDto);

            return turnoResponseDto;
        } catch (ResourceNotFoundException e){
            // Regresa al cliente el mensaje correspondiente al paciente o al odontólogo de la ResourceNotFoundException
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public void modificarTurno(TurnoModifyDto turnoModifyDto) {
        try {
            // Validamos que exista el turno a modificar
            Optional<TurnoResponseDto> turnoEncontrado = buscarTurnoPorId(turnoModifyDto.getId());

            // Buscamos y validamos que existan el paciente y el odontólogo en la base de datos
            Optional<PacienteResponseDto> pacienteResponseDto = pacienteService.buscarPacientePorId(turnoModifyDto.getPaciente_id());
            Optional<OdontologoResponseDto> odontologoResponseDto = odontologoService.buscarOdontologoPorId(turnoModifyDto.getOdontologo_id());

            // Convertimos de DTO a Entity el paciente y el odontólgo
            Paciente paciente = pacienteService.convertirPacienteResponseDtoEnPaciente(pacienteResponseDto.get());
            Odontologo odontologo = odontologoService.convertirOdontologoResponseDtoEnOdontologo(odontologoResponseDto.get());

            // Creamos el turno a modificar
            Turno turno = Turno.builder()
                    .id(turnoModifyDto.getId())
                    .paciente(paciente)
                    .odontologo(odontologo)
                    .fecha(LocalDate.parse(turnoModifyDto.getFecha()))
                    .build();

            // Se modifica el turno en la base de datos
            turnoRepository.save(turno);
            logger.info("modificarTurno -> El turno " + turno.getId() + " se modificó correctamente.");
        } catch (ResourceNotFoundException e){
            // Regresa al cliente el mensaje correspondiente al paciente o al odontólogo de la ResourceNotFoundException
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public void eliminarTurno(Integer id){
        // Validamos que exista el turno a eliminar
        Optional<TurnoResponseDto> turnoEncontrado = buscarTurnoPorId(id);

        // Se borra el turno de la base de datos
        turnoRepository.deleteById(turnoEncontrado.get().getId());
        logger.info("eliminarTurno -> El turno " + turnoEncontrado.get().getId() + " se eliminó de la base de datos.");
    }

    @Override
    public Optional<TurnoResponseDto> buscarTurnosPorPaciente(String pacienteApellido) {
        Optional<Turno> turno = turnoRepository.buscarPorApellidoPaciente(pacienteApellido);
        TurnoResponseDto turnoParaResponder = null;
        if(turno.isPresent()) {
            turnoParaResponder = convertirTurnoEnTurnoResponseDto(turno.get());
        }
        return Optional.ofNullable(turnoParaResponder);
    }

    // Se arma el turnoResponseDto desde el turno obtenido de la base de datos
    // De forma manual
    /*
    private TurnoResponseDto obtenerTurnoResponse(Turno turnoDesdeBD){
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(
                turnoDesdeBD.getOdontologo().getId(),
                turnoDesdeBD.getOdontologo().getNroMatricula(),
                turnoDesdeBD.getOdontologo().getApellido(),
                turnoDesdeBD.getOdontologo().getNombre()
        );

        DomicilioResponseDto domicilioResponseDto = new DomicilioResponseDto(
                turnoDesdeBD.getPaciente().getDomicilio().getId(),
                turnoDesdeBD.getPaciente().getDomicilio().getCalle(),
                turnoDesdeBD.getPaciente().getDomicilio().getNumero(),
                turnoDesdeBD.getPaciente().getDomicilio().getLocalidad(),
                turnoDesdeBD.getPaciente().getDomicilio().getProvincia()
        );

        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(
                turnoDesdeBD.getPaciente().getId(),
                turnoDesdeBD.getPaciente().getApellido(),
                turnoDesdeBD.getPaciente().getNombre(),
                turnoDesdeBD.getPaciente().getDni(),
                domicilioResponseDto,
                turnoDesdeBD.getPaciente().getTurnoSet()
        );

        TurnoResponseDto turnoResponseDto = new TurnoResponseDto(
                turnoDesdeBD.getId(),
                pacienteResponseDto,
                odontologoResponseDto,
                turnoDesdeBD.getFecha().toString()
        );

        logger.info("El turnoResponseDto " + turnoResponseDto.getId() + " se armó desde el turno obtenido de la base de datos de forma manual.");
        return turnoResponseDto;
    }
    */

    // Se arma el turnoResponseDto desde el turno obtenido de la base de datos
    // De forma automática con modelmapper
    private TurnoResponseDto convertirTurnoEnTurnoResponseDto(Turno turno){
        // Obtengo el paciente del turno
        Paciente paciente = turno.getPaciente();
        // Convierto el domicilio del paciente en domicilioResponseDto
        DomicilioResponseDto domicilioResponseDto = modelMapper.map(paciente.getDomicilio(), DomicilioResponseDto.class);

        // Convierto el paciente en pacienteResponseDto
        PacienteResponseDto pacienteResponseDto = modelMapper.map(paciente, PacienteResponseDto.class);
        // Seteo el domicilioResponseDto al pacienteResponseDto
        pacienteResponseDto.setDomicilio(domicilioResponseDto);

        // Obtengo el odontólogo del turno
        Odontologo odontologo = turno.getOdontologo();
        // Convierto el odontologo en odontologoResponseDto
        OdontologoResponseDto odontologoResponseDto = modelMapper.map(odontologo, OdontologoResponseDto.class);

        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setPacienteResponseDto(pacienteResponseDto);
        turnoResponseDto.setOdontologoResponseDto(odontologoResponseDto);

        logger.info("convertirTurnoEnTurnoResponseDto -> El turnoResponseDto " + turnoResponseDto.getId() + " se armó desde el turno obtenido de la base de datos forma automática con modelmapper.");
        return turnoResponseDto;
    }

}