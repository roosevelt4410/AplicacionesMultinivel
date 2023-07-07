

package co.edu.usbcali.airlinesapp.request;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import co.edu.usbcali.airlinesapp.utils.ProductoServiceMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrearProductoRequest {

    @NotBlank(message = ProductoServiceMessage.REFERENCIA_REQUERIDA)
    private String referencia;

    @NotBlank(message = ProductoServiceMessage.NOMBRE_REQUERIDO)
    private String nombre;
    private String descripcion;

    @NotNull(message = ProductoServiceMessage.PRECIO_UNITARIO)
    @Min(value = 1, message = ProductoServiceMessage.PRECIO_UNITARIO_NUMERO)
    private BigDecimal precioUnitario;

    @NotNull(message = ProductoServiceMessage.UNIDADES_DISPONIBLES_REQUERIDO)
    @Min(value = 0, message = ProductoServiceMessage.UNIDADES_DISPONIBLES_NUMERO)
    private BigDecimal unidadesDisponibles;

    @NotNull(message = ProductoServiceMessage.CATEGORIA_REQUERIDA)
    private Integer categoria;
}