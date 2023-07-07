package co.edu.usbcali.airlinesapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

	private Integer id;
    private String nombres;
    private String apellidos;
    private String documento;
    private String estado;
    private Integer tipoDocumento;
    //private String tipoDocumentoDescripcion;

}
