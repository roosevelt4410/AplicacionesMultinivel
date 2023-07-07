package co.edu.usbcali.airlinesapp.mapper;

import java.util.List;
import java.util.stream.Collectors;
import co.edu.usbcali.airlinesapp.domain.Cliente;
import co.edu.usbcali.airlinesapp.dtos.ClienteDTO;
import co.edu.usbcali.airlinesapp.request.CrearClienteRequest;
import co.edu.usbcali.airlinesapp.response.CrearClienteResponse;

public class ClienteMapper {
	public static ClienteDTO domainToDto(Cliente cliente){
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nombres(cliente.getNombres())
                .apellidos(cliente.getApellidos())
                .documento(cliente.getDocumento())
                .estado(cliente.getEstado())
                .tipoDocumento((cliente.getTipoDocumento() == null?
                        null : cliente.getTipoDocumento().getId()))
                .build();
    }

    public static Cliente dtoToDomain(ClienteDTO clienteDto){
        return Cliente.builder()
                .id(clienteDto.getId())
                .nombres(clienteDto.getNombres())
                .apellidos(clienteDto.getApellidos())
                .documento(clienteDto.getDocumento())
                .estado(clienteDto.getEstado())
                .build();
    }

    public static List<ClienteDTO> domainToDtoList(List<Cliente> clientes) {
        return clientes.stream()
                .map(ClienteMapper::domainToDto)
                .collect(Collectors.toList());
    }

    public static List<Cliente> dtoToDomainList(List<ClienteDTO> clientesDtos) {
        return clientesDtos.stream()
                .map(ClienteMapper::dtoToDomain)
                .collect(Collectors.toList());
    }
    
    public static Cliente crearRequestToDomain(CrearClienteRequest crearClienteRequest) {
        return Cliente.builder()
                .nombres(crearClienteRequest.getNombres())
                .apellidos(crearClienteRequest.getApellidos())
                .documento(crearClienteRequest.getDocumento())
                .estado(crearClienteRequest.getEstado())
                .build();
    }

    public static CrearClienteResponse crearDomainToResponse(Cliente cliente) {
        return CrearClienteResponse.builder()
                .id(cliente.getId())
                .nombres(cliente.getNombres())
                .apellidos(cliente.getApellidos())
                .documento(cliente.getDocumento())
                .estado(cliente.getEstado())
                .tipoDocumentoDescripcion((cliente.getTipoDocumento() == null) ? null :
                        cliente.getTipoDocumento().getDescripcion())
                .build();
    }
}
