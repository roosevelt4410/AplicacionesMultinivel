package co.edu.usbcali.airlinesapp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.usbcali.airlinesapp.domain.EstadoPedido;
import co.edu.usbcali.airlinesapp.dtos.EstadoPedidoDTO;

public class EstadoPedidoMapper {
	public static EstadoPedidoDTO domainToDto(EstadoPedido estadoPedido) {
        return EstadoPedidoDTO.builder()
                .id(estadoPedido.getId())
                .descripcion(estadoPedido.getDescripcion())
                .build();
    }

    public static EstadoPedido dtoToDomain(EstadoPedidoDTO estadoPedidoDTO) {
        return EstadoPedido.builder()
                .id(estadoPedidoDTO.getId())
                .descripcion(estadoPedidoDTO.getDescripcion())
                .build();
    }

    public static List<EstadoPedidoDTO> domainToDtoList(List<EstadoPedido> estadoPedidos) {
        return estadoPedidos.stream()
                .map(EstadoPedidoMapper::domainToDto)
                .collect(Collectors.toList());
    }

    public static List<EstadoPedido> dtoToDomainList(List<EstadoPedidoDTO> estadoPedidosDtos) {
        return estadoPedidosDtos.stream()
                .map(EstadoPedidoMapper::dtoToDomain)
                .collect(Collectors.toList());
    }

}
