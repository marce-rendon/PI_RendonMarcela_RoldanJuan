package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dh.backend.proyectoIntegrador.clinicaOdontologica.utils.GsonProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OdontologoResponseDto {

    private Integer id;

    private String nombre;

    private String apellido;

    private String nroMatricula;

    // Datos del(los) turno(s)
    //@JsonManagedReference(value = "odontologoDto-turnoDto")
    private Set<TurnoResponseDto> turnoSet;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }

}