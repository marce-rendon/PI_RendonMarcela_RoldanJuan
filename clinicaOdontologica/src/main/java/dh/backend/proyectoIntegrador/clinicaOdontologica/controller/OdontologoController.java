package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IOdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    //GET
    @GetMapping("/buscartodos")
    public ResponseEntity<List<Odontologo>> buscarTodosLosOdontologos(){
        return ResponseEntity.ok(odontologoService.buscarTodosLosOdontologos());
    }

    //GET
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Integer id){
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoPorId(id);
        if(odontologo.isPresent()){
            return ResponseEntity.ok(odontologo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //POST
    @PostMapping("/guardar")
    public ResponseEntity<Odontologo> agregarOdontologo(@RequestBody Odontologo odontologo){
        // Jackson convierte el objeto JSON a un objeto Java "Odontologo"
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    //PUT
    @PutMapping("/modificar")
    public ResponseEntity<String> modificarOdontologo(@RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarOdontologoPorId(odontologo.getId());
        if(odontologoEncontrado.isPresent()){
            odontologoService.modificarOdontologo(odontologo);
            String jsonResponse = "{\"mensaje\": \"El paciente " + odontologoEncontrado.get().getId() + " fue modificado.\"}";
            // String jsonResponse = "{\"mensaje\": \"El odontólogo fue modificado.\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id){
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarOdontologoPorId(id);
        if(odontologoEncontrado.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            String jsonResponse = "{\"mensaje\": \"El paciente " + odontologoEncontrado.get().getId() + " fue eliminado.\"}";
            // String jsonResponse = "{\"mensaje\": \"El odontólogo fue eliminado.\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}