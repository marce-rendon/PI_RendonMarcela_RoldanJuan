package dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.OdontologoModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.OdontologoRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.OdontologoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.PacienteResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Domicilio;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.exception.BadRequestException;
import dh.backend.proyectoIntegrador.clinicaOdontologica.exception.ResourceNotFoundException;
import dh.backend.proyectoIntegrador.clinicaOdontologica.repository.IOdontologoRepository;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IOdontologoService;
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
public class OdontologoService implements IOdontologoService {

    static final Logger logger = LoggerFactory.getLogger(OdontologoService.class);

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Cuando se usa @Autowired, ya no se requiere el constructo para inyectar la dependencia IOdontologoRepository
    /*
    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }
    */

    @Override
    public List<OdontologoResponseDto> buscarTodosLosOdontologos() {
        List<Odontologo> odontologosDesdeBD = odontologoRepository.findAll();
        List<OdontologoResponseDto> listOdontologosResponseDto = new ArrayList<>();

        for(Odontologo odontologo : odontologosDesdeBD){
            logger.info("buscarTodosLosOdontologos -> Odontologo " + odontologo.getId() + " encontrado.");

            // Se arma el odontologoResponseDto desde el ondontólogo obtenido de la base de datos
            // De forma automática con modelmapper
            OdontologoResponseDto odontologoResponseDto = convertirOdontologoEnOdontologoResponseDto(odontologo);
            logger.info("buscarTodosLosOdontologos -> OdontologoResponseDto: " + odontologoResponseDto);

            listOdontologosResponseDto.add(odontologoResponseDto);
        }

        return listOdontologosResponseDto;
    }

    @Override
    public Optional<OdontologoResponseDto> buscarOdontologoPorId(Integer id) {
        Optional<Odontologo> odontologoEncontrado = odontologoRepository.findById(id);

        // Validamos que el ondontólogo exista
        if(odontologoEncontrado.isPresent()){
            logger.info("buscarOdontologoPorId -> Odontologo " + odontologoEncontrado.get().getId() + " encontrado.");

            // Convertimos de Entity a DTO el paciente
            OdontologoResponseDto odontologoResponseDto = convertirOdontologoEnOdontologoResponseDto(odontologoEncontrado.get());
            logger.info("buscarOdontologoPorId -> OdontologoResponseDto: " + odontologoResponseDto);

            return Optional.of(odontologoResponseDto);
        } else {
            logger.info("buscarOdontologoPorId -> El odontólogo " + id + " no existe en la base de datos.");
            throw new ResourceNotFoundException("El odontólogo " + id + " no existe en la base de datos.");
        }
    }

    @Override
    public OdontologoResponseDto guardarOdontologo(OdontologoRequestDto odontologoRequestDto) {
        // Se arma el odontólogo a persistir en la base de datos obteniendo la información desde el odontologoRequestDto
        Odontologo odontologo = Odontologo.builder()
                .nombre(odontologoRequestDto.getNombre())
                .apellido(odontologoRequestDto.getApellido())
                .nroMatricula(odontologoRequestDto.getNroMatricula())
                .build();

        // Se guarda y obtiene el odontólogo persistido en la base de datos con el id generado automaticamente
        Odontologo odontologoDesdeBD = odontologoRepository.save(odontologo);
        logger.info("guardarOdontologo -> El odontologoDesdeBD " + odontologoDesdeBD.getId() + " se guardó correctamente en la base de datos.");

        // Se arma el odontologoResponseDto desde el odontólogo obtenido de la base de datos
        // De forma automática con modelmapper
        OdontologoResponseDto odontologoResponseDto = convertirOdontologoEnOdontologoResponseDto(odontologoDesdeBD);
        logger.info("guardarOdontologo -> OdontologoResponseDto: " + odontologoResponseDto);

        return odontologoResponseDto;
    }

