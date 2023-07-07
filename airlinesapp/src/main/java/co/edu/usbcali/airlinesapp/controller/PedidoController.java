package co.edu.usbcali.airlinesapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.airlinesapp.dtos.PedidoDTO;
import co.edu.usbcali.airlinesapp.exceptions.ClienteException;
import co.edu.usbcali.airlinesapp.exceptions.EstadoPedidoException;
import co.edu.usbcali.airlinesapp.exceptions.PedidoException;
import co.edu.usbcali.airlinesapp.services.PedidoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    
    @PostMapping("/guardar")
    ResponseEntity<PedidoDTO> nuevoPedido(@RequestBody PedidoDTO pedidoDTO) throws Exception {
        return ResponseEntity.ok(pedidoService.guardar(pedidoDTO));
    }

    @GetMapping("/{id}")
    ResponseEntity<PedidoDTO> buscarPedido(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @GetMapping("/listarpedidos")
    ResponseEntity<List<PedidoDTO>> obtenerPedido() throws Exception {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }

    @PutMapping("/actualizar")
    ResponseEntity<PedidoDTO> actualizar(@RequestBody @Valid PedidoDTO pedidoDTO) throws PedidoException, EstadoPedidoException, ClienteException {
        return ResponseEntity.ok(pedidoService.actualizar(pedidoDTO));
    }
}
