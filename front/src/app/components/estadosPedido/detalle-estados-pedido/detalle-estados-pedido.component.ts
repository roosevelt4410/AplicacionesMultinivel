import { Component, EventEmitter, Input, Output } from '@angular/core';
import { EstadoPedido } from '../model/estado-pedido';

@Component({
  selector: 'app-detalle-estados-pedido',
  templateUrl: './detalle-estados-pedido.component.html',
  styleUrls: ['./detalle-estados-pedido.component.css']
})
export class DetalleEstadosPedidoComponent {
  @Input() estadoPedido: EstadoPedido;
  @Output() cerrar = new EventEmitter<void>();

  cerrarDetalle(): void {
    this.cerrar.emit();
  }
}
