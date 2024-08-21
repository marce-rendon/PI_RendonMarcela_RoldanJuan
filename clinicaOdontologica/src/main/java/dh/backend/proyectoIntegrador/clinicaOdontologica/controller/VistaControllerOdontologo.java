package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.model.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.OdontologoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/odontologoBorrame")
public class VistaControllerOdontologo {

    private OdontologoService odontologoService;

    public static final Logger logger = LoggerFactory.getLogger(VistaControllerOdontologo.class);

    public VistaControllerOdontologo(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping("/{id}")
    public String buscarOdontologo(Model model, @PathVariable("id") Integer identificador){
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(identificador);
        logger.info("El odontologo devuelto al controller proveniente del service: " + odontologo);

        model.addAttribute("nombre", odontologo.getNombre());
        model.addAttribute("apellido", odontologo.getApellido());
        model.addAttribute("matricula", odontologo.getNroMatricula());

        return "vista/odontologo";
    }
}
