import { Component,Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ProductoService } from '../../producto/service/producto.service';
import { DetallesPedido } from '../model/detallepedido';
import { Producto } from '../../producto/model/producto';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-agregar-detalle-modal',
  templateUrl: './agregar-detalle-modal.component.html',
  styleUrls: ['./agregar-detalle-modal.component.css']
})
export class AgregarDetalleModalComponent {


  listaProductos: Producto[] = [];
  detalle: DetallesPedido = {
    id: null,
    cantidad: 1,
    valor: 0,
    pedidoId: this.data.pedidoId,
    productoId: null
  };

  constructor(
    public dialogRef: MatDialogRef<AgregarDetalleModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private productoService: ProductoService
  ) { }

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.productoService.obtenerProducto().subscribe(response => {
      this.listaProductos = response;
    });
  }

  guardarDetalle(): void {
    // Aquí puedes realizar la lógica para guardar el detalle del pedido
    // Puedes acceder a los datos ingresados en this.detalle

    if (!this.detalle.productoId) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Debe seleccionar un producto antes de confirmar el detalle',
        confirmButtonText: 'Aceptar'
      });
      return;
    }

    this.dialogRef.close(this.detalle);
  }

  cancelarModal(): void {
    this.dialogRef.close();
  }

}
