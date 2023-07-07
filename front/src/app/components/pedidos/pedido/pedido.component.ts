import { Component } from '@angular/core';
import { PedidoService } from '../service/pedido.service';
import { Pedido } from '../model/pedido';
import { DetallesPedido } from '../model/detallepedido';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CrearPedidoModalComponent } from '../crear-pedido-modal/crear-pedido-modal.component';
import { ClienteService } from '../../clientes/service/cliente.service';
import { EstadoPedidoService } from '../../estadosPedido/service/estado-pedido.service';

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

  constructor(
    private pedidoService: PedidoService,
    private dialog: MatDialog,
    private router: Router,
    private clienteService: ClienteService,
    private estadoPedidoService: EstadoPedidoService
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
    // Lógica para eliminar un pedido
    this.pedidoService.eliminarPedido(id).subscribe(() => {
      // Recargar la lista de pedidos después de eliminar
      this.cargarPedidos();
    });
  }

  verDetalle(pedidoId: number) {
    // Lógica para ver el detalle del pedido
  }

  obtenerNombreCliente(clienteId: number) {
    return this.cacheNombresClientes[clienteId] || '';
  }
  
  obtenerNombreEstado(estadoId: number) {
    return this.cacheNombresEstados[estadoId] || '';
  }
}
