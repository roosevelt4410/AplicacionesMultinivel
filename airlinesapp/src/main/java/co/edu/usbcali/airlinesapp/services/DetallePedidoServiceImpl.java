package co.edu.usbcali.airlinesapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import co.edu.usbcali.airlinesapp.domain.DetallesPedido;
import co.edu.usbcali.airlinesapp.dtos.DetallePedidoDTO;
import co.edu.usbcali.airlinesapp.exceptions.DetallePedidoException;
import co.edu.usbcali.airlinesapp.exceptions.PedidoException;
import co.edu.usbcali.airlinesapp.exceptions.ProductoException;
import co.edu.usbcali.airlinesapp.mapper.DetallePedidoMapper;
import co.edu.usbcali.airlinesapp.repository.IDetallePedidoRepository;
import co.edu.usbcali.airlinesapp.utils.DetallePedidoServiceMessage;
import co.edu.usbcali.airlinesapp.utils.ValidationUtility;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@RequestMapping("/detallepedido")
@CrossOrigin("*")
public class DetallePedidoServiceImpl implements DetallePedidoService{
	
	private final IDetallePedidoRepository detallePedidoRepository;


    private final PedidoService pedidoService;

    private final ProductoService productoService;

    @Override
    public List<DetallePedidoDTO> obtenerTodos() {
        return DetallePedidoMapper.domainToDtoList(detallePedidoRepository.findAll());
    }

    @Override
    public DetallePedidoDTO buscarPorId(Integer id) throws DetallePedidoException {
        ValidationUtility.integerIsNullOrLessZero(id,
                new DetallePedidoException(DetallePedidoServiceMessage.ID_NO_VALIDO_MSG));

        return detallePedidoRepository
                .findById(id)
                .map(DetallePedidoMapper::domainToDto)
                .orElseThrow(() -> new DetallePedidoException(String
                        .format(DetallePedidoServiceMessage.DETALLE_PEDIDO_NO_ENCONTRADA_POR_ID, id))
                );
    }

    @Override
    public DetallePedidoDTO guardar(DetallePedidoDTO detallePedidoDTO)
            throws PedidoException, ProductoException, DetallePedidoException {
        validarDetallePedido(detallePedidoDTO, false);

        DetallesPedido detallePedido = DetallePedidoMapper.dtoToDomain(detallePedidoDTO);
        detallePedido.setPedido(pedidoService.buscarPedidoPorId(detallePedidoDTO.getPedidoId()));
        detallePedido.setProducto(productoService.buscarProductoPorId(detallePedidoDTO.getProductoId()));

        return DetallePedidoMapper.domainToDto(detallePedidoRepository.save(detallePedido));
    }

    @Override
    public DetallePedidoDTO actualizar(DetallePedidoDTO detallePedidoDTO)
            throws PedidoException, ProductoException, DetallePedidoException {
        validarDetallePedido(detallePedidoDTO, true);

        DetallesPedido detallePedido = DetallePedidoMapper.dtoToDomain(detallePedidoDTO);
        detallePedido.setPedido(pedidoService.buscarPedidoPorId(detallePedidoDTO.getPedidoId()));
        detallePedido.setProducto(productoService.buscarProductoPorId(detallePedidoDTO.getProductoId()));

        return DetallePedidoMapper.domainToDto(detallePedidoRepository.save(detallePedido));
    }

    @Override
    public List<Object> guardarListado(List<DetallePedidoDTO> listDetallePedido) throws PedidoException, ProductoException, DetallePedidoException {
        List<Object> response = new ArrayList<>();
        for (DetallePedidoDTO detallePedidoDTO : listDetallePedido) {
            try {
                DetallePedidoDTO detallePedido = guardar(detallePedidoDTO);
                response.add(detallePedido); // Agregar el detalle guardado a la lista de respuesta
            } catch (Exception e) {
                response.add(e.getMessage()); // Agregar el mensaje de error a la lista de respuesta
            }
        }
        return response;
    }

    private void validarDetallePedido(DetallePedidoDTO detallePedidoDTO, Boolean esActualizar)
            throws DetallePedidoException {
        if (Boolean.TRUE.equals(esActualizar)){
            ValidationUtility.isNull(detallePedidoDTO.getId(),
                    new DetallePedidoException(DetallePedidoServiceMessage.ID_REQUERIDO));
        }

        ValidationUtility.isNull(detallePedidoDTO,
                new DetallePedidoException(DetallePedidoServiceMessage.DETALLE_PEDIDO_NULO));
        ValidationUtility.bigDecimalIsNullOrLessZero(detallePedidoDTO.getCantidad(),
                new DetallePedidoException(DetallePedidoServiceMessage.CANTIDAD_REQUERIDA));
        ValidationUtility.bigDecimalIsNullOrLessZero(detallePedidoDTO.getValor(),
                new DetallePedidoException(DetallePedidoServiceMessage.VALOR_REQUERIDO));
        ValidationUtility.integerIsNullOrLessZero(detallePedidoDTO.getPedidoId(),
                new DetallePedidoException(DetallePedidoServiceMessage.PEDIDO_ID_REQUERIDO));
        ValidationUtility.integerIsNullOrLessZero(detallePedidoDTO.getProductoId(),
                new DetallePedidoException(DetallePedidoServiceMessage.PRODUCTO_ID_REQUERIDO));
    }
	
}