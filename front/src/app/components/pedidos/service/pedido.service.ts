import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/app/environment/environment';
import { Pedido } from '../model/pedido';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  constructor(private httpClient:HttpClient) { }


  obtenerPedidos():Observable<Pedido[]>{
    return this.httpClient.get<Pedido[]>(`${environment.urlBase}/pedidos/listarpedidos`);
  }

  obtenerPedidoId(idPedido:number):Observable<Pedido>{
    return this.httpClient.get<Pedido>(`${environment.urlBase}/pedidos/${idPedido}`);
  }

  crearPedido(pedido:Pedido):Observable<Pedido>{
    return this.httpClient.post<Pedido>(`${environment.urlBase}/pedidos/guardar`,pedido);
  }

  eliminarPedido(idPedido:number):Observable<Pedido>{
    return this.httpClient.delete<Pedido>(`${environment.urlBase}/pedidos/eliminar/${idPedido}`);
  }


  actualizarPedido(pediddo:Pedido):Observable<Pedido>{
    return this.httpClient.put<Pedido>(`${environment.urlBase}/pedidos/actualizar`,pediddo);
  }
}
