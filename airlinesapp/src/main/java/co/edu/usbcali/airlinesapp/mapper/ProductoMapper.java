package co.edu.usbcali.airlinesapp.mapper;
import java.util.List;
import java.util.stream.Collectors;
import co.edu.usbcali.airlinesapp.domain.Producto;
import co.edu.usbcali.airlinesapp.dtos.ProductoDTO;
import co.edu.usbcali.airlinesapp.request.CrearProductoRequest;
import co.edu.usbcali.airlinesapp.response.CrearProductoResponse;

public class ProductoMapper {
	
	public static ProductoDTO domainToDto(Producto producto){
        return ProductoDTO.builder()
                .id(producto.getId())
                .referencia(producto.getReferencia())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .unidadesDisponibles(producto.getUnidadesDisponibles())
                .precioUnitario(producto.getPrecioUnitario())
                .categoria(producto.getCategoria() == null ?
                            null : producto.getCategoria().getId())
                .build();
    }

    public static Producto dtoToDomain(ProductoDTO productoDto){
        return Producto.builder()
                .id(productoDto.getId())
                .referencia(productoDto.getReferencia())
                .nombre(productoDto.getNombre())
                .descripcion(productoDto.getDescripcion())
                .unidadesDisponibles(productoDto.getUnidadesDisponibles())
                .precioUnitario(productoDto.getPrecioUnitario())
                .build();
    }

    public static List<ProductoDTO> domainToDtoList(List<Producto> productos) {
        return productos.stream()
                .map(ProductoMapper::domainToDto)
                .collect(Collectors.toList());
    }

    public static List<Producto> dtoToDomainList(List<ProductoDTO> productosDtos) {
        return productosDtos.stream()
                .map(ProductoMapper::dtoToDomain)
                .collect(Collectors.toList());
    }

    
    public static Producto crearRequestToDomain(CrearProductoRequest crearProductoRequest) {
        return Producto.builder()
                .referencia(crearProductoRequest.getReferencia())
                .nombre(crearProductoRequest.getNombre())
                .descripcion(crearProductoRequest.getDescripcion())
                .precioUnitario(crearProductoRequest.getPrecioUnitario())
                .unidadesDisponibles(crearProductoRequest.getUnidadesDisponibles())
                .build();
    }

    public static CrearProductoResponse crearDomainToResponse(Producto producto) {
        return CrearProductoResponse.builder()
                .id(producto.getId())
                .referencia(producto.getReferencia())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precioUnitario(producto.getPrecioUnitario())
                .unidadesDisponibles(producto.getUnidadesDisponibles())
                .nombreCategoria((producto.getCategoria() == null) ?
                        null : producto.getCategoria().getNombre())
                .build();
    }

}
