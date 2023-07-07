package co.edu.usbcali.airlinesapp.controller;

import java.util.List;

import javax.validation.Valid;

import co.edu.usbcali.airlinesapp.exceptions.DetallePedidoException;
import co.edu.usbcali.airlinesapp.exceptions.PedidoException;
import co.edu.usbcali.airlinesapp.exceptions.ProductoException;
import co.edu.usbcali.airlinesapp.repository.IDetallePedidoRepository;
import co.edu.usbcali.airlinesapp.repository.IPedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.airlinesapp.domain.DetallesPedido;
import co.edu.usbcali.airlinesapp.dtos.DetallePedidoDTO;
import co.edu.usbcali.airlinesapp.services.DetallePedidoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/detallepedido")
@CrossOrigin("*")
public class DetallePedidoController {
	
	@Autowired
	private final DetallePedidoService detallePedidoService;
	@Autowired
	private final IDetallePedidoRepository detallePedidoRepository;
	@Autowired
	private final IPedidoRepository pedidoRepository; 


    @PostMapping("/guardar")
    ResponseEntity<DetallePedidoDTO> nuevoCliente(@RequestBody DetallePedidoDTO detallePedidoDTO) throws Exception {
        return ResponseEntity.ok(detallePedidoService.guardar(detallePedidoDTO));
    }

    @PostMapping("/guardarlistado")
    public ResponseEntity<List<Object>> guardarListado(@RequestBody List<DetallePedidoDTO> listaDetallePedido) throws ProductoException, DetallePedidoException, PedidoException {
        List<Object> response = detallePedidoService.guardarListado(listaDetallePedido);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    ResponseEntity<DetallePedidoDTO> buscarCliente(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(detallePedidoService.buscarPorId(id));
    }

    @GetMapping("/listarDetallePedido")
    ResponseEntity<List<DetallePedidoDTO>> obtenerCliente() throws Exception {
        return ResponseEntity.ok(detallePedidoService.obtenerTodos());
    }

    @PutMapping("/actualizar")
    ResponseEntity<DetallePedidoDTO> actualizar(@RequestBody @Valid DetallePedidoDTO detallePedidoDTO) throws Exception {
        return ResponseEntity.ok(detallePedidoService.actualizar(detallePedidoDTO));
    }
    

    
    @DeleteMapping("/eliminar/{pedidoId}")
    public void eliminar(@PathVariable Integer pedidoId) {	
    	List<DetallesPedido> detallesPedido = detallePedidoRepository.findAllByPedidoId(pedidoId);
    	detallesPedido.forEach(detallePedido -> detallePedidoService.eliminarPorId(detallePedido.getId()));
    	pedidoRepository.deleteById(pedidoId);
    }
    
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<DetallesPedido>> obtenerDetallesPorPedidoId(@PathVariable Integer pedidoId) {
        List<DetallesPedido> detallesPedido = detallePedidoRepository.findAllByPedidoId(pedidoId);
        return ResponseEntity.ok(detallesPedido);
    }
}