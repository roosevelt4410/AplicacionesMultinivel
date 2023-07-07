import { Component,Inject  } from '@angular/core';
import { Pedido } from '../model/pedido';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DetallesPedido } from '../model/detallepedido';
import { EstadoPedido } from '../../estadosPedido/model/estado-pedido';
import { Cliente } from '../../clientes/model/cliente';
import { ClienteService } from '../../clientes/service/cliente.service';
import { EstadoPedidoService } from '../../estadosPedido/service/estado-pedido.service';
import { AgregarDetalleModalComponent } from '../agregar-detalle-modal/agregar-detalle-modal.component';
import { ProductoService } from '../../producto/service/producto.service';
import { Producto } from '../../producto/model/producto';
import { PedidoService } from '../service/pedido.service';
import Swal from 'sweetalert2';
import { DetallePedidoService } from '../service/detallepedido.service';

@Component({
  selector: 'app-crear-pedido-modal',
  templateUrl: './crear-pedido-modal.component.html',
  styleUrls: ['./crear-pedido-modal.component.css']
})
export class CrearPedidoModalComponent {

  listaEstadosPedido:EstadoPedido[]=[];
  listaClientes:Cliente[]=[];
  nombreProducto: string;
  
  ngOnInit(): void {
    this.cargarClientes();
    this.cargarEstadosPedido();
    this.obtenerListaProductos();
  }

  cargarClientes(){
    this.clienteService.obtenerClientes().subscribe(response=>{
      this.listaClientes = response;
    })
  }

  cargarEstadosPedido(){
    this.estadoPedidoService.obtenerEstadosPedidos().subscribe(response=>{
      this.listaEstadosPedido = response;
    })
  }

  listaProductos: Producto[] = [];



  pedido: Pedido = {
    id: 0,
    fecha: new Date(),
    total: 0,
    clienteId: 0,
    estadoPedidoId: 0,
  };
  detallesPedido: DetallesPedido[] = [];

  constructor(
    public dialogRef: MatDialogRef<CrearPedidoModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialog: MatDialog,
    private clienteService: ClienteService,
    private estadoPedidoService:EstadoPedidoService,
    private productoService:ProductoService,  
    private pedidoService:PedidoService,
    private detalllePedidoService:DetallePedidoService
    ) {}


  obtenerListaProductos(): void {
    this.productoService.obtenerProducto().subscribe(response => {
      this.listaProductos = response;
    });
  }

  crearPedido(): void {
    debugger;
    const totalPedido = this.detallesPedido.reduce((total, detalle) => total + detalle.valor, 0);
  
    const detalles: DetallesPedido[] = this.detallesPedido.map((detalle) => ({
      id: 0,
      cantidad: Number(detalle.cantidad.toFixed(2)),
      valor: Number(detalle.valor.toFixed(2)),
      pedidoId: 0, 
      productoId: detalle.productoId
    }));
  
    const pedido: Pedido = {
      fecha: this.pedido.fecha,
      total: totalPedido,
      clienteId: this.pedido.clienteId,
      estadoPedidoId: this.pedido.estadoPedidoId,
    };
  
    this.pedidoService.crearPedido(pedido).subscribe((pedidoResponse) => {
      Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Has guardado correctamente el pedido!',
        showConfirmButton: false,
        timer: 2000
      }).then(() => {
        this.pedido = {
          id: 0,
          fecha: new Date(),
          total: 0,
          clienteId: 0,
          estadoPedidoId: 0,
        };
      });
      detalles.forEach((detalle) => {
        detalle.pedidoId = pedidoResponse.id;
      });
  
      this.detalllePedidoService.crearDetallePedidos(detalles).subscribe(() => {
        this.dialogRef.close(true);
        window.location.reload();
      });
    });
  }
  

  cancelar(): void {
    this.dialogRef.close();
  }

  agregarDetalle(): void {
    const nuevoDetalle: DetallesPedido = {
      id: null,
      cantidad: 0,
      valor: 0,
      pedidoId: this.pedido.id,
      productoId: null
    };
    const dialogRef = this.dialog.open(AgregarDetalleModalComponent, {
      width: '400px',
      data: {
        pedidoId: this.pedido.id
      }
    });
  
    dialogRef.afterClosed().subscribe((resultado: DetallesPedido) => {
      this.productoService.obtenerProductoId(resultado.productoId).subscribe(response=>{
        resultado.valor = resultado.cantidad * response.precioUnitario;
        if (resultado) {
          this.detallesPedido.push({...resultado}); 
        }
        this.nombreProducto = response.nombre;
      })
    });
  }
}