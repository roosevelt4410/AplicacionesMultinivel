import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/app/environment/environment';
import { Pedido } from '../model/pedido';
import { Observable } from 'rxjs';
import { DetallesPedido } from '../model/detallepedido';

@Injectable({
  providedIn: 'root'
})
export class DetallePedidoService {

  constructor(private httpClient:HttpClient) { }


  obtenerDetallePedidos():Observable<DetallesPedido[]>{
    return this.httpClient.get<DetallesPedido[]>(`${environment.urlBase}/detallepedido/listarDetallePedido`);
  }

  obtenerDetallePedidoId(idDetallePedido:number):Observable<DetallesPedido>{
    return this.httpClient.get<DetallesPedido>(`${environment.urlBase}/detallepedido/${idDetallePedido}`);
  }

  crearDetallePedido(detallePedido:DetallesPedido):Observable<DetallesPedido>{
    return this.httpClient.post<DetallesPedido>(`${environment.urlBase}/detallepedido/guardar`,detallePedido);
  }

  crearDetallePedidos(detallePedidos:DetallesPedido[]):Observable<DetallesPedido[]>{
    return this.httpClient.post<DetallesPedido[]>(`${environment.urlBase}/detallepedido/guardarlistado`,detallePedidos);
  }

  eliminarDetallePedido(idDetallePedido:number):Observable<DetallesPedido>{
    return this.httpClient.delete<DetallesPedido>(`${environment.urlBase}/detallepedido/eliminar/${idDetallePedido}`);
  }


  actualizarPedido(pediddo:Pedido):Observable<DetallesPedido>{
    return this.httpClient.put<DetallesPedido>(`${environment.urlBase}/detallepedido/actualizar`,pediddo);
  }
}
