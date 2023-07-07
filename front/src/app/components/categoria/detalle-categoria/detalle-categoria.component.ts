import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Categoria } from '../model/categoria';

@Component({
  selector: 'app-detalle-categoria',
  templateUrl: './detalle-categoria.component.html',
  styleUrls: ['./detalle-categoria.component.css']
})
export class DetalleCategoriaComponent {
  @Input() categoria: Categoria;
  @Output() cerrar = new EventEmitter<void>();

  cerrarDetalle(): void {
    this.cerrar.emit();
  }
}
