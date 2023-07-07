import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Pedido } from '../model/pedido';
import { DetallesPedido } from '../model/detallepedido';

@Component({
  selector: 'app-detallepedidomodal',
  templateUrl: './detallepedidomodal.component.html',
  styleUrls: ['./detallepedidomodal.component.css']
})
export class DetallepedidomodalComponent {
  @Input() pedido: Pedido;
  @Output() cerrar = new EventEmitter<void>();
  @Input() detallesPedidoss: DetallesPedido[] = [];

  cerrarDetalle(): void {
    this.cerrar.emit();
  }
}
