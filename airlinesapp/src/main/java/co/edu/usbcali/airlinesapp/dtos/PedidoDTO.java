package co.edu.usbcali.airlinesapp.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
	private Integer id;
    private Instant fecha;
    private BigDecimal total;
    private Integer clienteId;
    private Integer estadoPedidoId;
}
