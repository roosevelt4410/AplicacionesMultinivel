import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EstadoPedido } from '../model/estado-pedido';
import { environment } from 'src/app/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class EstadoPedidoService {

  constructor(private httpClient:HttpClient) { }


  obtenerEstadosPedidos():Observable<EstadoPedido[]>{
    return this.httpClient.get<EstadoPedido[]>(`${environment.urlBase}/estadosPedido/listarEstadosPedidos`);
  }

  obtenerEstadosPedidosId(idEstadosPedidos:number):Observable<EstadoPedido>{
    return this.httpClient.get<EstadoPedido>(`${environment.urlBase}/estadosPedido/${idEstadosPedidos}`);
  }

  crearEstadosPedidos(EstadosPedidos:EstadoPedido):Observable<EstadoPedido>{
    return this.httpClient.post<EstadoPedido>(`${environment.urlBase}/estadosPedido/guardar`,EstadosPedidos);
  }

  eliminarEstadosPedidos(idEstadosPedidos:number):Observable<EstadoPedido>{
    return this.httpClient.delete<EstadoPedido>(`${environment.urlBase}/estadosPedido/eliminar/${idEstadosPedidos}`);
  }


  actualizarEstadosPedidos(EstadosPedidos:EstadoPedido):Observable<EstadoPedido>{
    return this.httpClient.put<EstadoPedido>(`${environment.urlBase}/estadosPedido/actualizar`,EstadosPedidos);
  }
}
