import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Cliente } from '../model/cliente';

@Component({
  selector: 'app-detalle-cliente',
  templateUrl: './detalle-cliente.component.html',
  styleUrls: ['./detalle-cliente.component.css']
})
export class DetalleClienteComponent {
  @Input() cliente: Cliente;
  @Output() cerrar = new EventEmitter<void>();

  cerrarDetalle(): void {
    this.cerrar.emit();
  }
}
