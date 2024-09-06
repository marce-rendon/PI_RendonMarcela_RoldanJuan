package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response;

import dh.backend.proyectoIntegrador.clinicaOdontologica.utils.GsonProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OdontologoResponseDto {
    private Integer id;
    private String matricula;
    private String apellido;
    private String nombre;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }

}