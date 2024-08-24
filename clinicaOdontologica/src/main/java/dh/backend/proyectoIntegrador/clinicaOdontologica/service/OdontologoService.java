package dh.backend.proyectoIntegrador.clinicaOdontologica.service;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dao.IDao;
import dh.backend.proyectoIntegrador.clinicaOdontologica.model.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.model.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OdontologoService {
    private IDao<Odontologo> odontologoIDao;

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public List<Odontologo> buscarTodosLosOdontologos(){
        return odontologoIDao.listaTodos();
    }

    public Odontologo buscarOdontologoPorId(Integer id){
        return odontologoIDao.buscarPorId(id);
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoIDao.guardar(odontologo);
    }

    public void modificarOdontologo(Odontologo odontologo){
        odontologoIDao.modificar(odontologo);
    }

    public void eliminarOdontologo(Integer id){
        odontologoIDao.eliminar(id);
    }

}
