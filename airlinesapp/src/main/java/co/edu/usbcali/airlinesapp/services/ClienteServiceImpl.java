package co.edu.usbcali.airlinesapp.services;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import co.edu.usbcali.airlinesapp.domain.Cliente;

import co.edu.usbcali.airlinesapp.domain.TipoDocumento;
import co.edu.usbcali.airlinesapp.dtos.ClienteDTO;
import co.edu.usbcali.airlinesapp.exceptions.ClienteException;

import co.edu.usbcali.airlinesapp.mapper.ClienteMapper;
import co.edu.usbcali.airlinesapp.repository.IClienteRepository;
import co.edu.usbcali.airlinesapp.request.CrearClienteRequest;
import co.edu.usbcali.airlinesapp.response.CrearClienteResponse;
import co.edu.usbcali.airlinesapp.utils.ClienteServiceMessages;
import co.edu.usbcali.airlinesapp.utils.ValidationUtility;
import co.edu.usbcali.airlinesapp.utils.ValidationsUtil;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	private final IClienteRepository clienteRepository;
    private final TipoDocumentoService tipoDocumentoService;

    public ClienteServiceImpl(
    		IClienteRepository clienteRepository,
            TipoDocumentoService tipoDocumentoService) {
        this.clienteRepository = clienteRepository;
        this.tipoDocumentoService = tipoDocumentoService;
    }

    @Override
    public List<ClienteDTO> obtenerTodos() {
        return ClienteMapper.domainToDtoList(clienteRepository.findAll());
    }

    @Override
    public ClienteDTO buscarPorId(Integer id) throws Exception {
        ValidationsUtil.integerIsNullOrLessZero(id, ClienteServiceMessages.ID_REQUERIDO);

        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            return ClienteMapper.domainToDto(cliente);
        } else {
            throw new ClienteException(String.format(ClienteServiceMessages.CLIENTE_NO_ENCONTRADO_POR_ID, id));
        }
    }


    @Override
    public ClienteDTO guardar(ClienteDTO clienteDTO) throws Exception {
    	validarCliente(clienteDTO, true);
        Cliente cliente = ClienteMapper.dtoToDomain(clienteDTO);
        TipoDocumento tipoDocumento = tipoDocumentoService.buscarTipoDocumentoPorId(
                clienteDTO.getTipoDocumento());
        cliente.setTipoDocumento(tipoDocumento);

        return ClienteMapper.domainToDto(clienteRepository.save(cliente));
    }

    @Override
    public ClienteDTO actualizar(ClienteDTO clienteDTO) throws Exception {
        validarCliente(clienteDTO, false);
        Cliente cliente = ClienteMapper.dtoToDomain(clienteDTO);
        TipoDocumento tipoDocumento = tipoDocumentoService.buscarTipoDocumentoPorId(
                clienteDTO.getTipoDocumento());
        cliente.setTipoDocumento(tipoDocumento);

        Cliente clienteActualizado = clienteRepository.save(cliente);
        return ClienteMapper.domainToDto(clienteActualizado);
    }

    private void validarCliente(ClienteDTO clienteDTO, boolean esGuardado) throws Exception{
    	if(!esGuardado) {
            ValidationsUtil.isNull(clienteDTO.getId(), ClienteServiceMessages.ID_REQUERIDO);
        }
        ValidationsUtil.isNull(clienteDTO, ClienteServiceMessages.CLIENTE_NULO);
        ValidationsUtil.stringIsNullOrBlank(clienteDTO.getNombres(), ClienteServiceMessages.NOMBRES_REQUERIDOS);
        ValidationsUtil.stringIsNullOrBlank(clienteDTO.getApellidos(), ClienteServiceMessages.APELLIDOS_REQUERIDOS);
        ValidationsUtil.stringIsNullOrBlank(clienteDTO.getDocumento(), ClienteServiceMessages.DOCUMENTO_REQUERIDO);
        ValidationsUtil.stringIsNullOrBlank(clienteDTO.getEstado(), ClienteServiceMessages.ESTADO_REQUERIDO);
        ValidationsUtil.lenghtString(clienteDTO.getEstado(), 1, ClienteServiceMessages.ESTADO_LENGHT);
        ValidationsUtil.integerIsNullOrLessZero(clienteDTO.getTipoDocumento(), ClienteServiceMessages.TIPO_DOCUMENTO_ID_REQUERIDO);
    }

	@Override
	public CrearClienteResponse crearCliente(CrearClienteRequest crearClienteRequest) throws Exception {
	
        Cliente cliente = ClienteMapper.crearRequestToDomain(crearClienteRequest);

   
        TipoDocumento tipoDocumento = tipoDocumentoService.buscarTipoDocumentoPorId(
                crearClienteRequest.getTipoDocumentoId());

        
        boolean existePorTipoYDocumento = clienteRepository.existsByTipoDocumentoIdAndDocumento(
                crearClienteRequest.getTipoDocumentoId(), crearClienteRequest.getDocumento());
        if (existePorTipoYDocumento) {
            throw new Exception(String.format(
                    ClienteServiceMessages.TIPO_DOCUMENTO_ID_REQUERIDO,
                    tipoDocumento.getDescripcion(),
                    crearClienteRequest.getDocumento()
            ));
        }

        cliente.setTipoDocumento(tipoDocumento);
        return ClienteMapper.crearDomainToResponse(clienteRepository.save(cliente));
	}
	
	@Override
    public void eliminar(Integer id) throws Exception {
        Optional<Cliente> tipoDocumentoExistente = clienteRepository.findById(id);
        if (tipoDocumentoExistente.isPresent()) {
        	clienteRepository.delete(tipoDocumentoExistente.get());
        } else {
            throw new Exception("No se encontró la categoría con el ID proporcionado");
        }
    }

	@Override
	public Cliente buscarClientePorId(Integer id) throws ClienteException {
		 ValidationUtility.integerIsNullOrLessZero(id, new ClienteException(ClienteServiceMessages.ID_REQUERIDO));

	        return clienteRepository
	                .findById(id)
	                .orElseThrow(() -> new ClienteException(String
	                        .format(ClienteServiceMessages.CLIENTE_NO_ENCONTRADO_POR_ID, id))
	                );
	}
	
	

}
