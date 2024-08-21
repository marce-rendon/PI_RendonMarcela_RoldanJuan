package dh.backend.proyectoIntegrador.clinicaOdontologica.dao.impl;

import dh.backend.proyectoIntegrador.clinicaOdontologica.dao.IDao;
import dh.backend.proyectoIntegrador.clinicaOdontologica.db.H2Connection;
import dh.backend.proyectoIntegrador.clinicaOdontologica.model.Odontologo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoH2Odontologo implements IDao<Odontologo> {
    public static final Logger logger = LoggerFactory.getLogger(DaoH2Odontologo.class);
    public static final String INSERT = "INSERT INTO ODONTOLOGOS VALUES (DEFAULT,?,?,?)";
    public static final String SELECT_ID = "SELECT * FROM ODONTOLOGOS WHERE ID = ?";
    public static  final String SELECT_ALL = "SELECT * FROM ODONTOLOGOS";

    @Override
    public Odontologo guardar(Odontologo odontologo) {

        Connection connection = null;
        Odontologo odontologoARetornar = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getNroMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                Integer idDesdeDB = resultSet.getInt(1);
                odontologoARetornar = new Odontologo(idDesdeDB, odontologo.getNroMatricula(), odontologo.getNombre(),
                        odontologo.getApellido());
            }
            logger.info("Odontologo guardado en base de datos: "+ odontologoARetornar );

            connection.commit();

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologoARetornar;
    }

    @Override
    public Odontologo buscarPorId(Integer id) {
        Connection connection = null;
        Odontologo odontologoEncontrado = null;
        try{
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Integer idDB = resultSet.getInt(1);
                String nroMatricula = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);
                odontologoEncontrado = new Odontologo(id, nroMatricula, nombre, apellido);
            }
            if(odontologoEncontrado != null){
                logger.info("Odontologo encontrado: "+ odontologoEncontrado);
            } else {
                logger.info("El odontólogo no se encontró en la base de datos.");
            }

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologoEncontrado;
    }

    @Override
    public List<Odontologo> listaTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        Odontologo odontologoDesdeLaDB = null;

        try{
            connection = H2Connection.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                Integer id = resultSet.getInt(1);
                String nroMatricula = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);
                odontologoDesdeLaDB = new Odontologo(id, nroMatricula, nombre, apellido);

                // Vamos cargando la lista de odontologos
                odontologos.add(odontologoDesdeLaDB);
                logger.info("Odontologo: "+ odontologoDesdeLaDB);
            }

        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return odontologos;

    }

    @Override
    public void modificar(Odontologo odontologo) {

    }

    @Override
    public void eliminar(Integer id) {

    }

}