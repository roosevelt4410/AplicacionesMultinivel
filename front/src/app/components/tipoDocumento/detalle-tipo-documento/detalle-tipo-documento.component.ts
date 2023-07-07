import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TipoDocumento } from '../model/tipodocumento';

@Component({
  selector: 'app-detalle-tipo-documento',
  templateUrl: './detalle-tipo-documento.component.html',
  styleUrls: ['./detalle-tipo-documento.component.css']
})
export class DetalleTipoDocumentoComponent {
  @Input() tipoDocumento: TipoDocumento;
  @Output() cerrar = new EventEmitter<void>();

  cerrarDetalle(): void {
    this.cerrar.emit();
  }
}
