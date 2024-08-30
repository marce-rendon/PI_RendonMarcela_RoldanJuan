package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoResponseDto {
    private Integer id;
    // Datos del paciente
    private PacienteResponseDto pacienteResponseDto;
    // Datos del odontólogo
    private OdontologoResponseDto odontologoResponseDto;
    private String fecha;

}