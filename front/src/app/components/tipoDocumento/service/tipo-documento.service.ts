import { Injectable } from '@angular/core';
import { TipoDocumento } from '../model/tipodocumento';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/app/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class TipoDocumentoService {

  constructor(private httpClient:HttpClient) { }


  obtenerTipoDocumentos():Observable<TipoDocumento[]>{
    return this.httpClient.get<TipoDocumento[]>(`${environment.urlBase}/tipoDocumento/listarTiposDocumentos`);
  }

  obtenerTipoDocumentoId(idTipoDocumento:number):Observable<TipoDocumento>{
    return this.httpClient.get<TipoDocumento>(`${environment.urlBase}/tipoDocumento/${idTipoDocumento}`);
  }

  crearTipoDocumento(tipoDocumento:TipoDocumento):Observable<TipoDocumento>{
    return this.httpClient.post<TipoDocumento>(`${environment.urlBase}/tipoDocumento/guardar`,tipoDocumento);
  }

  eliminarTipoDocumento(tipoDocumentoId:number):Observable<TipoDocumento>{
    return this.httpClient.delete<TipoDocumento>(`${environment.urlBase}/tipoDocumento/eliminar/${tipoDocumentoId}`);
  }


  actualizarTipoDocumento(tipoDocumento:TipoDocumento):Observable<TipoDocumento>{
    return this.httpClient.put<TipoDocumento>(`${environment.urlBase}/tipoDocumento/actualizar`,tipoDocumento);
  }
}
