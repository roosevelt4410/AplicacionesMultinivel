import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Producto } from '../model/producto';

@Component({
  selector: 'app-detalle-producto',
  templateUrl: './detalle-producto.component.html',
  styleUrls: ['./detalle-producto.component.css']
})
export class DetalleProductoComponent {
  @Input() producto: Producto;
  @Output() cerrar = new EventEmitter<void>();

  cerrarDetalle(): void {
    this.cerrar.emit();
  }
}