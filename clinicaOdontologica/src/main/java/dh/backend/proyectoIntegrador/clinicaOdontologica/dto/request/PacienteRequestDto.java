package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacienteRequestDto {

    //@NotBlank
    private String nombre;

    private String apellido;

    //@NotBlank
    //@Size(min = 8, max = 15)
    private String dni;

    //@NotNull
    private String fechaIngreso;

    private DomicilioRequestDto domicilio;

}