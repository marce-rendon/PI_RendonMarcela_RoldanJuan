package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.OdontologoModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.OdontologoRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.PacienteModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.OdontologoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IOdontologoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    @Autowired
    private IOdontologoService odontologoService;

    // Cuando se usa @Autowired, ya no se requiere el constructo para inyectar la dependencia IOdontologoService
    /*
    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }
    */

    //GET
    @GetMapping("/buscartodos")
    public ResponseEntity<List<OdontologoResponseDto>> buscarTodosLosOdontologos(){
        return ResponseEntity.ok(odontologoService.buscarTodosLosOdontologos());
    }

    //GET
    @GetMapping("/buscar/{id}")
    public ResponseEntity<OdontologoResponseDto> buscarOdontologoPorId(@PathVariable Integer id){
        Optional<OdontologoResponseDto> odontologoEncontrado = odontologoService.buscarOdontologoPorId(id);
        return ResponseEntity.ok(odontologoEncontrado.get());
    }

    //POST
    @PostMapping("/guardar")
    public ResponseEntity<OdontologoResponseDto> guardarOdontologo(@Valid @RequestBody OdontologoRequestDto odontologoRequestDto){
        // Jackson convierte el objeto JSON a un objeto Java "Odontologo"
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologoRequestDto));
    }

    //PUT
    @PutMapping("/modificar")
    public ResponseEntity<String> modificarOdontologo(@Valid @RequestBody OdontologoModifyDto odontologoModifyDto){
        odontologoService.modificarOdontologo(odontologoModifyDto);
        return ResponseEntity.ok("{\"mensaje\" : \"El odontólogo "+ odontologoModifyDto.getId() +" fue modificado\"}");
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id){
        odontologoService.eliminarOdontologo(id);
        String jsonResponse = "{\"mensaje\": \"El odontólogo " + id + " fue eliminado.\"}";
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<List<OdontologoResponseDto>> buscarPorNombre(@PathVariable String nombre){
        return ResponseEntity.ok(odontologoService.buscarPorNombreLike(nombre));
    }

    @GetMapping("/buscarApellido/{apellido}")
    public ResponseEntity<List<OdontologoResponseDto>> buscarPorApellido(@PathVariable String apellido){
        return ResponseEntity.ok(odontologoService.buscarPorApellidoLike(apellido));
    }

    @GetMapping("/buscarMatricula/{matricula}")
    public ResponseEntity<OdontologoResponseDto> buscarPorMatricula(@PathVariable String matricula){
        return ResponseEntity.ok(odontologoService.buscarPorMatricula(matricula));
    }

}