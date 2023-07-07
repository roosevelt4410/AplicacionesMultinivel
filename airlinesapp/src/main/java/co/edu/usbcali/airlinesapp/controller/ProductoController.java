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

import co.edu.usbcali.airlinesapp.domain.Producto;
import co.edu.usbcali.airlinesapp.dtos.ProductoDTO;
import co.edu.usbcali.airlinesapp.exceptions.CategoriaException;
import co.edu.usbcali.airlinesapp.exceptions.ProductoException;
import co.edu.usbcali.airlinesapp.exceptions.TipoDocumentoException;
import co.edu.usbcali.airlinesapp.repository.IProductoRepository;
import co.edu.usbcali.airlinesapp.request.CrearProductoRequest;
import co.edu.usbcali.airlinesapp.response.CrearProductoResponse;
import co.edu.usbcali.airlinesapp.services.ProductoService;
import co.edu.usbcali.airlinesapp.utils.ProductoServiceMessage;
import co.edu.usbcali.airlinesapp.utils.TipoDocumentoServiceMessages;
import co.edu.usbcali.airlinesapp.utils.ValidationsUtil;



@RestController
@RequestMapping("/productos")
@CrossOrigin("*")
public class ProductoController {
	
	private final ProductoService productoService;
	
	private IProductoRepository productoRepository;

    public ProductoController(ProductoService productoService, IProductoRepository productoRepository) {
        this.productoService = productoService;
        this.productoRepository = productoRepository;
    }

    @PostMapping("/nuevo")
    CrearProductoResponse nuevoProducto(@RequestBody @Valid CrearProductoRequest crearProductoRequest) throws Exception {
        return productoService.guardarNuevo(crearProductoRequest);
    }
    
    @GetMapping("/listarProductos")
	public List<Producto> findAll(){
    	
    	List<Producto> productos = productoRepository.findAll();
    	System.out.println(productos);
		return productos;
	}
    
    
    @GetMapping("/{id}")
	public ResponseEntity<ProductoDTO> findById(@PathVariable Integer id) throws Exception {
	    ValidationsUtil.integerIsNullOrLessZero(id, ProductoServiceMessage.CATEGORIA_REQUERIDA);
	    ProductoDTO productoDTO = productoService.buscarPorId(id);
	    if (productoDTO!=null) {
	        return ResponseEntity.ok(productoDTO);
	    } else {
	        throw new TipoDocumentoException(
	            String.format(TipoDocumentoServiceMessages.TIPO_DOCUMENTO_NO_ENCONTRADO_POR_ID, id));
	    }
	}

    @PutMapping("/actualizar")
    ResponseEntity<ProductoDTO> actualizarProducto(@RequestBody @Valid ProductoDTO productoDTO) throws ProductoException, CategoriaException {
        
    	System.out.println(productoDTO);
    	return  ResponseEntity.ok(productoService.actualizar(productoDTO));
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
