package dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.DomicilioResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.PacienteResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.TurnoPacienteResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.TurnoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Turno;
import dh.backend.proyectoIntegrador.clinicaOdontologica.exception.ResourceNotFoundException;
import dh.backend.proyectoIntegrador.clinicaOdontologica.repository.IPacienteRepository;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {

    static final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    @Autowired
    private IPacienteRepository pacienteRepository;

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

            // Se arma el pacienteResponseDto desde el turno obtenido de la base de datos
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

        if(pacienteEncontrado.isPresent()){
            logger.info("buscarPacientePorId -> Paciente " + pacienteEncontrado.get().getId() + " encontrado.");

            PacienteResponseDto pacienteResponseDto = convertirPacienteEnPacienteResponseDto(pacienteEncontrado.get());
            logger.info("buscarPacientePorId -> PacienteResponseDto: " + pacienteResponseDto);

            return Optional.of(pacienteResponseDto);
        } else {
            logger.info("buscarPacientePorId -> El paciente " + id + " no se ecuentra en la base de datos.");
            throw new ResourceNotFoundException("El paciente " + id + " no se ecuentra en la base de datos.");
        }
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public void modificarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    @Override
    public void eliminarPaciente(Integer id) {
        pacienteRepository.deleteById(id);
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
        //pacienteResponseDto.setDomicilioResponseDto(modelMapper.map(paciente.getDomicilio(), DomicilioResponseDto.class));
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