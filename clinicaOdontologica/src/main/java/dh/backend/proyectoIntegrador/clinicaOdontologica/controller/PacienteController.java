package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.PacienteModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.PacienteRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.PacienteResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IPacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<PacienteResponseDto> pacienteEncontrado = pacienteService.buscarPacientePorId(id);
        return ResponseEntity.ok(pacienteEncontrado.get());
    }

    //POST
    @PostMapping("/guardar")
    public ResponseEntity<PacienteResponseDto> guardarPaciente(@Valid @RequestBody PacienteRequestDto pacienteRequestDto){
        // Jackson convierte el objeto JSON a un objeto Java "Paciente"
        return ResponseEntity.ok(pacienteService.guardarPaciente(pacienteRequestDto));
    }

    //PUT
    @PutMapping("/modificar")
    public ResponseEntity<String> modificarPaciente(@Valid @RequestBody PacienteModifyDto pacienteModifyDto){
        pacienteService.modificarPaciente(pacienteModifyDto);
        return ResponseEntity.ok("{\"mensaje\" : \"El paciente "+ pacienteModifyDto.getId() +" fue modificado\"}");
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Integer id){
        pacienteService.eliminarPaciente(id);
        String jsonResponse = "{\"mensaje\": \"El paciente " + id + " fue eliminado.\"}";
        return ResponseEntity.ok(jsonResponse);
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