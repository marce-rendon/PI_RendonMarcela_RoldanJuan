package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response;

import dh.backend.proyectoIntegrador.clinicaOdontologica.entity.Domicilio;
import dh.backend.proyectoIntegrador.clinicaOdontologica.utils.GsonProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteResponseDto {
    private Integer id;
    private String apellido;
    private String nombre;
    private String dni;

    // Datos del domicilio
    //private DomicilioResponseDto domicilioResponseDto;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }

}