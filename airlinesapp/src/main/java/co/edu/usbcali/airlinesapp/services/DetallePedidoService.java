package co.edu.usbcali.airlinesapp.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import co.edu.usbcali.airlinesapp.domain.DetallesPedido;
import co.edu.usbcali.airlinesapp.dtos.DetallePedidoDTO;
import co.edu.usbcali.airlinesapp.exceptions.DetallePedidoException;
import co.edu.usbcali.airlinesapp.exceptions.PedidoException;
import co.edu.usbcali.airlinesapp.exceptions.ProductoException;

public interface DetallePedidoService {
	
	 List<DetallePedidoDTO> obtenerTodos();

	    DetallePedidoDTO buscarPorId(Integer id) throws DetallePedidoException;

	    DetallePedidoDTO guardar(DetallePedidoDTO detallePedidoDTO)
	            throws PedidoException, ProductoException, DetallePedidoException;

	    DetallePedidoDTO actualizar(DetallePedidoDTO detallePedidoDTO)
	            throws PedidoException, ProductoException, DetallePedidoException;

		List<Object> guardarListado(List<DetallePedidoDTO> listDetallePedido)
			throws PedidoException, ProductoException, DetallePedidoException;
		
		 public void eliminarPorId(Integer pedidoId);
		 
		 public List<DetallesPedido> obtenerDetallesPorPedidoId(Integer pedidoId);

}