package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.TurnoModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.TurnoRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.TurnoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Turno;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private ITurnoService turnoService;

    // Cuando se usa @Autowired, ya no se requiere el constructo para inyectar la dependencia ITurnoService
    /*
    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }
    */

    //GET
    @GetMapping("/buscartodos")
    public ResponseEntity<List<TurnoResponseDto>> buscarTodosLosTurnos(){
        return ResponseEntity.ok(turnoService.buscarTodosLosTurnos());
    }

    //GET
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Integer id){
        Optional<Turno> turnoEncontrado = turnoService.buscarTurnoPorId(id);
        if(turnoEncontrado.isPresent()) {
            return ResponseEntity.ok(turnoEncontrado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //POST
    @PostMapping("/guardar")
    public ResponseEntity<TurnoResponseDto> guardarTurno(@RequestBody TurnoRequestDto turnoRequestDto){
        return ResponseEntity.ok(turnoService.guardarTurno(turnoRequestDto));
    }

    //PUT
    @PutMapping("/modificar")
    public ResponseEntity<String> modificarTurno(@RequestBody TurnoModifyDto turnoModifyDto){
        turnoService.modificarTurno(turnoModifyDto);
        return ResponseEntity.ok("{\"mensaje\": \"El turno "+ turnoModifyDto.getId() +" fue modificado\"}");
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id){
        Optional<Turno> turnoEncontrado = turnoService.buscarTurnoPorId(id);
        if(turnoEncontrado.isPresent()) {
            turnoService.eliminarTurno(id);
            String jsonResponse = "{\"mensaje\": \"El paciente " + turnoEncontrado.get().getId() + " fue eliminado.\"}";
            //String jsonResponse = "{\"mensaje\": \"El turno fue eliminado.\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarTurnoApellido/{apellido}")
    public ResponseEntity<Turno> buscarTurnoPorApellido(@PathVariable String apellido){
        Optional<Turno> turno = turnoService.buscarTurnosPorPaciente(apellido);
        return ResponseEntity.ok(turno.get());
    }

}