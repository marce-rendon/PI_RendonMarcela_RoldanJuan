package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OdontologoRequestDto {

    private String nombre;

    private String apellido;

    private String nroMatricula;

}