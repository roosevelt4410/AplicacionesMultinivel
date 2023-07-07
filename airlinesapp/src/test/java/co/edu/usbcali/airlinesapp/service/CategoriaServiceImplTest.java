package co.edu.usbcali.airlinesapp.service;

import org.springframework.boot.test.context.SpringBootTest;

import co.edu.usbcali.airlinesapp.mocks.CategoriaMockTest;
import co.edu.usbcali.airlinesapp.repository.ICategoriaRepository;
import co.edu.usbcali.airlinesapp.services.CategoriaServiceImpl;
import co.edu.usbcali.airlinesapp.utils.CategoriaServiceMessages;
import co.edu.usbcali.airlinesapp.domain.Categoria;
import co.edu.usbcali.airlinesapp.dtos.CategoriaDTO;
import co.edu.usbcali.airlinesapp.mapper.CategoriaMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@SpringBootTest
public class CategoriaServiceImplTest {
	
	@InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Mock
    private ICategoriaRepository categoriaRepository;


    @Test
    void obtenerTodasLasCategorias_OK() {
        given(categoriaRepository.findAllByOrderByNombreAsc()).willReturn(
                CategoriaMockTest.CATEGORIAS_LIST
        );

        List<CategoriaDTO> obtenerTodas = categoriaService.obtenerTodos();

        assertEquals(2, obtenerTodas.size());
        assertEquals(CategoriaMockTest.ID_UNO, obtenerTodas.get(0).getId());
    }

    @Test
    void buscarPorId_NOK() {
        Exception exception = assertThrows(Exception.class, () ->
                categoriaService.buscarPorId(2));

        String expectedMessage = String.format(
                CategoriaServiceMessages.CATEGORIA_NO_ENCONTRADA_POR_ID, 2);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void buscarPorId_OK() throws Exception {
        given(categoriaRepository.findById(1)).willReturn(
                Optional.of(CategoriaMockTest.CATEGORIA_UNO)
        );

        CategoriaDTO categoriaDTO = categoriaService.buscarPorId(1);

        assertNotNull(categoriaDTO);
        assertEquals(CategoriaMockTest.NOMBRE_UNO, categoriaDTO.getNombre());
    }

    @Test
    void guardarOk() throws Exception{
        CategoriaDTO catToSave = CategoriaDTO.builder()
                .nombre("C2").descripcion("C2")
                .build();

        given(categoriaRepository.save(any(Categoria.class)))
                .willReturn(Categoria.builder().id(2).nombre("C2").descripcion("C2").build());
        CategoriaDTO categoriaResult = categoriaService.guardar(catToSave);
        assertNotNull(categoriaResult);
        assertEquals(2, categoriaResult.getId());
    }

    @Test
    void guardarError() {
        CategoriaDTO catToSave =
                CategoriaMapper.domainToDto(CategoriaMockTest.CATEGORIA_UNO);

        given(categoriaRepository.existsByNombreIgnoreCase(
                CategoriaMockTest.NOMBRE_UNO
        )).willReturn(true);

        Exception exception= assertThrows(Exception.class, ()->categoriaService.guardar(catToSave));

        String message = String.format(CategoriaServiceMessages.EXISTE_POR_NOMBRE, CategoriaMockTest.NOMBRE_UNO);

        assertEquals(message, exception.getMessage());
    }


}
