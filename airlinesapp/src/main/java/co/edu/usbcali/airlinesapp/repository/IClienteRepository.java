package co.edu.usbcali.airlinesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.usbcali.airlinesapp.domain.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
	
	boolean existsByTipoDocumentoIdAndDocumento(Integer tipoDocumentoId, String documento);

}
