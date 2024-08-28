package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Turno;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.ITurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    //GET
    @GetMapping("/buscartodos")
    public ResponseEntity<List<Turno>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodosLosTurnos());
    }

    //POST
    @PostMapping("/guardar")
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno){
        return ResponseEntity.ok(turnoService.guardarTurno(turno));
    }

}





/*
    // Service
    private TurnoService turnoService;

    // Constructor
    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    // GET
    @GetMapping("/buscartodos")
    public ResponseEntity<List<Turno>> buscarTodosLosTurnos(){
        return ResponseEntity.ok(turnoService.buscarTodosLosTurnos());
    }

    //GET
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Integer id){
        Turno turnoEncontrado = turnoService.buscarTurnoPorId(id);
        if(turnoEncontrado != null) {
            return ResponseEntity.ok(turnoEncontrado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST
    @PostMapping("/guardar")
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno){
        return ResponseEntity.ok(turnoService.guardarTurno(turno));
    }

    //PUT
    @PutMapping("/modificar")
    public ResponseEntity<String> modificarTurno(@RequestBody Turno turno){
        Turno turnoEncontrado = turnoService.buscarTurnoPorId(turno.getId());
        if(turnoEncontrado != null){
            turnoService.modificarTurno(turno);
            String jsonResponse = "{\"mensaje\": \"El turno " + turno.getId() + " fue modificado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id){
        Turno turnoEncontrado = turnoService.buscarTurnoPorId(id);
        if(turnoEncontrado != null) {
            turnoService.eliminarTurno(id);
            String jsonResponse = "{\"mensaje\": \"El paciente " + turnoEncontrado.getId() + " fue eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
*/
