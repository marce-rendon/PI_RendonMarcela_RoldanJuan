package dh.backend.proyectoIntegrador.clinicaOdontologica.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dh.backend.proyectoIntegrador.clinicaOdontologica.utils.GsonProvider;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String apellido;
    private String nombre;
    private String dni;
    private LocalDate fechaIngreso;

    @OneToOne(cascade  = CascadeType.ALL)
    private Domicilio domicilio;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "paciente-turno")
    //@JsonIgnore
    private Set<Turno> turnoSet;


    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }

}