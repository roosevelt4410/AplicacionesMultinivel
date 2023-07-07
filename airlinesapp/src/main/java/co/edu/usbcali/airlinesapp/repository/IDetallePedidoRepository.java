package co.edu.usbcali.airlinesapp.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.usbcali.airlinesapp.domain.DetallesPedido;

@Repository
public interface IDetallePedidoRepository extends JpaRepository<DetallesPedido, Integer>{
	List<DetallesPedido> findAllByPedidoId(Integer pedidoId);
}
