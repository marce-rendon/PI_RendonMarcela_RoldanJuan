package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OdontologoRequestDto {

    private String nombre;

    private String apellido;

    private String nroMatricula;

}