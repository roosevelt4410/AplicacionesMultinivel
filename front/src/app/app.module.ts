import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AngularMaterialModule } from './shared/angular-material.module';
import { TranslateLoader, TranslateModule, TranslateService} from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient,HttpClientModule} from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CategoriaComponent } from './components/categoria/categoria/categoria.component';
import { ProductoComponent } from './components/producto/producto/producto.component';
import { DetalleProductoComponent } from './components/producto/detalle-producto/detalle-producto.component';
import { MatDialogModule } from '@angular/material/dialog';
import { HomeComponent } from './components/home/home/home.component';
import { HeaderComponent } from './components/header/header/header.component';
import { DetalleCategoriaComponent } from './components/categoria/detalle-categoria/detalle-categoria.component';
import { TipoDocumentoComponent } from './components/tipoDocumento/tipo-documento/tipo-documento.component';
import { DetalleTipoDocumentoComponent } from './components/tipoDocumento/detalle-tipo-documento/detalle-tipo-documento.component';
import { EstadosPedidoComponent } from './components/estadosPedido/estados-pedido/estados-pedido.component';
import { DetalleEstadosPedidoComponent } from './components/estadosPedido/detalle-estados-pedido/detalle-estados-pedido.component';
import { PedidoComponent } from './components/pedidos/pedido/pedido.component';
import { ClienteComponent } from './components/clientes/cliente/cliente.component';
import { DetalleClienteComponent } from './components/clientes/detalle-cliente/detalle-cliente.component';
import { CrearPedidoModalComponent } from './components/pedidos/crear-pedido-modal/crear-pedido-modal.component';
import { EditarPedidoModalComponent } from './components/pedidos/editar-pedido-modal/editar-pedido-modal.component';
import { EliminarPedidoModalComponent } from './components/pedidos/eliminar-pedido-modal/eliminar-pedido-modal.component';
import { AgregarDetalleModalComponent } from './components/pedidos/agregar-detalle-modal/agregar-detalle-modal.component';


@NgModule({
  declarations: [
    AppComponent,
    CategoriaComponent,
    ProductoComponent,
    DetalleProductoComponent,
    HomeComponent,
    HeaderComponent,
    DetalleCategoriaComponent,
    TipoDocumentoComponent,
    DetalleTipoDocumentoComponent,
    EstadosPedidoComponent,
    DetalleEstadosPedidoComponent,
    PedidoComponent,
    ClienteComponent,
    DetalleClienteComponent,
    CrearPedidoModalComponent,
    EditarPedidoModalComponent,
    EliminarPedidoModalComponent,
    AgregarDetalleModalComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,

    AngularMaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FormsModule,
    MatDialogModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (http: HttpClient) => {
          return new TranslateHttpLoader(http, './../assets/i18n/', '.json');
        },
        deps: [HttpClient]
      }
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor(translateService:TranslateService){
    translateService.setDefaultLang('es');
    translateService.use('es');
  }
}
