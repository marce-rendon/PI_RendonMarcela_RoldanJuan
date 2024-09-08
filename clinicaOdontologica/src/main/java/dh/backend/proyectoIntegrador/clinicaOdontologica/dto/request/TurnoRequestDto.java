package dh.backend.proyectoIntegrador.clinicaOdontologica.dto.request;

import dh.backend.proyectoIntegrador.clinicaOdontologica.utils.GsonProvider;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoRequestDto {

    //@NotBlank(message = "El código id del paciente no puede ser nulo.")
    @NotNull(message = "El código id del paciente no puede ser nulo.")
    private Integer paciente_id;

    //@NotBlank(message = "El código id de odontólogo no puede ser nulo.")
    @NotNull(message = "El código id de odontólogo no puede ser nulo.")
    private Integer odontologo_id;

    //@NotNull(message = "La fecha no debe estar vacia")
    @NotEmpty(message = "La fecha no debe estar vacia.")
    private String fecha;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }

}