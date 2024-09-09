package dh.backend.proyectoIntegrador.clinicaOdontologica;

import dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ClinicaApplicationTests {

	@Autowired
	private PacienteService clinicaService;

	@Test
	void contextLoads() {
		assertNotNull(clinicaService, "ClinicaService should be loaded in the application context");
	}

}
