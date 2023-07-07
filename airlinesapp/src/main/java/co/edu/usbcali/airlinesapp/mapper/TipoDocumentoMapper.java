package co.edu.usbcali.airlinesapp.mapper;

import java.util.List;
import java.util.stream.Collectors;
import co.edu.usbcali.airlinesapp.domain.TipoDocumento;
import co.edu.usbcali.airlinesapp.dtos.TipoDocumentoDTO;

public class TipoDocumentoMapper {

	
	public static TipoDocumentoDTO domainToDto(TipoDocumento tipoDocumento){
        return TipoDocumentoDTO.builder()
                .id(tipoDocumento.getId())
                .descripcion(tipoDocumento.getDescripcion())
                .build();
    }

    public static TipoDocumento dtoToDomain(TipoDocumentoDTO tipoDocumentoDTO){
        return TipoDocumento.builder()
                .id(tipoDocumentoDTO.getId())
                .descripcion(tipoDocumentoDTO.getDescripcion())
                .build();
    }

    public static List<TipoDocumentoDTO> domainToDtoList(List<TipoDocumento> tipoDocumentos) {
        return tipoDocumentos.stream()
                .map(TipoDocumentoMapper::domainToDto)
                .collect(Collectors.toList());
    }

    public static List<TipoDocumento> dtoToDomainList(List<TipoDocumentoDTO> tipoDocumentosDtos) {
        return tipoDocumentosDtos.stream()
                .map(TipoDocumentoMapper::dtoToDomain)
                .collect(Collectors.toList());
    }
}
