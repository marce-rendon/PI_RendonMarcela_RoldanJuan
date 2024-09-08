package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DomicilioRequestDto {

    private String calle;

    private int numero;

    private String localidad;

    private String provincia;
}
