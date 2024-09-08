package dh.backend.proyectoIntegrador.clinicaOdontologica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "domicilios")
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String calle;

    private int numero;

    //@NotBlank
    private String localidad;

    //@NotBlank
    private String provincia;

}