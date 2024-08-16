package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dao.impl.DaoH2Domicilio;
import dh.backend.proyectoIntegrador.clinicaOdontologica.model.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.PacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PacienteController {

    private PacienteService pacienteService;

    public static final Logger logger = LoggerFactory.getLogger(DaoH2Domicilio.class);

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/index")
    public String buscarPaciente(Model model, @RequestParam Integer id){
        Paciente paciente = pacienteService.buscarPorId(id);
        logger.info("Paciente devuelto al controller proveniente del service: " + paciente);

        model.addAttribute("nombre", paciente.getNombre());
        model.addAttribute("apellido", paciente.getApellido());

        return "vista/paciente";
    }
}
