package dh.backend.proyectoIntegrador.clinicaOdontologica.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dh.backend.proyectoIntegrador.clinicaOdontologica.utils.GsonProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonBackReference(value = "paciente-turno")
    private Paciente paciente;

    @ManyToOne
    @JsonBackReference(value = "odontologo-turno")
    private Odontologo odontologo;

    private LocalDate fecha;

    // Se movió el toString() al correspodiente DTO
    /*
    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }
    */

}