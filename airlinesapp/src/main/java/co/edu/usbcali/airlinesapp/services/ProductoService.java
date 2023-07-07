package co.edu.usbcali.airlinesapp.services;

import co.edu.usbcali.airlinesapp.domain.Producto;
import co.edu.usbcali.airlinesapp.dtos.ProductoDTO;
import co.edu.usbcali.airlinesapp.exceptions.CategoriaException;
import co.edu.usbcali.airlinesapp.exceptions.ProductoException;
import co.edu.usbcali.airlinesapp.request.CrearProductoRequest;
import co.edu.usbcali.airlinesapp.response.CrearProductoResponse;

public interface ProductoService {

	ProductoDTO buscarPorId(Integer id) throws Exception;
	Producto buscarProductoPorId(Integer id) throws ProductoException;
	
    CrearProductoResponse guardarNuevo(CrearProductoRequest crearProductoRequest) throws Exception;

    ProductoDTO actualizar(ProductoDTO productoDTO) throws CategoriaException, ProductoException;

    void eliminar(Integer id) throws Exception;
    

}
