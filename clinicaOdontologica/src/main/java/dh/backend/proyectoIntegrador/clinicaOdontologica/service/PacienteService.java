package dh.backend.proyectoIntegrador.clinicaOdontologica.service;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dao.IDao;
import dh.backend.proyectoIntegrador.clinicaOdontologica.model.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PacienteService {
    private IDao<Paciente> pacienteIDao;

    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente guardarPaciente(Paciente paciente){
        return pacienteIDao.guardar(paciente);
    }

    public Paciente buscarPacientePorId(Integer id){
        return pacienteIDao.buscarPorId(id);
    }

    public List<Paciente> buscarTodosLosPacientes(){
        return pacienteIDao.listaTodos();
    }

    public void modificarPaciente(Paciente paciente){
        pacienteIDao.modificar(paciente);
    }

    public void eliminarPaciente(Integer id){
        pacienteIDao.eliminar(id);
    }
}
