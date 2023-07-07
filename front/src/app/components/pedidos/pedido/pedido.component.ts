import { Component, TemplateRef, ViewChild } from '@angular/core';
import { PedidoService } from '../service/pedido.service';
import { Pedido } from '../model/pedido';
import { DetallesPedido } from '../model/detallepedido';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CrearPedidoModalComponent } from '../crear-pedido-modal/crear-pedido-modal.component';
import { ClienteService } from '../../clientes/service/cliente.service';
import { EstadoPedidoService } from '../../estadosPedido/service/estado-pedido.service';
import { DetallePedidoService } from '../service/detallepedido.service';
import Swal from 'sweetalert2';
import { DetallepedidomodalComponent } from '../detallepedidomodal/detallepedidomodal.component';

@Component({
  selector: 'app-pedido',
  templateUrl: './pedido.component.html',
  styleUrls: ['./pedido.component.css']
})
export class PedidoComponent {
  pedidos: Pedido[];
  detallesPedido: DetallesPedido[];
  cacheNombresClientes: { [id: number]: string } = {};
  cacheNombresEstados: { [id: number]: string } = {};
  pedido: Pedido;
  detallesPedidoss: DetallesPedido[];

  selectedPedido: Pedido;
  @ViewChild('detallePedidoTemplate', { static: true }) DetallepedidomodalComponent: TemplateRef<any>;

  constructor(
    private pedidoService: PedidoService,
    private dialog: MatDialog,
    private router: Router,
    private clienteService: ClienteService,
    private estadoPedidoService: EstadoPedidoService,
    private detallePedidoService:DetallePedidoService
  ) {}

  ngOnInit(): void {
    this.cargarClientes();
    this.cargarEstadosPedido();
    this.cargarPedidos();
  }

  cargarClientes(): void {
    this.clienteService.obtenerClientes().subscribe(response => {
      response.forEach(cliente => {
        this.cacheNombresClientes[cliente.id] = cliente.nombres;
      });
    });
  }

  cargarEstadosPedido(): void {
    this.estadoPedidoService.obtenerEstadosPedidos().subscribe(response => {
      response.forEach(estado => {
        this.cacheNombresEstados[estado.id] = estado.descripcion;
      });
    });
  }

  cargarPedidos(): void {
    this.pedidoService.obtenerPedidos().subscribe((response: Pedido[]) => {
      this.pedidos = response;
    });
  }

  crearPedido(): void {
    const dialogRef = this.dialog.open(CrearPedidoModalComponent, {
      width: '400px',
      data: { /* datos adicionales que puedas necesitar */ }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Realiza las acciones necesarias después de crear el pedido
        this.cargarPedidos();
      }
    });
  }

  editarPedido(id: number): void {
    // Lógica para editar un pedido existente
    /* this.router.navigate(['/pedidos/editar', id]); */
  }

  eliminarPedido(id: number): void {
    Swal.fire({
      title: 'Estas seguro?',
      text: "No puedes revertir este cambio!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#673AB7',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, Eliminarla!'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(
          'Eliminada!',
          'La el pedido con sus detalles ha sido eliminado.',
          'success'
        )
        this.detallePedidoService.eliminarDetallePedido(id).subscribe(() => {
          this.cargarPedidos();
        });
      }
    })
  }

  verDetalle(pedidoId: number) {
    this.pedidoService.obtenerPedidoId(pedidoId).subscribe(response=>{
      this.pedido = response;
    })
    this.detallePedidoService.obtenerDetallesPorPedidoId(pedidoId).subscribe((response) => {
     
      this.detallesPedidoss = response;
      this.dialog.open(this.DetallepedidomodalComponent);
    });
  }

  obtenerNombreCliente(clienteId: number) {
    return this.cacheNombresClientes[clienteId] || '';
  }
  
  obtenerNombreEstado(estadoId: number) {
    return this.cacheNombresEstados[estadoId] || '';
  }

  cerrarDetallePedido(): void {
    this.selectedPedido = null;
    this.dialog.closeAll();
  }
}
