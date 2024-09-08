package dh.backend.proyectoIntegrador.clinicaOdontologica.service;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.OdontologoModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.OdontologoRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.OdontologoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import java.util.List;
import java.util.Optional;

public interface IOdontologoService {

    List<OdontologoResponseDto> buscarTodosLosOdontologos();

    Optional<OdontologoResponseDto> buscarOdontologoPorId(Integer id);

    OdontologoResponseDto guardarOdontologo(OdontologoRequestDto odontologoRequestDto);

    void modificarOdontologo(OdontologoModifyDto odontologoModifyDto);

    void eliminarOdontologo(Integer id);

    Odontologo convertirOdontologoResponseDtoEnOdontologo(OdontologoResponseDto odontologoResponseDto);

}