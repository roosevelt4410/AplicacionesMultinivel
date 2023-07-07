package co.edu.usbcali.airlinesapp.controller;

import java.util.List;

import javax.validation.Valid;

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

import co.edu.usbcali.airlinesapp.dtos.EstadoPedidoDTO;
import co.edu.usbcali.airlinesapp.exceptions.EstadoPedidoException;
import co.edu.usbcali.airlinesapp.exceptions.TipoDocumentoException;
import co.edu.usbcali.airlinesapp.mapper.EstadoPedidoMapper;
import co.edu.usbcali.airlinesapp.repository.IEstadosPedidoRepository;
import co.edu.usbcali.airlinesapp.services.EstadoPedidoService;
import co.edu.usbcali.airlinesapp.utils.EstadoPedidoServiceMessages;
import co.edu.usbcali.airlinesapp.utils.TipoDocumentoServiceMessages;
import co.edu.usbcali.airlinesapp.utils.ValidationsUtil;

@RestController
@RequestMapping("/estadosPedido")
@CrossOrigin("*")
public class EstadoPedidoController {

	private EstadoPedidoService estadoPedidoService;

	private IEstadosPedidoRepository estadoPedidoRepository;

	public EstadoPedidoController(IEstadosPedidoRepository estadoPedidoRepository,EstadoPedidoService estadoPedidoService) {
		this.estadoPedidoRepository = estadoPedidoRepository;
		this.estadoPedidoService = estadoPedidoService;
	}

	@GetMapping("/listarEstadosPedidos")
	public List<EstadoPedidoDTO> findAll() {
		return EstadoPedidoMapper.domainToDtoList(estadoPedidoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstadoPedidoDTO> findById(@PathVariable Integer id) throws Exception {
		ValidationsUtil.integerIsNullOrLessZero(id, TipoDocumentoServiceMessages.ID_VALIDO_MESSAGE);
		EstadoPedidoDTO estadoPedidoDTO = estadoPedidoService.buscarPorId(id);
		if (estadoPedidoDTO != null) {
			return ResponseEntity.ok(estadoPedidoDTO);
		} else {
			throw new TipoDocumentoException(
					String.format(EstadoPedidoServiceMessages.ESTADO_PEDIDO_NO_ENCONTRADO_POR_ID, id));
		}
	}
	
	 @PutMapping("/actualizar")
	    ResponseEntity<EstadoPedidoDTO> actualizarTipoDocumento(@RequestBody @Valid EstadoPedidoDTO estadoPedidoDTO) throws EstadoPedidoException{
	    	return  ResponseEntity.ok(estadoPedidoService.actualizar(estadoPedidoDTO));
	    }
	 
	 @DeleteMapping("/eliminar/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
	        try {
	        	estadoPedidoService.eliminar(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	 
	 @PostMapping("/guardar")
	    public ResponseEntity<EstadoPedidoDTO> save(@RequestBody EstadoPedidoDTO estadoPedidoDTO) {
	        try {
	            EstadoPedidoDTO saveEstadoPedido = estadoPedidoService.guardar(estadoPedidoDTO);
	            return ResponseEntity.status(HttpStatus.CREATED).body(saveEstadoPedido);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

}
