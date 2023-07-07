package co.edu.usbcali.airlinesapp.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.airlinesapp.repository.ICategoriaRepository;
import co.edu.usbcali.airlinesapp.services.CategoriaService;
import co.edu.usbcali.airlinesapp.utils.CategoriaServiceMessages;
import co.edu.usbcali.airlinesapp.utils.TipoDocumentoServiceMessages;
import co.edu.usbcali.airlinesapp.utils.ValidationsUtil;
import co.edu.usbcali.airlinesapp.domain.Categoria;
import co.edu.usbcali.airlinesapp.dtos.CategoriaDTO;
import co.edu.usbcali.airlinesapp.exceptions.TipoDocumentoException;

@RestController
@RequestMapping("/categorias")
@CrossOrigin("*")
public class CategoriaController {
	
	private ICategoriaRepository categoriaRepository;
	
	private CategoriaService categoriaService;

	
	public CategoriaController(ICategoriaRepository categoriaRepository, CategoriaService categoriaService) {
		this.categoriaRepository = categoriaRepository;
		this.categoriaService = categoriaService;
	}
	
	@GetMapping("/listarCategorias")
	public List<Categoria> findAll(){
		return categoriaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTO> findById(@PathVariable Integer id) throws Exception {
	    ValidationsUtil.integerIsNullOrLessZero(id, CategoriaServiceMessages.CATEGORIA_NO_ENCONTRADA_POR_ID);
	    CategoriaDTO categoriaDTO = categoriaService.buscarPorId(id);
	    if (categoriaDTO!=null) {
	        return ResponseEntity.ok(categoriaDTO);
	    } else {
	        throw new TipoDocumentoException(
	            String.format(TipoDocumentoServiceMessages.TIPO_DOCUMENTO_NO_ENCONTRADO_POR_ID, id));
	    }
	}
	
	@PostMapping("/guardar")
    public ResponseEntity<CategoriaDTO> save(@RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaDTO savedCategoriaDTO = categoriaService.guardar(categoriaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoriaDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<CategoriaDTO> actualizar(@RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaDTO updatedCategoriaDTO = categoriaService.actualizar(categoriaDTO);
            return ResponseEntity.ok(updatedCategoriaDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping(value = "/buscarPorNombre")
    List<CategoriaDTO> buscarPorNombre(@RequestParam("nombre") String nombre) throws Exception {
        return categoriaService.buscarPorNombreLike(nombre);
    }

}