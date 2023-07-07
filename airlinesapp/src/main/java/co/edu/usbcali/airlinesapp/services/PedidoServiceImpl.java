package co.edu.usbcali.airlinesapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.usbcali.airlinesapp.domain.Cliente;
import co.edu.usbcali.airlinesapp.domain.Pedido;
import co.edu.usbcali.airlinesapp.dtos.PedidoDTO;
import co.edu.usbcali.airlinesapp.exceptions.ClienteException;
import co.edu.usbcali.airlinesapp.exceptions.EstadoPedidoException;
import co.edu.usbcali.airlinesapp.exceptions.PedidoException;
import co.edu.usbcali.airlinesapp.mapper.PedidoMapper;
import co.edu.usbcali.airlinesapp.repository.IClienteRepository;
import co.edu.usbcali.airlinesapp.repository.IPedidoRepository;
import co.edu.usbcali.airlinesapp.utils.PedidoServiceMessage;
import co.edu.usbcali.airlinesapp.utils.ValidationUtility;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{

	 	private final IPedidoRepository pedidoRepository;

	    private final ClienteService clienteService;

	    private final EstadoPedidoService estadoPedidoService;
	    
	    private final IClienteRepository clienteRepository;


	    @Override
	    public List<PedidoDTO> obtenerTodos() {
	        return PedidoMapper.domainToDtoList(pedidoRepository.findAll());
	    }

	    @Override
	    public PedidoDTO buscarPorId(Integer id) throws PedidoException {
	        ValidationUtility.integerIsNullOrLessZero(id, new PedidoException(PedidoServiceMessage.ID_NO_VALIDO_MSG));
	        return pedidoRepository.findById(id)
	                .map(PedidoMapper::domainToDto)
	                .orElseThrow(() -> new PedidoException(String.format(PedidoServiceMessage.PEDIDO_NO_ENCONTRADA_POR_ID, id)));
	    }

	    @Override
	    public Pedido buscarPedidoPorId(Integer id) throws PedidoException {
	        ValidationUtility.integerIsNullOrLessZero(id, new PedidoException(PedidoServiceMessage.ID_NO_VALIDO_MSG));
	        return pedidoRepository
	                .findById(id)
	                .orElseThrow(() -> new PedidoException(String
	                        .format(PedidoServiceMessage.PEDIDO_NO_ENCONTRADA_POR_ID, id)));
	    }

	    @Override
	    public PedidoDTO guardar(PedidoDTO pedidoDTO) throws ClienteException, EstadoPedidoException, PedidoException {
	        validarPedido(pedidoDTO, false);

	        Pedido pedido = PedidoMapper.dtoToDomain(pedidoDTO);
	        Optional<Cliente> clienteOptional = clienteRepository.findById(pedidoDTO.getClienteId());
	        Cliente cliente = clienteOptional.orElse(null);
	        pedido.setCliente(cliente);
	        
	        
	        pedido.setEstadoPedido(estadoPedidoService.buscarEstadoPedidoPorId(pedidoDTO.getEstadoPedidoId()));

	        return PedidoMapper.domainToDto(pedidoRepository.save(pedido));
	    }

	    @Override
	    public PedidoDTO actualizar(PedidoDTO pedidoDTO) throws ClienteException, EstadoPedidoException, PedidoException {
	        validarPedido(pedidoDTO, true);

	        Pedido pedido = PedidoMapper.dtoToDomain(pedidoDTO);
	        pedido.setCliente(clienteService.buscarClientePorId(pedidoDTO.getClienteId()));
	        pedido.setEstadoPedido(estadoPedidoService.buscarEstadoPedidoPorId(pedidoDTO.getEstadoPedidoId()));

	        return PedidoMapper.domainToDto(pedidoRepository.save(pedido));
	    }

	    private void validarPedido(PedidoDTO pedidoDTO, Boolean esActualizar) throws PedidoException {
	        if (Boolean.TRUE.equals(esActualizar)){
	            ValidationUtility.isNull(pedidoDTO.getId(),
	                    new PedidoException(PedidoServiceMessage.ID_REQUERIDO));
	        }

	        ValidationUtility.isNull(pedidoDTO,
	                new PedidoException(PedidoServiceMessage.PEDIDO_NULO));
	        ValidationUtility.isNull(pedidoDTO.getFecha(),
	                new PedidoException(PedidoServiceMessage.FECHA_REQUERIDA));
	        ValidationUtility.bigDecimalIsNullOrLessZero(pedidoDTO.getTotal(),
	                new PedidoException(PedidoServiceMessage.TOTAL_REQUERIDO));
	        ValidationUtility.integerIsNullOrLessZero(pedidoDTO.getClienteId(),
	                new PedidoException(PedidoServiceMessage.CLIENTE_ID_REQUERIDO));
	        ValidationUtility.integerIsNullOrLessZero(pedidoDTO.getEstadoPedidoId(),
	                new PedidoException(PedidoServiceMessage.ESTADO_PEDIDO_ID_REQUERIDO));
	    }
}
