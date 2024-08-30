package dh.backend.proyectoIntegrador.clinicaOdontologica.service.impl;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Odontologo;
import dh.backend.proyectoIntegrador.clinicaOdontologica.repository.IOdontologoRepository;
import dh.backend.proyectoIntegrador.clinicaOdontologica.service.IOdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {

    @Autowired
    private IOdontologoRepository odontologoRepository;

    // Cuando se usa @Autowired, ya no se requiere el constructo para inyectar la dependencia IOdontologoRepository
    /*
    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }
    */

    @Override
    public List<Odontologo> buscarTodosLosOdontologos() {
        return odontologoRepository.findAll();
    }

    @Override
    public Optional<Odontologo> buscarOdontologoPorId(Integer id) {
        return odontologoRepository.findById(id);
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) {
        odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        odontologoRepository.deleteById(id);
    }

}