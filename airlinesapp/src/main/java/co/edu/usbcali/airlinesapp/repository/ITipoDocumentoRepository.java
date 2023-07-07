package co.edu.usbcali.airlinesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.usbcali.airlinesapp.domain.TipoDocumento;


@Repository
public interface ITipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer>{

}