    @Override
    public void modificarOdontologo(OdontologoModifyDto odontologoModifyDto) {
        try {
            // Validamos que exista el odontólogo a modificar
            Optional<OdontologoResponseDto> odontologoEncontrado = buscarOdontologoPorId(odontologoModifyDto.getId());

            // Creamos el odontólogo a modificar
            Odontologo odontologo = Odontologo.builder()
                    .id(odontologoModifyDto.getId())
                    .nombre(odontologoModifyDto.getNombre())
                    .apellido(odontologoModifyDto.getApellido())
                    .nroMatricula(odontologoModifyDto.getNroMatricula())
                    .build();

            // Se modifica el odontólogo en la base de datos
            odontologoRepository.save(odontologo);
            logger.info("modificarOdontologo -> El odontólogo " + odontologo.getId() + " se modificó correctamente.");
        } catch (ResourceNotFoundException e){
            // Regresa al cliente el mensaje correspondiente al odontólogo de la ResourceNotFoundException
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public void eliminarOdontologo(Integer id) {
        // Validamos que exista el odontólogo a eliminar
        Optional<OdontologoResponseDto> odontologoEncontrado = buscarOdontologoPorId(id);

        // Se borra el odontólogo de la base de datos
        odontologoRepository.deleteById(odontologoEncontrado.get().getId());
        logger.info("eliminarTurno -> El odontólogo " + odontologoEncontrado.get().getId() + " se eliminó de la base de datos.");
    }

    @Override
    public List<OdontologoResponseDto> buscarPorNombreLike(String nombre) {
        // Busca el nombre del odontólogo que sea exactamente igual al nombre del parámetro recibido
        List<Odontologo> odontologosDesdeBD = odontologoRepository.findByNombreLike(nombre);
        List<OdontologoResponseDto> listOdontologosResponseDto = new ArrayList<>();

        for(Odontologo odontologo : odontologosDesdeBD){
            logger.info("buscarPorNombreLike -> Odontologo " + odontologo.getId() + " encontrado.");

            // Se arma el odontologoResponseDto desde el ondontólogo obtenido de la base de datos
            // De forma automática con modelmapper
            OdontologoResponseDto odontologoResponseDto = convertirOdontologoEnOdontologoResponseDto(odontologo);
            logger.info("buscarPorNombreLike -> OdontologoResponseDto: " + odontologoResponseDto);

            listOdontologosResponseDto.add(odontologoResponseDto);
        }

        return listOdontologosResponseDto;
    }

    @Override
    public List<OdontologoResponseDto> buscarPorApellidoLike(String apellido) {
        // Busca el apellido del odontólogo que sea exactamente igual al apellido del parámetro recibido
        List<Odontologo> odontologosDesdeBD = odontologoRepository.findByApellidoLike(apellido);
        List<OdontologoResponseDto> listOdontologosResponseDto = new ArrayList<>();

        for(Odontologo odontologo : odontologosDesdeBD){
            logger.info("buscarPorApellidoLike -> Odontologo " + odontologo.getId() + " encontrado.");

            // Se arma el odontologoResponseDto desde el ondontólogo obtenido de la base de datos
            // De forma automática con modelmapper
            OdontologoResponseDto odontologoResponseDto = convertirOdontologoEnOdontologoResponseDto(odontologo);
            logger.info("buscarPorApellidoLike -> OdontologoResponseDto: " + odontologoResponseDto);

            listOdontologosResponseDto.add(odontologoResponseDto);
        }

        return listOdontologosResponseDto;
    }

    @Override
    public OdontologoResponseDto buscarPorMatricula(String matricula) {
        // Busca la matricula del odontólogo que sea exactamente igual a la matricula del parámetro recibido
        Odontologo odontologoDesdeBD = odontologoRepository.buscarMatricula(matricula);

        OdontologoResponseDto odontologoResponseDto = convertirOdontologoEnOdontologoResponseDto(odontologoDesdeBD);
        logger.info("buscarPorMatricula -> OdontologoResponseDto: " + odontologoResponseDto);

        return odontologoResponseDto;
    }

    private OdontologoResponseDto convertirOdontologoEnOdontologoResponseDto(Odontologo odontologo){
        OdontologoResponseDto odontologoResponseDto = modelMapper.map(odontologo, OdontologoResponseDto.class);

        logger.info("convertirOdontologoEnOdontologoResponseDto -> El odontologoResponseDto " + odontologoResponseDto.getId() + " se armó desde el paciente obtenido de la base de datos de forma automática con modelmapper.");
        return odontologoResponseDto;
    }

    @Override
    public Odontologo convertirOdontologoResponseDtoEnOdontologo(OdontologoResponseDto odontologoResponseDto){
        Odontologo odontologo = modelMapper.map(odontologoResponseDto, Odontologo.class);

        logger.info("convertirOdontologoResponseDtoEnPaciente -> El odontologo " + odontologo.getId() + " se armó desde el odontólogo obtenido de la base de datos forma automática con modelmapper.");
        return odontologo;
    }

}