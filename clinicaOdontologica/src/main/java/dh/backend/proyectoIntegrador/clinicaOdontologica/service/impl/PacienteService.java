package dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.PacienteModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.PacienteRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.DomicilioResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.PacienteResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Domicilio;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.exception.BadRequestException;
import dh.backend.proyectoIntegrador.clinicaOdontologica.exception.ResourceNotFoundException;
import dh.backend.proyectoIntegrador.clinicaOdontologica.repository.IDomicilioRepository;
import dh.backend.proyectoIntegrador.clinicaOdontologica.repository.IPacienteRepository;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {

    static final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IDomicilioRepository domicilioRepository;

    @Autowired
    private ModelMapper modelMapper;
    // Cuando se usa @Autowired, ya no se requiere el constructo para inyectar la dependencia IPacienteRepository
    /*
    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }
    */

    @Override
    public List<PacienteResponseDto> buscarTodosLosPacientes() {
        List<Paciente> pacientesDesdeBD = pacienteRepository.findAll();
        List<PacienteResponseDto> listPacientesResponseDto = new ArrayList<>();

        for(Paciente paciente : pacientesDesdeBD){
            logger.info("buscarTodosLosPacientes -> Paciente " + paciente.getId() + " encontrado.");

            // Se arma el pacienteResponseDto desde el paciente obtenido de la base de datos
            // De forma automática con modelmapper
            PacienteResponseDto pacienteResponseDto = convertirPacienteEnPacienteResponseDto(paciente);
            logger.info("buscarTodosLosPacientes -> PacienteResponseDto: " + pacienteResponseDto);

            listPacientesResponseDto.add(pacienteResponseDto);
        }

        return listPacientesResponseDto;
    }

    @Override
    public Optional<PacienteResponseDto> buscarPacientePorId(Integer id) {
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(id);

        // Validamos que el paciente exista
        if(pacienteEncontrado.isPresent()){
            logger.info("buscarPacientePorId -> Paciente " + pacienteEncontrado.get().getId() + " encontrado.");

            // Convertimos de Entity a DTO el paciente
            PacienteResponseDto pacienteResponseDto = convertirPacienteEnPacienteResponseDto(pacienteEncontrado.get());
            logger.info("buscarPacientePorId -> PacienteResponseDto: " + pacienteResponseDto);

            return Optional.of(pacienteResponseDto);
        } else {
            logger.info("buscarPacientePorId -> El paciente " + id + " no existe en la base de datos.");
            throw new ResourceNotFoundException("El paciente " + id + " no existe en la base de datos.");
        }
    }

    @Override
    public PacienteResponseDto guardarPaciente(PacienteRequestDto pacienteRequestDto) {
        // Se arma el domicilio del paciente a persistir en la base de datos obteniendo la información desde el pacienteRequestDto
        Domicilio domicilio = Domicilio.builder()
                .calle(pacienteRequestDto.getDomicilio().getCalle())
                .numero(pacienteRequestDto.getDomicilio().getNumero())
                .localidad(pacienteRequestDto.getDomicilio().getLocalidad())
                .provincia(pacienteRequestDto.getDomicilio().getProvincia())
                .build();

        // Se arma el paciente a persistir en la base de datos obteniendo la información desde el pacienteRequestDto
        Paciente paciente = Paciente.builder()
                .nombre(pacienteRequestDto.getNombre())
                .apellido(pacienteRequestDto.getApellido())
                .dni(pacienteRequestDto.getDni())
                .fechaIngreso(LocalDate.parse(pacienteRequestDto.getFechaIngreso()))
                .domicilio(domicilio)
                .build();

        // Se guarda y obtiene el paciente persistido en la base de datos con el id generado automaticamente
        Paciente pacienteDesdeBD = pacienteRepository.save(paciente);
        logger.info("guardarPaciente -> El pacienteDesdeBD " + pacienteDesdeBD.getId() + " se guardó correctamente en la base de datos.");

        // Se arma el pacienteResponseDto desde el paciente obtenido de la base de datos
        // De forma automática con modelmapper
        PacienteResponseDto pacienteResponseDto = convertirPacienteEnPacienteResponseDto(pacienteDesdeBD);
        logger.info("guardarPaciente -> PacienteResponseDto: " + pacienteResponseDto);

        return pacienteResponseDto;
    }

    @Override
    public void modificarPaciente(PacienteModifyDto pacienteModifyDto) {
        try {
            // Validamos que exista el paciente a modificar
            Optional<PacienteResponseDto> pacienteEncontrado = buscarPacientePorId(pacienteModifyDto.getId());

            // Buscamos y validamos que existe el domicilio en la base de datos
            Optional<Domicilio> domicilio = domicilioRepository.findById(pacienteEncontrado.get().getId());
            // Seteamos los nuevos valores del domicilio
            domicilio.get().setCalle(pacienteModifyDto.getDomicilio().getCalle());
            domicilio.get().setNumero(pacienteModifyDto.getDomicilio().getNumero());
            domicilio.get().setLocalidad(pacienteModifyDto.getDomicilio().getLocalidad());
            domicilio.get().setProvincia(pacienteModifyDto.getDomicilio().getProvincia());

            // Creamos el paciente a modificar
            Paciente paciente = Paciente.builder()
                    .id(pacienteModifyDto.getId())
                    .nombre(pacienteModifyDto.getNombre())
                    .apellido(pacienteModifyDto.getApellido())
                    .dni(pacienteModifyDto.getDni())
                    .fechaIngreso(LocalDate.parse(pacienteModifyDto.getFechaIngreso()))
                    .domicilio(domicilio.get())
                    .build();

            // Se modifica el paciente en la base de datos
            pacienteRepository.save(paciente);
            logger.info("modificarPaciente -> El paciente " + paciente.getId() + " se modificó correctamente.");
        } catch (ResourceNotFoundException e){
            // Regresa al cliente el mensaje correspondiente al paciente de la ResourceNotFoundException
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public void eliminarPaciente(Integer id) {
        // Validamos que exista el paciente a eliminar
        Optional<PacienteResponseDto> pacienteEncontrado = buscarPacientePorId(id);

        // Se borra el paciente de la base de datos
        pacienteRepository.deleteById(pacienteEncontrado.get().getId());
        logger.info("eliminarTurno -> El paciente " + pacienteEncontrado.get().getId() + " se eliminó de la base de datos.");
    }

    @Override
    public List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre) {
        return pacienteRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Paciente> buscarPorUnaParteApellido(String parte){
        return pacienteRepository.buscarPorParteApellido(parte);
    }

    private PacienteResponseDto convertirPacienteEnPacienteResponseDto(Paciente paciente){
        PacienteResponseDto pacienteResponseDto = modelMapper.map(paciente, PacienteResponseDto.class);
        pacienteResponseDto.setDomicilio(modelMapper.map(paciente.getDomicilio(), DomicilioResponseDto.class));

        logger.info("convertirPacienteEnPacienteResponseDto -> El pacienteResponseDto " + pacienteResponseDto.getId() + " se armó desde el paciente obtenido de la base de datos de forma automática con modelmapper.");
        return pacienteResponseDto;
    }

    @Override
    public Paciente convertirPacienteResponseDtoEnPaciente(PacienteResponseDto pacienteResponseDto){
        Paciente paciente = modelMapper.map(pacienteResponseDto, Paciente.class);
        //paciente.setDomicilio(modelMapper.map(pacienteResponseDto.getDomicilioResponseDto(), DomicilioResponseDto.class));

        logger.info("convertirPacienteResponseDtoEnPaciente -> El paciente " + paciente.getId() + " se armó desde el paciente obtenido de la base de datos forma automática con modelmapper.");
        return paciente;
    }

}