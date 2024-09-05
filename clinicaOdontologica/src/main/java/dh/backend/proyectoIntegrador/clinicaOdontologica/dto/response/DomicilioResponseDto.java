package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DomicilioResponseDto {
    private Integer id;
    private String calle;
    private int numero;
    private String localidad;
    private String provincia;
}
