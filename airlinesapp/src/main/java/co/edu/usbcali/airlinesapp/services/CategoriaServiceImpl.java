package co.edu.usbcali.airlinesapp.services;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import co.edu.usbcali.airlinesapp.domain.Categoria;
import co.edu.usbcali.airlinesapp.dtos.CategoriaDTO;
import co.edu.usbcali.airlinesapp.exceptions.CategoriaException;
import co.edu.usbcali.airlinesapp.mapper.CategoriaMapper;
import co.edu.usbcali.airlinesapp.repository.ICategoriaRepository;
import co.edu.usbcali.airlinesapp.utils.CategoriaServiceMessages;
import co.edu.usbcali.airlinesapp.utils.ClienteServiceMessages;
import co.edu.usbcali.airlinesapp.utils.ValidationsUtil;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	
	private final ICategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<CategoriaDTO> obtenerTodos() {
        return CategoriaMapper.domainToDtoList(categoriaRepository.findAllByOrderByNombreAsc());
    }

    @Override
    public CategoriaDTO buscarPorId(Integer id) throws Exception {
    	
    	ValidationsUtil.integerIsNullOrLessZero(id, ClienteServiceMessages.ID_VALIDO_MSG);
    	
    	Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        if (categoriaOptional.isPresent()) {
            Categoria categoria = categoriaOptional.get();
            return CategoriaMapper.domainToDto(categoria);
        } else {
            throw new CategoriaException(String.format(ClienteServiceMessages.CLIENTE_NO_ENCONTRADO_POR_ID, id));
        }
    }

    @Override
    public Categoria buscarTipoDocumentoPorId(Integer id) throws Exception {
        ValidationsUtil.integerIsNullOrLessZero(id, ClienteServiceMessages.ID_VALIDO_MSG);
        
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        } else {
            throw new CategoriaException(String.format(ClienteServiceMessages.CLIENTE_NO_ENCONTRADO_POR_ID, id));
        }
    }

    @Override
    public CategoriaDTO guardar(CategoriaDTO categoriaDTO) throws Exception {
        validarCategoria(categoriaDTO, true);
        Categoria categoria = CategoriaMapper.dtoToDomain(categoriaDTO);
        return CategoriaMapper.domainToDto(categoriaRepository.save(categoria));
    }
    
    @Override
    public Categoria buscarCategoriaPorId(Integer id) throws Exception {
        ValidationsUtil.integerIsNullOrLessZero(id, CategoriaServiceMessages.ID_VALIDO_MSG);
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaException(
                        String.format(CategoriaServiceMessages.CATEGORIA_NO_ENCONTRADA_POR_ID, id)
                ));
    }


    @Override
    public CategoriaDTO actualizar(CategoriaDTO categoriaDTO) throws Exception {
    	validarCategoria(categoriaDTO, false);

        //Valiar si existe la categoria con ese nombre y otro Id
        boolean existePorNombreYOtroId = categoriaRepository
                .existsByNombreIgnoreCaseAndIdNot(categoriaDTO.getNombre(), categoriaDTO.getId());
        if(existePorNombreYOtroId) throw new Exception(
                String.format(CategoriaServiceMessages.EXISTE_POR_NOMBRE, categoriaDTO.getNombre()));

        Categoria categoria = buscarCategoriaPorId(categoriaDTO.getId());

        //Modificar atributos
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        return CategoriaMapper.domainToDto(categoriaRepository.save(categoria));
    }


    private void validarCategoria(CategoriaDTO categoriaDTO, boolean esGuardado) throws Exception {
        if(!esGuardado) {
            ValidationsUtil.isNull(categoriaDTO.getId(), CategoriaServiceMessages.ID_REQUERIDO);
        }
        
        
        
        ValidationsUtil.isNull(categoriaDTO, CategoriaServiceMessages.CATEGORIA_NULA);
        ValidationsUtil.stringIsNullOrBlank(categoriaDTO.getNombre(), CategoriaServiceMessages.NOMBRE_REQUERIDO);
        
        
        //Validar si existe la categoria con ese nombre
        
        boolean existePorNombre = categoriaRepository.existsByNombreIgnoreCase(categoriaDTO.getNombre());
        if(existePorNombre)throw new Exception(String.format(CategoriaServiceMessages.EXISTE_POR_NOMBRE, categoriaDTO.getId()));
        
        ValidationsUtil.stringIsNullOrBlank(categoriaDTO.getDescripcion(), CategoriaServiceMessages.DESCRIPCION_REQUERIDA);
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(id);
        if (categoriaExistente.isPresent()) {
            categoriaRepository.delete(categoriaExistente.get());
        } else {
            throw new Exception("No se encontró la categoría con el ID proporcionado");
        }
    }
    
    @Override
    public List<CategoriaDTO> buscarPorNombreLike(String nombre) throws Exception {
        ValidationsUtil.stringIsNullOrBlank(nombre, CategoriaServiceMessages.NOMBRE_REQUERIDO);
        return CategoriaMapper.domainToDtoList(categoriaRepository.findByNombreLikeIgnoreCase("%"+nombre+"%"));
    }


}
