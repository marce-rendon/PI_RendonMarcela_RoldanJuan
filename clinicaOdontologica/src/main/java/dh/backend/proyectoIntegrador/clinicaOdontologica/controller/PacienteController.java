package dh.backend.proyectoIntegrador.clinicaOdontologica.controller;

import dh.backend.proyectoIntegrador.clinicaOdontologica.model.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //POST
    @PostMapping("/guardar")
    public Paciente guardarPaciente(@RequestBody Paciente paciente){
        return pacienteService.guardarPaciente(paciente);
    }

    //PUT
    @PutMapping("/modificar")
    public String modificarPaciente(@RequestBody Paciente paciente){
        pacienteService.modificarPaciente(paciente);
        return "El paciente "+ paciente.getId() + " fue modificado";
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Integer id){
        pacienteService.eliminarPaciente(id);
        return "El paciente "+ id + " fue eliminado";
    }

    //GET
    @GetMapping("/buscar/{id}")
    public Paciente buscarPorId(@PathVariable Integer id){
        return pacienteService.buscarPacientePorId(id);
    }

    //GET
    @GetMapping("/buscartodos")
    public List<Paciente> buscarTodos(){
        return pacienteService.buscarTodosLosPacientes();
    }
}
