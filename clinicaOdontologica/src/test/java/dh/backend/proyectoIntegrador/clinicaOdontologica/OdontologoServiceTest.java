package dh.backend.proyectoIntegrador.clinicaOdontologica;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OdontologoServiceTest {

/*
    static final Logger logger = LoggerFactory.getLogger(OdontologoServiceTest.class);
    OdontologoService odontologoService = new OdontologoService(new DaoH2Odontologo());

    @BeforeAll
    static void crearTablas(){
        H2Connection.crearTablas();
    }

    @Test
    @DisplayName("Testear que un odontologo fue cargado correctamente")
    void caso1(){
        //Dado
        Odontologo odontologo = new Odontologo("Mat12345", "Lionel", "Messi");
        //cuando
        Odontologo odontologoDesdeDb = odontologoService.guardarOdontologo(odontologo);
        // entonces
        assertNotNull(odontologoDesdeDb.getId());
    }

    @Test
    @DisplayName("Testear que un odontologo pueda acceder por id")
    void caso2(){
        //Dado
        Integer id = 1;
        //cuando
        Odontologo odontologoDesdeDb = odontologoService.buscarOdontologoPorId(id);
        // entonces
        assertEquals(id, odontologoDesdeDb.getId());
    }

    @Test
    @DisplayName("Listar todos los odontologos")
    void caso3(){
        //Dado
        List<Odontologo> odontologos;
        // cuando
        odontologos = odontologoService.buscarTodosLosOdontologos();
        // entonces
        assertFalse(odontologos.isEmpty());
    }
*/
}
