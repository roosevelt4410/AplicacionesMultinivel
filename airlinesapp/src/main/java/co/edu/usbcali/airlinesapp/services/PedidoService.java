package co.edu.usbcali.airlinesapp.services;

import java.util.List;

import co.edu.usbcali.airlinesapp.domain.Pedido;
import co.edu.usbcali.airlinesapp.dtos.PedidoDTO;
import co.edu.usbcali.airlinesapp.exceptions.ClienteException;
import co.edu.usbcali.airlinesapp.exceptions.EstadoPedidoException;
import co.edu.usbcali.airlinesapp.exceptions.PedidoException;


public interface PedidoService {
	
	List<PedidoDTO> obtenerTodos();

    PedidoDTO buscarPorId(Integer id) throws PedidoException;

    Pedido buscarPedidoPorId(Integer id) throws PedidoException;

    PedidoDTO guardar(PedidoDTO pedidoDTO) throws ClienteException, EstadoPedidoException, PedidoException;

    PedidoDTO actualizar(PedidoDTO pedidoDTO) throws ClienteException, EstadoPedidoException, PedidoException;

}
