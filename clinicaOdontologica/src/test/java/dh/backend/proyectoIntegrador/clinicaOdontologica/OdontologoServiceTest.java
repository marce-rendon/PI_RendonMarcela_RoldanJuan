package dh.backend.proyectoIntegrador.clinicaOdontologica;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.OdontologoRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.OdontologoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OdontologoServiceTest {

    static final Logger logger = LoggerFactory.getLogger(OdontologoServiceTest.class);

    @Autowired
    OdontologoService odontologoService;

    @BeforeAll
    static void crearTablas(){
//        H2Connection.crearTablas();
    }

    @Test
    @DisplayName("Testear que un odontologo fue cargado correctamente")
    void guardarOdontologoTest(){
        //Dado
        OdontologoRequestDto odontologo = OdontologoRequestDto.builder()
                .nombre("Lionel")
                .apellido("Messi")
                .nroMatricula("Mat12345")
                .build();
        //cuando
        OdontologoResponseDto odontologoDesdeDb = odontologoService.guardarOdontologo(odontologo);
        // entonces
        assertNotNull(odontologoDesdeDb.getId());
    }

    @Test
    @DisplayName("Testear que un odontologo pueda acceder por id")
    void consultarOdontologoPorIdTest(){
        //Dado
        OdontologoRequestDto odontologo = OdontologoRequestDto.builder()
                .nombre("Lionel")
                .apellido("Messi")
                .nroMatricula("Mat12345")
                .build();
        odontologoService.guardarOdontologo(odontologo);
        Integer id = 1;
        //cuando
        Optional<OdontologoResponseDto> odontologoDesdeDb = odontologoService.buscarOdontologoPorId(id);
        // entonces
        assertEquals(id, odontologoDesdeDb.orElseThrow().getId());
    }

    @Test
    @DisplayName("Listar todos los odontologos")
    void ListarOdontologosTest(){
        //Dado
        OdontologoRequestDto odontologo = OdontologoRequestDto.builder()
                .nombre("Lionel")
                .apellido("Messi")
                .nroMatricula("Mat12345")
                .build();
        odontologoService.guardarOdontologo(odontologo);
        List<OdontologoResponseDto> odontologos;
        // cuando
        odontologos = odontologoService.buscarTodosLosOdontologos();
        // entonces
        assertFalse(odontologos.isEmpty());
    }

}
