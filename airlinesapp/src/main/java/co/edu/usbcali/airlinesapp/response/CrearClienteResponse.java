package co.edu.usbcali.airlinesapp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrearClienteResponse {
	private Integer id;
    private String nombres;
    private String apellidos;
    private String documento;
    private String estado;
    private String tipoDocumentoDescripcion;
}
