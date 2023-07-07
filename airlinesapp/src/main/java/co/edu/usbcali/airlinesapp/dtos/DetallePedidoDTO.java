package co.edu.usbcali.airlinesapp.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoDTO {
	
	private Integer id;
    private BigDecimal cantidad;
    private BigDecimal valor;
    private Integer pedidoId;
    private Integer productoId;

}
