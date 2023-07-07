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

import co.edu.usbcali.airlinesapp.dtos.ClienteDTO;
import co.edu.usbcali.airlinesapp.mapper.ClienteMapper;
import co.edu.usbcali.airlinesapp.repository.IClienteRepository;
import co.edu.usbcali.airlinesapp.request.CrearClienteRequest;
import co.edu.usbcali.airlinesapp.response.CrearClienteResponse;
import co.edu.usbcali.airlinesapp.services.ClienteService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin("*")
public class ClienteController {

	private final ClienteService clienteService;
	private final IClienteRepository clienteRepository;

    public ClienteController(ClienteService clienteService, IClienteRepository clienteRepository) {
        this.clienteService = clienteService;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/nuevo")
    CrearClienteResponse nuevoCliente(@RequestBody @Valid CrearClienteRequest crearClienteRequest) throws Exception {
        return clienteService.crearCliente(crearClienteRequest);
    }
    
    
    @GetMapping("/listarClientes")
	public List<ClienteDTO> findAll(){
		return ClienteMapper.domainToDtoList(clienteRepository.findAll());
	}
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            clienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    @PostMapping("/guardar")
    public ResponseEntity<ClienteDTO> save(@RequestBody ClienteDTO ClienteDTO) {
        try {
            ClienteDTO savedClienteDTO = clienteService.guardar(ClienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedClienteDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ClienteDTO> actualizar(@RequestBody ClienteDTO ClienteDTO) {
        try {
            ClienteDTO updatedClienteDTO = clienteService.actualizar(ClienteDTO);
            return ResponseEntity.ok(updatedClienteDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
