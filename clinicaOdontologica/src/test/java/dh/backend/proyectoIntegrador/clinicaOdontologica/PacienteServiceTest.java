package dh.backend.proyectoIntegrador.clinicaOdontologica;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.DomicilioRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.PacienteRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.PacienteResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Domicilio;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl.PacienteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class PacienteServiceTest {

    static final Logger logger = LoggerFactory.getLogger(PacienteServiceTest.class);
    @Autowired
    PacienteService pacienteService;

    @BeforeAll
    static void crearTablas(){

        //H2Connection.crearTablas();
    }

    @Test
    @DisplayName("Testear que un paciente fue cargado correctamente con su domicilio")
    void pacienteCargadoTest(){
        //Dado
        PacienteRequestDto paciente = PacienteRequestDto.builder()
                .nombre("Ronaldo")
                .apellido("Cristiano")
                .dni("48974646")
                .fechaIngreso(LocalDate.of(2024,7,15).toString())
                .domicilio(DomicilioRequestDto.builder()
                        .calle("Falsa")
                        .numero(145)
                        .provincia("Buenos Aires")
                        .localidad("CABA")
                        .build())
                .build();

        //cuando
        PacienteResponseDto pacienteDesdeDb = pacienteService.guardarPaciente(paciente);
        // entonces
        assertNotNull(pacienteDesdeDb.getId());
    }

    @Test
    @DisplayName("Testear que un paciente pueda acceder por id")
    void consultarPacienteporIdTest(){
        //Dado
        PacienteRequestDto paciente = PacienteRequestDto.builder()
                .nombre("Ronaldo")
                .apellido("Cristiano")
                .dni("48974646")
                .fechaIngreso(LocalDate.of(2024,7,15).toString())
                .domicilio(DomicilioRequestDto.builder()
                        .calle("Falsa")
                        .numero(145)
                        .provincia("Buenos Aires")
                        .localidad("CABA")
                        .build())
                .build();
        PacienteResponseDto pacienteGuardado = pacienteService .guardarPaciente(paciente);
        Integer id = pacienteGuardado.getId();
        //cuando
        Optional<PacienteResponseDto> pacienteDesdeDb = pacienteService.buscarPacientePorId(id);
        // entonces
        assertEquals(id, pacienteDesdeDb.orElseThrow().getId());
    }

    @Test
    @DisplayName("Listar todos los pacientes")
    void ListarPacienteTest(){
        //Dado
        PacienteRequestDto paciente = PacienteRequestDto.builder()
                .nombre("Ronaldo")
                .apellido("Cristiano")
                .dni("48974646")
                .fechaIngreso(LocalDate.of(2024,7,15).toString())
                .domicilio(DomicilioRequestDto.builder()
                        .calle("Falsa")
                        .numero(145)
                        .provincia("Buenos Aires")
                        .localidad("CABA")
                        .build())
                .build();
        pacienteService.guardarPaciente(paciente);
        List<PacienteResponseDto> pacientes;
        // cuando
        pacientes = pacienteService.buscarTodosLosPacientes();
        // entonces
        assertFalse(pacientes.isEmpty());
    }


}