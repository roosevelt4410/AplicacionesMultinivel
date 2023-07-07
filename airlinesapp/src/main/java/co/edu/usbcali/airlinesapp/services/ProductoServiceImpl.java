package co.edu.usbcali.airlinesapp.services;



import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.usbcali.airlinesapp.domain.Categoria;
import co.edu.usbcali.airlinesapp.domain.Producto;
import co.edu.usbcali.airlinesapp.dtos.ProductoDTO;
import co.edu.usbcali.airlinesapp.exceptions.CategoriaException;
import co.edu.usbcali.airlinesapp.exceptions.ProductoException;
import co.edu.usbcali.airlinesapp.mapper.ProductoMapper;
import co.edu.usbcali.airlinesapp.repository.IProductoRepository;
import co.edu.usbcali.airlinesapp.request.CrearProductoRequest;
import co.edu.usbcali.airlinesapp.response.CrearProductoResponse;
import co.edu.usbcali.airlinesapp.utils.CategoriaServiceMessages;
import co.edu.usbcali.airlinesapp.utils.ClienteServiceMessages;
import co.edu.usbcali.airlinesapp.utils.ProductoServiceMessage;
import co.edu.usbcali.airlinesapp.utils.ValidationsUtil;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	private final IProductoRepository productoRepository;

    private final CategoriaService categoriaService;

    public ProductoServiceImpl(IProductoRepository productoRepository, CategoriaService categoriaService) {
        this.productoRepository = productoRepository;
        this.categoriaService = categoriaService;
    }


	@Override
	public CrearProductoResponse guardarNuevo(CrearProductoRequest crearProductoRequest) throws Exception {
		// Ya se hizo validación de campos
        // Buscar la Categoría en Base de Datos
        Categoria categoria = categoriaService.buscarCategoriaPorId(crearProductoRequest.getCategoria());

        // Validar si ya existe la referencia en la Categoría
        boolean existeReferenciaEnCategoria = productoRepository.existsByCategoriaIdAndReferencia(crearProductoRequest.getCategoria(), crearProductoRequest.getReferencia());
        if (existeReferenciaEnCategoria) throw new Exception(
                String.format(ProductoServiceMessage.EXISTE_REFERENCIA_EN_CATEGORIA,
                        crearProductoRequest.getReferencia(), (categoria.getId()+" - "+categoria.getNombre()))
        );

        // Mapear del request al Producto
        Producto producto = ProductoMapper.crearRequestToDomain(crearProductoRequest);

        // Inyectar la categoría buscada al Objeto del dominio Producto
        producto.setCategoria(categoria);

        // 1. Guarda el nuevo producto
        // 2. Mapea el producto al Response
        return ProductoMapper.crearDomainToResponse(productoRepository.save(producto));
	}
	
	
	@Override
    public Producto buscarProductoPorId(Integer id) throws ProductoException {
        

		return productoRepository
		        .findById(id)
		        .orElseThrow(() -> new ProductoException(String.format(ProductoServiceMessage.PRODUCTO_NO_ENCONTRADA_POR_ID, id)));

    }
    
	@Override
    public ProductoDTO buscarPorId(Integer id) throws Exception {
    	
    	ValidationsUtil.integerIsNullOrLessZero(id, ClienteServiceMessages.ID_VALIDO_MSG);
    	
    	Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
        	Producto producto = productoOptional.get();
            return ProductoMapper.domainToDto(producto);
        } else {
            throw new CategoriaException(String.format(ClienteServiceMessages.CLIENTE_NO_ENCONTRADO_POR_ID, id));
        }
    }


	 @Override
	    public ProductoDTO actualizar(ProductoDTO productoDTO) throws CategoriaException, ProductoException {
	        try {
				validarProducto(productoDTO, true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        Producto producto = ProductoMapper.dtoToDomain(productoDTO);
	        try {
				producto.setCategoria(categoriaService.buscarCategoriaPorId(productoDTO.getCategoria()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        return ProductoMapper.domainToDto(productoRepository.save(producto));
	    }



    private void validarProducto(ProductoDTO productoDTO, boolean esGuardado) throws Exception {
        if(!esGuardado) {
            ValidationsUtil.isNull(productoDTO.getId(), CategoriaServiceMessages.ID_REQUERIDO);
        }
        
        
        
        ValidationsUtil.isNull(productoDTO, CategoriaServiceMessages.CATEGORIA_NULA);
        ValidationsUtil.stringIsNullOrBlank(productoDTO.getNombre(), CategoriaServiceMessages.NOMBRE_REQUERIDO);
        
        
        //Validar si existe la categoria con ese nombre
        
//        BOOLEAN EXISTEPORNOMBRE = PRODUCTOREPOSITORY.EXISTSBYNOMBREIGNORECASE(PRODUCTODTO.GETNOMBRE());
//        IF(EXISTEPORNOMBRE)THROW NEW EXCEPTION(STRING.FORMAT(CATEGORIASERVICEMESSAGES.EXISTE_POR_NOMBRE, PRODUCTODTO.GETID()));
        
        ValidationsUtil.stringIsNullOrBlank(productoDTO.getDescripcion(), CategoriaServiceMessages.DESCRIPCION_REQUERIDA);
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if (productoExistente.isPresent()) {
            productoRepository.delete(productoExistente.get());
        } else {
            throw new Exception("No se encontró la categoría con el ID proporcionado");
        }
    }



}
