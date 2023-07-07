import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../model/cliente';
import { environment } from 'src/app/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(private httpClient:HttpClient) { }


  obtenerClientes():Observable<Cliente[]>{
    return this.httpClient.get<Cliente[]>(`${environment.urlBase}/clientes/listarClientes`);
  }

  obtenerClienteId(idCliente:number):Observable<Cliente>{
    return this.httpClient.get<Cliente>(`${environment.urlBase}/clientes/${idCliente}`);
  }

  crearCliente(cliente:Cliente):Observable<Cliente>{
    return this.httpClient.post<Cliente>(`${environment.urlBase}/clientes/guardar`,cliente);
  }

  eliminarCliente(clienteId:number):Observable<Cliente>{
    return this.httpClient.delete<Cliente>(`${environment.urlBase}/clientes/eliminar/${clienteId}`);
  }


  actualizarCliente(cliente:Cliente):Observable<Cliente>{
    return this.httpClient.put<Cliente>(`${environment.urlBase}/clientes/actualizar`,cliente);
  }
}
