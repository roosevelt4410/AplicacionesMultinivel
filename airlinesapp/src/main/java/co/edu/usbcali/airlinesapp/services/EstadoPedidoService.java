package co.edu.usbcali.airlinesapp.services;

import java.util.List;


import co.edu.usbcali.airlinesapp.domain.EstadoPedido;
import co.edu.usbcali.airlinesapp.dtos.EstadoPedidoDTO;
import co.edu.usbcali.airlinesapp.exceptions.EstadoPedidoException;

public interface EstadoPedidoService  {
	
	List<EstadoPedidoDTO> obtenerTodos();
	EstadoPedidoDTO buscarPorId(Integer id) throws Exception;
    EstadoPedido buscarTipoDocumentoPorId(Integer id) throws Exception;
    EstadoPedidoDTO actualizar(EstadoPedidoDTO estadoPedidoDTO) throws EstadoPedidoException;
    
    void eliminar(Integer id) throws Exception;
    EstadoPedidoDTO guardar(EstadoPedidoDTO estadoPedidoDTO) throws Exception;
    EstadoPedido buscarEstadoPedidoPorId(Integer id) throws EstadoPedidoException;
}
