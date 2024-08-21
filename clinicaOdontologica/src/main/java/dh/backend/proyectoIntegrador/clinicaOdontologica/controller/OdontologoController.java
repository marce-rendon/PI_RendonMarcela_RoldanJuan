package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.model.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.OdontologoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    //POST
    @PostMapping("/guardar")
    public Odontologo guardarOdontologo(@RequestBody Odontologo odontologo){
        return odontologoService.guardarOdontologo(odontologo);
    }

    //PUT
    @PutMapping("/modificar")
    public String modificarOdontologo(@RequestBody Odontologo odontologo){
        odontologoService.modificarOdontologo(odontologo);
        return "El odontologo "+ odontologo.getId() + " fue modificado";
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public String eliminarOdontologo(@PathVariable Integer id){
        odontologoService.eliminarOdontologo(id);
        return "El odontologo "+ id + " fue eliminado";
    }

    //GET
    @GetMapping("/buscar/{id}")
    public Odontologo buscarPorId(@PathVariable Integer id){
        return odontologoService.buscarOdontologoPorId(id);
    }

    //GET
    @GetMapping("/buscartodos")
    public List<Odontologo> buscarTodos(){
        return odontologoService.buscarTodosLosOdontologos();
    }
}
