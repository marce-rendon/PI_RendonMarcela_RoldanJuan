package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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