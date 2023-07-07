package co.edu.usbcali.airlinesapp.domain;


import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipodocumento")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoDocumento {
	
	 @Id
	 @Column(nullable = false)
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer id ;

	 @Column(length = 10)
	 private String descripcion;
}
