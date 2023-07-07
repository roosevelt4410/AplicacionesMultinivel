import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Producto } from '../model/producto';
import { environment } from 'src/app/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  constructor(private httpClient:HttpClient) { }


  obtenerProducto():Observable<Producto[]>{
    return this.httpClient.get<Producto[]>(`${environment.urlBase}/productos/listarProductos`);
  }

  obtenerProductoId(idProducto:number):Observable<Producto>{
    return this.httpClient.get<Producto>(`${environment.urlBase}/productos/${idProducto}`);
  }

  crearProducto(producto:Producto):Observable<Producto>{
    return this.httpClient.post<Producto>(`${environment.urlBase}/productos/nuevo`,producto);
  }

  eliminarProducto(productoId:number):Observable<Producto>{
    return this.httpClient.delete<Producto>(`${environment.urlBase}/productos/eliminar/${productoId}`);
  }


  actualizarProducto(producto:Producto):Observable<Producto>{
    console.log(producto);
    return this.httpClient.put<Producto>(`${environment.urlBase}/productos/actualizar`,producto);
  }
}
