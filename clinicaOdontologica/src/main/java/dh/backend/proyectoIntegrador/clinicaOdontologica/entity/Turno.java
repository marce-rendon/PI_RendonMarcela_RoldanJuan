package dh.backend.proyectoIntegrador.clinicaOdontologica.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
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

//    @Override
//    public String toString() {
//        return GsonProvider.getGson().toJson(this);
//    }

//    @Override
//    public String toString() {
//        return "Turno{" +
//                "id=" + id +
//                ", paciente=" + paciente +
//                ", odontologo=" + odontologo +
//                ", fecha=" + fecha +
//                '}';
//    }
}
