package dh.backend.proyectoIntegrador.clinicaOdontologica.service;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dao.IDao;
import dh.backend.proyectoIntegrador.clinicaOdontologica.model.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.model.Paciente;
import dh.backend.proyectoIntegrador.clinicaOdontologica.model.Turno;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {
    private IDao<Turno> turnoIDao;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    public TurnoService(IDao<Turno> turnoIDao, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoIDao = turnoIDao;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    public Turno guardarTurno(Turno turno){
        Paciente paciente = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());
        Turno turnoARetornar = null;
        if(paciente != null && odontologo != null){
            turno.setPaciente(paciente);
            turno.setOdontologo(odontologo);
            turnoARetornar = turnoIDao.guardar(turno);
        }
        return turnoARetornar;
    }
    public Turno buscarTurnoPorId(Integer id){
        return turnoIDao.buscarPorId(id);
    }

    public List<Turno> buscarTodosLosTurnos(){
        return turnoIDao.listaTodos();
    }

    void modificarTurno(Turno turno){
        turnoIDao.modificar(turno);
    }

    void eliminarTurno(Integer id){
        turnoIDao.eliminar(id);
    }


}
