package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.TurnoModifyDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request.TurnoRequestDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response.TurnoResponseDto;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private ITurnoService turnoService;

    // Cuando se usa @Autowired, ya no se requiere el constructor para inyectar la dependencia ITurnoService
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
    public ResponseEntity<TurnoResponseDto> buscarTurnoPorId(@PathVariable Integer id){
        Optional<TurnoResponseDto> turnoEncontrado = turnoService.buscarTurnoPorId(id);
            return ResponseEntity.ok(turnoEncontrado.get());
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
            turnoService.eliminarTurno(id);
            String jsonResponse = "{\"mensaje\": \"El turno " + id + " fue eliminado.\"}";
            return ResponseEntity.ok(jsonResponse);
        }

    @GetMapping("/buscarTurnoApellido/{apellido}")
    public ResponseEntity<TurnoResponseDto> buscarTurnoPorApellido(@PathVariable String apellido){
        Optional<TurnoResponseDto> turno = turnoService.buscarTurnosPorPaciente(apellido);
        return ResponseEntity.ok(turno.get());
    }

}