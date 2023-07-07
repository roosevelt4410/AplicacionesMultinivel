package co.edu.usbcali.airlinesapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.usbcali.airlinesapp.domain.EstadoPedido;
import co.edu.usbcali.airlinesapp.dtos.EstadoPedidoDTO;
import co.edu.usbcali.airlinesapp.exceptions.EstadoPedidoException;
import co.edu.usbcali.airlinesapp.mapper.EstadoPedidoMapper;
import co.edu.usbcali.airlinesapp.repository.IEstadosPedidoRepository;
import co.edu.usbcali.airlinesapp.utils.EstadoPedidoServiceMessages;
import co.edu.usbcali.airlinesapp.utils.TipoDocumentoServiceMessages;
import co.edu.usbcali.airlinesapp.utils.ValidationUtility;
import co.edu.usbcali.airlinesapp.utils.ValidationsUtil;


@Service
public class EstadoPedidoServiceImpl implements EstadoPedidoService {
	
	private final IEstadosPedidoRepository estadoPedidoRepository;

    public EstadoPedidoServiceImpl(IEstadosPedidoRepository estadoPedidoRepository) {
        this.estadoPedidoRepository = estadoPedidoRepository;
    }

	@Override
	public List<EstadoPedidoDTO> obtenerTodos() {
		return EstadoPedidoMapper.domainToDtoList(estadoPedidoRepository.findAll());
	}

	@Override
	public EstadoPedidoDTO buscarPorId(Integer id) throws Exception {
		ValidationsUtil.integerIsNullOrLessZero(id, TipoDocumentoServiceMessages.ID_VALIDO_MESSAGE);
		Optional<EstadoPedido> tipoDocumentoOptional = estadoPedidoRepository.findById(id);
		if (tipoDocumentoOptional.isPresent()) {
		    return EstadoPedidoMapper.domainToDto(tipoDocumentoOptional.get());
		} else {
		    throw new EstadoPedidoException(
		        String.format(TipoDocumentoServiceMessages.TIPO_DOCUMENTO_NO_ENCONTRADO_POR_ID, id)
		    );
		}
	}
	 @Override
	    public EstadoPedidoDTO actualizar(EstadoPedidoDTO estadoPedidoDTO) throws EstadoPedidoException {
	        EstadoPedido estadoPedido = EstadoPedidoMapper.dtoToDomain(estadoPedidoDTO);
	        return EstadoPedidoMapper.domainToDto(estadoPedidoRepository.save(estadoPedido));
	    }
	    @Override
	    public void eliminar(Integer id) throws Exception {
	        Optional<EstadoPedido> tipoDocumentoExistente = estadoPedidoRepository.findById(id);
	        if (tipoDocumentoExistente.isPresent()) {
	        	estadoPedidoRepository.delete(tipoDocumentoExistente.get());
	        } else {
	            throw new Exception("No se encontró el estado pedido con el ID proporcionado");
	        }
	    }
		@Override
		public EstadoPedidoDTO guardar(EstadoPedidoDTO estadoPedidoDTO) throws Exception {
	        EstadoPedido estadoPedido = EstadoPedidoMapper.dtoToDomain(estadoPedidoDTO);
	        return EstadoPedidoMapper.domainToDto(estadoPedidoRepository.save(estadoPedido));
		}

		@Override
		public EstadoPedido buscarTipoDocumentoPorId(Integer id) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
	    public EstadoPedido buscarEstadoPedidoPorId(Integer id) throws EstadoPedidoException {
			ValidationUtility.integerIsNullOrLessZero(id,new EstadoPedidoException(EstadoPedidoServiceMessages.ID_VALIDO_MESSAGE));

	        return estadoPedidoRepository.findById(id).orElseThrow(() -> new EstadoPedidoException(String.format(EstadoPedidoServiceMessages.ESTADO_PEDIDO_NO_ENCONTRADO_POR_ID, id)));

	    }
}
