package co.edu.usbcali.airlinesapp.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import co.edu.usbcali.airlinesapp.domain.TipoDocumento;
import co.edu.usbcali.airlinesapp.dtos.TipoDocumentoDTO;
import co.edu.usbcali.airlinesapp.exceptions.TipoDocumentoException;
import co.edu.usbcali.airlinesapp.mapper.TipoDocumentoMapper;
import co.edu.usbcali.airlinesapp.repository.ITipoDocumentoRepository;
import co.edu.usbcali.airlinesapp.utils.TipoDocumentoServiceMessages;
import co.edu.usbcali.airlinesapp.utils.ValidationsUtil;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {
	
	private final ITipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoServiceImpl(ITipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    public List<TipoDocumentoDTO> obtenerTodos() {
        return TipoDocumentoMapper.domainToDtoList(tipoDocumentoRepository.findAll());
    }

    @Override
    public TipoDocumentoDTO buscarPorId(Integer id) throws Exception {
        ValidationsUtil.integerIsNullOrLessZero(id, TipoDocumentoServiceMessages.ID_VALIDO_MESSAGE);
        Optional<TipoDocumento> tipoDocumentoOptional = tipoDocumentoRepository.findById(id);
        if (tipoDocumentoOptional.isPresent()) {
            TipoDocumento tipoDocumento = tipoDocumentoOptional.get();
            return TipoDocumentoMapper.domainToDto(tipoDocumento);
        } else {
            throw new TipoDocumentoException(
                String.format(TipoDocumentoServiceMessages.TIPO_DOCUMENTO_NO_ENCONTRADO_POR_ID, id));
        }
    }

    @Override
    public TipoDocumento buscarTipoDocumentoPorId(Integer id) throws Exception {
        ValidationsUtil.integerIsNullOrLessZero(id, TipoDocumentoServiceMessages.ID_VALIDO_MESSAGE);
        Optional<TipoDocumento> tipoDocumentoOptional = tipoDocumentoRepository.findById(id);
        if (tipoDocumentoOptional.isPresent()) {
            return tipoDocumentoOptional.get();
        } else {
            throw new TipoDocumentoException(
                String.format(TipoDocumentoServiceMessages.TIPO_DOCUMENTO_NO_ENCONTRADO_POR_ID, id));
        }
    }
    
    @Override
    public TipoDocumentoDTO actualizar(TipoDocumentoDTO tipoDocumentoDTO) throws TipoDocumentoException {

        TipoDocumento tipoDocumento = TipoDocumentoMapper.dtoToDomain(tipoDocumentoDTO);

        return TipoDocumentoMapper.domainToDto(tipoDocumentoRepository.save(tipoDocumento));
    }
    
    @Override
    public void eliminar(Integer id) throws Exception {
        Optional<TipoDocumento> tipoDocumentoExistente = tipoDocumentoRepository.findById(id);
        if (tipoDocumentoExistente.isPresent()) {
            tipoDocumentoRepository.delete(tipoDocumentoExistente.get());
        } else {
            throw new Exception("No se encontró la categoría con el ID proporcionado");
        }
    }

	@Override
	public TipoDocumentoDTO guardar(TipoDocumentoDTO tipoDocumentoDTO) throws Exception {
        TipoDocumento tipoDocumento = TipoDocumentoMapper.dtoToDomain(tipoDocumentoDTO);
        return TipoDocumentoMapper.domainToDto(tipoDocumentoRepository.save(tipoDocumento));
	}

}
