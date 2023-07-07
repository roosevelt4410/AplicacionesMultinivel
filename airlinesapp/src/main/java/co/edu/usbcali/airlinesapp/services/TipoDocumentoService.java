package co.edu.usbcali.airlinesapp.services;

import java.util.List;

import co.edu.usbcali.airlinesapp.domain.TipoDocumento;
import co.edu.usbcali.airlinesapp.dtos.TipoDocumentoDTO;
import co.edu.usbcali.airlinesapp.exceptions.TipoDocumentoException;

public interface TipoDocumentoService {
	
	List<TipoDocumentoDTO> obtenerTodos();
    TipoDocumentoDTO buscarPorId(Integer id) throws Exception;
    TipoDocumento buscarTipoDocumentoPorId(Integer id) throws Exception;
    TipoDocumentoDTO actualizar(TipoDocumentoDTO tipoDocumentoDTO) throws TipoDocumentoException;
    
    void eliminar(Integer id) throws Exception;
    TipoDocumentoDTO guardar(TipoDocumentoDTO tipoDocumentoDTO) throws Exception;
}
