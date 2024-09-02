package dh.backend.proyectoIntegrador.clinicaOdontologica.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dh.backend.proyectoIntegrador.clinicaOdontologica.utils.GsonProvider;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nroMatricula;
    private String apellido;
    private String nombre;

    @OneToMany(mappedBy = "odontologo", cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "odontologo-turno")
    //@JsonIgnore
    private Set<Turno> turnoSet;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }

}