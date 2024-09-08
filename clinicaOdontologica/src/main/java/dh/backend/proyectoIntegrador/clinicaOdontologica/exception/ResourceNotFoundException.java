package dh.backend.proyectoIntegrador.clinicaOdontologica.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }

}