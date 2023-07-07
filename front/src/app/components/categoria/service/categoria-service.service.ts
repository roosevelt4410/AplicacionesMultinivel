import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import { Categoria } from '../model/categoria';
import { environment } from 'src/app/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoriaServiceService {

  constructor(private httpClient:HttpClient) { 
  }

  obtenerCategorias():Observable<Categoria[]>{
    return this.httpClient.get<Categoria[]>(`${environment.urlBase}/categorias/listarCategorias`);
  }

  obtenerCategoriaId(idCategoria:number):Observable<Categoria>{
    return this.httpClient.get<Categoria>(`${environment.urlBase}/categorias/${idCategoria}`);
  }

  crearCategoria(categoria:Categoria):Observable<Categoria>{
    console.log(categoria);
    return this.httpClient.post<Categoria>(`${environment.urlBase}/categorias/guardar`,categoria);
  }

  eliminarCategoria(categoriaId:number):Observable<Categoria>{
    return this.httpClient.delete<Categoria>(`${environment.urlBase}/categorias/eliminar/${categoriaId}`);
  }


  actualizarCategoria(categoria:Categoria):Observable<Categoria>{
    return this.httpClient.put<Categoria>(`${environment.urlBase}/categorias/actualizar`,categoria);
  }
}
