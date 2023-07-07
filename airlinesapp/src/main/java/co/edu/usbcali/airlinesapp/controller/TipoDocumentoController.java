package co.edu.usbcali.airlinesapp.controller;


import java.util.List;

import javax.validation.Valid;

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

import co.edu.usbcali.airlinesapp.dtos.TipoDocumentoDTO;
import co.edu.usbcali.airlinesapp.exceptions.TipoDocumentoException;
import co.edu.usbcali.airlinesapp.mapper.TipoDocumentoMapper;
import co.edu.usbcali.airlinesapp.repository.ITipoDocumentoRepository;
import co.edu.usbcali.airlinesapp.services.TipoDocumentoService;
import co.edu.usbcali.airlinesapp.utils.TipoDocumentoServiceMessages;
import co.edu.usbcali.airlinesapp.utils.ValidationsUtil;


@RestController
@RequestMapping("/tipoDocumento")
@CrossOrigin("*")
public class TipoDocumentoController {
	
	@Autowired
	private  TipoDocumentoService tipoDocumentoService;
	
	private  ITipoDocumentoRepository tipoDocumentoRepository;
	
	public TipoDocumentoController(ITipoDocumentoRepository tipoDocumentoRepository) {
		this.tipoDocumentoRepository = tipoDocumentoRepository;
	}
	
	@GetMapping("/listarTiposDocumentos")
	public List<TipoDocumentoDTO> findAll(){
		return TipoDocumentoMapper.domainToDtoList(tipoDocumentoRepository.findAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<TipoDocumentoDTO> findById(@PathVariable Integer id) throws Exception {
	    ValidationsUtil.integerIsNullOrLessZero(id, TipoDocumentoServiceMessages.ID_VALIDO_MESSAGE);
	    TipoDocumentoDTO tipoDocumentoDTO = tipoDocumentoService.buscarPorId(id);
	    if (tipoDocumentoDTO!=null) {
	        return ResponseEntity.ok(tipoDocumentoDTO);
	    } else {
	        throw new TipoDocumentoException(
	            String.format(TipoDocumentoServiceMessages.TIPO_DOCUMENTO_NO_ENCONTRADO_POR_ID, id));
	    }
	}
	
	 @PutMapping("/actualizar")
	    ResponseEntity<TipoDocumentoDTO> actualizarTipoDocumento(@RequestBody @Valid TipoDocumentoDTO tipoDocumentoDTO) throws TipoDocumentoException{
	       
	    	return  ResponseEntity.ok(tipoDocumentoService.actualizar(tipoDocumentoDTO));
	    }
	 
	 @DeleteMapping("/eliminar/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
	        try {
	            tipoDocumentoService.eliminar(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	 
	 @PostMapping("/guardar")
	    public ResponseEntity<TipoDocumentoDTO> save(@RequestBody TipoDocumentoDTO tipoDocumentoDTO) {
	        try {
	            TipoDocumentoDTO saveTipoDocumento = tipoDocumentoService.guardar(tipoDocumentoDTO);
	            return ResponseEntity.status(HttpStatus.CREATED).body(saveTipoDocumento);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

}
