package co.edu.usbcali.airlinesapp.services;

import java.util.List;

import co.edu.usbcali.airlinesapp.domain.Cliente;
import co.edu.usbcali.airlinesapp.dtos.ClienteDTO;
import co.edu.usbcali.airlinesapp.exceptions.ClienteException;
import co.edu.usbcali.airlinesapp.request.CrearClienteRequest;
import co.edu.usbcali.airlinesapp.response.CrearClienteResponse;

public interface ClienteService {
	
	List<ClienteDTO> obtenerTodos();
    ClienteDTO buscarPorId(Integer id) throws Exception;
    ClienteDTO guardar(ClienteDTO clienteDTO) throws Exception;
    ClienteDTO actualizar(ClienteDTO clienteDTO) throws Exception;
    CrearClienteResponse crearCliente( CrearClienteRequest crear) throws Exception;
    void eliminar(Integer id) throws Exception;
    Cliente buscarClientePorId(Integer id) throws ClienteException;
   
}
