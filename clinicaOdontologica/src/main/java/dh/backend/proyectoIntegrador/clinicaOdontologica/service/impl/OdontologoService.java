package dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.OdontologoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.PacienteResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.exception.ResourceNotFoundException;
import dh.backend.proyectoIntegrador.clinicaOdontologica.repository.IOdontologoRepository;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IOdontologoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

            // Se arma el pacienteResponseDto desde el turno obtenido de la base de datos
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

        if(odontologoEncontrado.isPresent()){
            logger.info("buscarOdontologoPorId -> Odontologo " + odontologoEncontrado.get().getId() + " encontrado.");

            OdontologoResponseDto odontologoResponseDto = convertirOdontologoEnOdontologoResponseDto(odontologoEncontrado.get());
            logger.info("buscarOdontologoPorId -> OdontologoResponseDto: " + odontologoResponseDto);

            return Optional.of(odontologoResponseDto);
        } else {
            logger.info("buscarOdontologoPorId -> El odontólogo " + id + " no se ecuentra en la base de datos.");
            throw new ResourceNotFoundException("El odontólogo " + id + " no se ecuentra en la base de datos.");
        }
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) {
        odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        odontologoRepository.deleteById(id);
    }

    private OdontologoResponseDto convertirOdontologoEnOdontologoResponseDto(Odontologo odontologo){
        OdontologoResponseDto odontologoResponseDto = modelMapper.map(odontologo, OdontologoResponseDto.class);
        logger.info("convertirOdontologoEnOdontologoResponseDto -> El odontologoResponseDto " + odontologoResponseDto.getId() + " se armó desde el paciente obtenido de la base de datos de forma automática con modelmapper.");

        return odontologoResponseDto;
    }

    @Override
    public Odontologo convertirOdontologoResponseDtoEnPaciente(OdontologoResponseDto odontologoResponseDto){
        Odontologo odontologo = modelMapper.map(odontologoResponseDto, Odontologo.class);
        //paciente.setDomicilio(modelMapper.map(pacienteResponseDto.getDomicilioResponseDto(), DomicilioResponseDto.class));

        logger.info("convertirOdontologoResponseDtoEnPaciente -> El odontologo " + odontologo.getId() + " se armó desde el odontólogo obtenido de la base de datos forma automática con modelmapper.");
        return odontologo;
    }

}