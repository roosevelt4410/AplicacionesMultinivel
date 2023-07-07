package co.edu.usbcali.airlinesapp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.usbcali.airlinesapp.domain.Categoria;
import co.edu.usbcali.airlinesapp.dtos.CategoriaDTO;

public class CategoriaMapper {
	
	public static CategoriaDTO domainToDto(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .build();
    }

    public static Categoria dtoToDomain(CategoriaDTO categoriaDTO) {
        return Categoria.builder()
                .id(categoriaDTO.getId())
                .nombre(categoriaDTO.getNombre())
                .descripcion(categoriaDTO.getDescripcion())
                .build();
    }

    public static List<CategoriaDTO> domainToDtoList(List<Categoria> categorias) {
        return categorias.stream()
                .map(CategoriaMapper::domainToDto)
                .collect(Collectors.toList());
    }

    public static List<Categoria> dtoToDomainList(List<CategoriaDTO> categoriasDtos) {
        return categoriasDtos.stream()
                .map(CategoriaMapper::dtoToDomain)
                .collect(Collectors.toList());
    }
}
