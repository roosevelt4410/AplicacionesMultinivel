package co.edu.usbcali.airlinesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.usbcali.airlinesapp.domain.Producto;


@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {
	boolean existsByCategoriaIdAndReferencia(Integer categoriaId, String referencia);
}
