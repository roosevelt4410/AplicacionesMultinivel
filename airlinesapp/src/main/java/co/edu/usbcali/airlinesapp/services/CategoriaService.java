package co.edu.usbcali.airlinesapp.services;

import java.util.List;

import co.edu.usbcali.airlinesapp.domain.Categoria;
import co.edu.usbcali.airlinesapp.dtos.CategoriaDTO;

public interface CategoriaService {

	
	List<CategoriaDTO> obtenerTodos();

    CategoriaDTO buscarPorId(Integer id) throws Exception;

    Categoria buscarTipoDocumentoPorId(Integer id) throws Exception;
	
    CategoriaDTO guardar(CategoriaDTO categoriaDTO) throws Exception;

    CategoriaDTO actualizar( CategoriaDTO categoriaDTO) throws Exception;

    void eliminar(Integer id) throws Exception;
    
    public Categoria buscarCategoriaPorId(Integer id) throws Exception;
    
    List<CategoriaDTO> buscarPorNombreLike(String nombre) throws Exception;
}
