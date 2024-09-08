package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response;

import dh.backend.proyectoIntegrador.clinicaOdontologica.utils.GsonProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteResponseDto {

    private Integer id;

    private String nombre;

    private String apellido;

    private String dni;

    private LocalDate fechaIngreso;

    // Datos del domicilio
    private DomicilioResponseDto domicilio;

    // Datos del(los) turno(s)
    //private Set<Turno> turnoSet;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }

}