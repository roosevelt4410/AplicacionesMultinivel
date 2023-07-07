package co.edu.usbcali.airlinesapp.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "productos")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

	@Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cate_id", referencedColumnName = "id", nullable = false)
    private Categoria categoria;

    @Column(length = 10, nullable = false)
    private String referencia;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @Column(name = "precio_unitario", length = 19, precision = 2)
    private BigDecimal precioUnitario;

    @Column(name = "unidades_disponibles", length = 19, precision = 2)
    private BigDecimal unidadesDisponibles;
}
