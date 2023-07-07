package co.edu.usbcali.airlinesapp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.usbcali.airlinesapp.domain.DetallesPedido;
import co.edu.usbcali.airlinesapp.dtos.DetallePedidoDTO;

public class DetallePedidoMapper {

	
	public static DetallePedidoDTO domainToDto(DetallesPedido detallePedido){
        return DetallePedidoDTO.builder()
                .id(detallePedido.getId())
                .cantidad(detallePedido.getCantidad())
                .valor(detallePedido.getValor())
                .pedidoId(detallePedido.getPedido() == null ?
                        null : detallePedido.getPedido().getId())
                .productoId(detallePedido.getProducto() == null ?
                        null : detallePedido.getProducto().getId())
                .build();
    }

    public static DetallesPedido dtoToDomain(DetallePedidoDTO detallePedidoDto){
        return DetallesPedido.builder()
                .id(detallePedidoDto.getId())
                .cantidad(detallePedidoDto.getCantidad())
                .valor(detallePedidoDto.getValor())
                .build();
    }

    public static List<DetallePedidoDTO> domainToDtoList(List<DetallesPedido> detallePedidos) {
        return detallePedidos.stream()
                .map(DetallePedidoMapper::domainToDto)
                .collect(Collectors.toList());
    }

    public static List<DetallesPedido> dtoToDomainList(List<DetallePedidoDTO> detallePedidosDtos) {
        return detallePedidosDtos.stream()
                .map(DetallePedidoMapper::dtoToDomain)
                .collect(Collectors.toList());
    }

}
