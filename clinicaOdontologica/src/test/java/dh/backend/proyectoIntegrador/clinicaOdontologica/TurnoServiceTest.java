package dh.backend.proyectoIntegrador.clinicaOdontologica;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.DomicilioRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.PacienteRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.TurnoRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.PacienteResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.TurnoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Domicilio;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Turno;
import dh.backend.proyectoIntegrador.clinicaOdontologica.exception.ResourceNotFoundException;
import dh.backend.proyectoIntegrador.clinicaOdontologica.repository.IOdontologoRepository;
import dh.backend.proyectoIntegrador.clinicaOdontologica.repository.IPacienteRepository;
import dh.backend.proyectoIntegrador.clinicaOdontologica.repository.ITurnoRepository;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IOdontologoService;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl.PacienteService;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl.TurnoService;
import org.aspectj.lang.annotation.Before;
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
class TurnoServiceTest {

    static final Logger logger = LoggerFactory.getLogger(TurnoServiceTest.class);
    @Autowired
    TurnoService turnoService;

    @Autowired
    IPacienteRepository pacienteRepository;

    @Autowired
    IOdontologoRepository odontologoRepository;

    @Autowired
    ITurnoRepository turnoRepository;


    @Test
    @DisplayName("Testear que un turno fue asinado correctamente")
    void turnoAsignadoTest(){
        //Dado
        Paciente paciente = guardarPaciente();
        Odontologo odontologo = guardarOdontologo();
        TurnoRequestDto turno = TurnoRequestDto.builder()
                .paciente_id (paciente.getId())
                .odontologo_id(odontologo.getId())
                .fecha(LocalDate.of(2024, 5, 20).toString())
                .build();

        //cuando
        TurnoResponseDto turnoResponse = turnoService.guardarTurno(turno);
        // entonces
        assertNotNull(turnoResponse.getId());
    }


    @Test
    @DisplayName("Testear que se pueda buscar turno por ID")
    void consultarTurnoPorIdTest(){
        //Dado
        Paciente paciente = guardarPaciente();
        Odontologo odontologo = guardarOdontologo();
        TurnoRequestDto turnoRequestDto = TurnoRequestDto.builder()
                .paciente_id (paciente.getId())
                .odontologo_id(odontologo.getId())
                .fecha(LocalDate.of(2024, 5, 20).toString())
                .build();

        TurnoResponseDto turnoResponse = turnoService.guardarTurno(turnoRequestDto);
        Integer id = turnoResponse.getId();
        //cuando
        Optional<TurnoResponseDto> turnoDesdeDb = turnoService.buscarTurnoPorId(id);
        // entonces
        assertEquals(id, turnoDesdeDb.orElseThrow().getId());
    }



    @Test
    @DisplayName("Listar todos los pacientes")
    void ListarPacienteTest(){
        //Dado
        Paciente paciente = guardarPaciente();
        Odontologo odontologo = guardarOdontologo();
        TurnoRequestDto turno = TurnoRequestDto.builder()
                .paciente_id (paciente.getId())
                .odontologo_id(odontologo.getId())
                .fecha(LocalDate.of(2024, 5, 20).toString())
                .build();

        turnoService.guardarTurno(turno);
        // cuando
        List<TurnoResponseDto> turnos = turnoService.buscarTodosLosTurnos();
        // entonces
        assertFalse(turnos.isEmpty());
    }



    private Odontologo guardarOdontologo(){
        Odontologo odontologo = Odontologo.builder()
                .nroMatricula("123456")
                .nombre("Dr. Muelitas")
                .apellido("Cordales")
                .build();

        return odontologoRepository.save(odontologo);
    }

    private Paciente guardarPaciente(){
        Paciente paciente = Paciente.builder()
                .nombre("Juan")
                .apellido("Ronaldo")
                .dni("123456")
                .fechaIngreso(LocalDate.of(1980,7,15))
                .domicilio(Domicilio.builder()
                        .calle("Quinta")
                        .localidad("Fredonia")
                        .provincia("Antioquia")
                        .build())
                .build();

        return pacienteRepository.save(paciente);
    }

    @Test
    @DisplayName("Testear que se pueda eliminar un turno")
    void eliminarTurno (){

        //Dado
        Paciente paciente = guardarPaciente();
        Odontologo odontologo = guardarOdontologo();
        TurnoRequestDto turnoRequest = TurnoRequestDto.builder()
                .paciente_id(paciente.getId())
                .odontologo_id(odontologo.getId())
                .fecha(LocalDate.of (2024, 5, 20).toString())
                .build();

        //Guardar el turno
        TurnoResponseDto turnoResponse = turnoService.guardarTurno(turnoRequest);
        Integer id = turnoResponse.getId();
        assertNotNull(id, "El turno debe ser guardado correctamente antes  de ser eliminado");

        //cuando: eliminar turno
        turnoService.eliminarTurno(id);

        //entonces: verificar que el turno fue eliminado
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            turnoService.buscarTurnoPorId(id);
        });

        // Verificar el mensaje de la excepción
        assertEquals("El turno " + id + " no existe en la base de datos.", exception.getMessage());
    }
}