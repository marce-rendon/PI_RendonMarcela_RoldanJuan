package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.PacienteResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private IPacienteService pacienteService;

    // Cuando se usa @Autowired, ya no se requiere el constructo para inyectar la dependencia IPacienteService
    /*
    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    */

    //GET
    @GetMapping("/buscartodos")
    public ResponseEntity<List<PacienteResponseDto>> buscarTodosLosPacientes(){
        return ResponseEntity.ok(pacienteService.buscarTodosLosPacientes());
    }

    //GET
    @GetMapping("/buscar/{id}")
    public ResponseEntity<PacienteResponseDto> buscarPacientePorId(@PathVariable Integer id){
        Optional<PacienteResponseDto>  pacienteEncontrado = pacienteService.buscarPacientePorId(id);
        if(pacienteEncontrado.isPresent()) {
            return ResponseEntity.ok(pacienteEncontrado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //POST
    @PostMapping("/guardar")
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente){
        // Jackson convierte el objeto JSON a un objeto Java "Paciente"
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    //PUT
    @PutMapping("/modificar")
    public ResponseEntity<String> modificarPaciente(@RequestBody Paciente paciente){
        Optional<PacienteResponseDto> pacienteEncontrado = pacienteService.buscarPacientePorId(paciente.getId());
        if(pacienteEncontrado.isPresent()){
            pacienteService.modificarPaciente(paciente);
            String jsonResponse = "{\"mensaje\": \"El paciente " + pacienteEncontrado.get().getId() + " fue modificado.\"}";
            // String jsonResponse = "{\"mensaje\": \"El paciente fue modificado.\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Integer id){
        Optional<PacienteResponseDto> pacienteEncontrado = pacienteService.buscarPacientePorId(id);
        if(pacienteEncontrado.isPresent()) {
            pacienteService.eliminarPaciente(id);
            String jsonResponse = "{\"mensaje\": \"El paciente " + pacienteEncontrado.get().getId() + " fue eliminado.\"}";
            // String jsonResponse = "{\"mensaje\": \"El paciente fue eliminado.\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarApellidoNombre/{apellido}/{nombre}")
    public ResponseEntity<List<Paciente>> buscarApellido(@PathVariable String apellido, @PathVariable String nombre){
        return ResponseEntity.ok(pacienteService.buscarPorApellidoyNombre(apellido, nombre));
    }

    @GetMapping("/buscarApellido/{parte}")
    public ResponseEntity<List<Paciente>> buscarParteApellido(@PathVariable String parte){
        return ResponseEntity.ok(pacienteService.buscarPorUnaParteApellido(parte));
    }

}