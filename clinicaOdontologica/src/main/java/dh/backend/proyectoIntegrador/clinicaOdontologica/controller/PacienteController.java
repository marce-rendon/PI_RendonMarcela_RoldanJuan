package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //GET
    @GetMapping("/buscartodos")
    public ResponseEntity<List<Paciente>>  buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodosLosPacientes());
    }

    //GET
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Paciente>  buscarPorId(@PathVariable Integer id){
        Optional<Paciente>  pacienteEncontrado = pacienteService.buscarPacientePorId(id);
        if(pacienteEncontrado.isPresent()) {
            return ResponseEntity.ok(pacienteEncontrado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //POST
    @PostMapping("/guardar")
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    //PUT
    @PutMapping("/modificar")
    public ResponseEntity<String>  modificarPaciente(@RequestBody Paciente paciente){
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPacientePorId(paciente.getId());
        if(pacienteEncontrado.isPresent()){
            pacienteService.modificarPaciente(paciente);
            // String jsonResponse = "{\"mensaje\": \"El paciente " + pacienteEncontrado.getId() + " fue modificado.\"}";
            String jsonResponse = "{\"mensaje\": \"El paciente fue modificado.\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Integer id){
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPacientePorId(id);
        if(pacienteEncontrado.isPresent()) {
            pacienteService.eliminarPaciente(id);
            // String jsonResponse = "{\"mensaje\": \"El paciente " + pacienteEncontrado.getId() + " fue eliminado.\"}";
            String jsonResponse = "{\"mensaje\": \"El paciente fue eliminado.\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}





/*


    // Service
    private PacienteService pacienteService;

    // Constructor
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //GET
    @GetMapping("/buscartodos")
    public ResponseEntity<List<Paciente>> buscarTodosLosPacientes(){
        return ResponseEntity.ok(pacienteService.buscarTodosLosPacientes());
    }

    //GET
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id){
        Paciente pacienteEncontrado = pacienteService.buscarPacientePorId(id);
        if(pacienteEncontrado != null) {
            return ResponseEntity.ok(pacienteEncontrado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //POST
    @PostMapping("/guardar")
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    //PUT
    @PutMapping("/modificar")
    public ResponseEntity<String> modificarPaciente(@RequestBody Paciente paciente){
        Paciente pacienteEncontrado = pacienteService.buscarPacientePorId(paciente.getId());
        if(pacienteEncontrado != null){
            pacienteService.modificarPaciente(paciente);
            String jsonResponse = "{\"mensaje\": \"El paciente " + pacienteEncontrado.getId() + " fue modificado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Integer id){
        Paciente pacienteEncontrado = pacienteService.buscarPacientePorId(id);
        if(pacienteEncontrado != null) {
            pacienteService.eliminarPaciente(id);
            String jsonResponse = "{\"mensaje\": \"El paciente " + pacienteEncontrado.getId() + " fue eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

*/