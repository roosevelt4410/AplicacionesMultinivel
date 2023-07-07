package co.edu.usbcali.airlinesapp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.usbcali.airlinesapp.domain.Pedido;
import co.edu.usbcali.airlinesapp.dtos.PedidoDTO;

public class PedidoMapper {
	public static PedidoDTO domainToDto(Pedido pedido){
        return PedidoDTO.builder()
                .id(pedido.getId())
                .fecha(pedido.getFecha())
                .total(pedido.getTotal())
                .estadoPedidoId(pedido.getEstadoPedido() == null ?
                            null : pedido.getEstadoPedido().getId())
                .clienteId(pedido.getCliente() == null ?
                            null : pedido.getCliente().getId())
                .build();
    }

    public static Pedido dtoToDomain(PedidoDTO pedidoDto){
        return Pedido.builder()
                .id(pedidoDto.getId())
                .fecha(pedidoDto.getFecha())
                .total(pedidoDto.getTotal())
                .build();
    }

    public static List<PedidoDTO> domainToDtoList(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(PedidoMapper::domainToDto)
                .collect(Collectors.toList());
    }

    public static List<Pedido> dtoToDomainList(List<PedidoDTO> pedidosDtos) {
        return pedidosDtos.stream()
                .map(PedidoMapper::dtoToDomain)
                .collect(Collectors.toList());
    }

}
