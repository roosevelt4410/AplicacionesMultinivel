package co.edu.usbcali.airlinesapp.domain;

import java.math.BigDecimal;
import java.time.Instant;

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

@Builder
@Entity
@Table(name = "pedidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "cli_id", referencedColumnName = "id", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "espe_id", referencedColumnName = "id", nullable = false)
    private EstadoPedido estadoPedido;
	
	@Column(nullable=false)
	private Instant fecha;
	
	@Column(nullable=false)
	private BigDecimal total;
}
