package co.edu.usbcali.airlinesapp.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.usbcali.airlinesapp.domain.Categoria;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {
	boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Integer id);

    List<Categoria> findByNombreLikeIgnoreCase(String nombre);
    
    List<Categoria> findAllByOrderByNombreAsc();
}

