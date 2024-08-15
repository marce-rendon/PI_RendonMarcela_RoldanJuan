package dh.backendpi.clinicaOdontologica;

import dao.impl.DaoH2Odontologo;
import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {
    private static final Logger logger = Logger.getLogger(OdontologoServiceTest.class);
    private static OdontologoService odontologoService = new OdontologoService(new DaoH2Odontologo());

    @BeforeAll
    static void crearTabla(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection =  DriverManager.getConnection("jdbc:h2:./BaseDatos/odontologos;INIT=RUNSCRIPT FROM 'create.sql'","sa","sa");
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Testear que se agregue un odontologo de manera correcta")
    void caso1(){
        System.out.println("caso 1");
        //DADO
        Odontologo odontologo = new Odontologo("6666","Jhoel","Acosta");
        //CUANDO
        Odontologo odontologoDesdeBD = odontologoService.guardarOdontologo(odontologo);
        // entonces
        assertNotNull(odontologoDesdeBD);
    }


    @Test
    @DisplayName("Testear que se listen todos los odontologos")
    void caso2(){
        //DADO
        List<Odontologo> odontologos;
        //CUANDO
        odontologos = odontologoService.buscarTodos();
        // entonces
        assertNotNull(odontologos);
    }


}