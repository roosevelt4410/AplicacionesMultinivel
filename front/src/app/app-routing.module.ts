import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriaComponent } from './components/categoria/categoria/categoria.component';
import { ProductoComponent } from './components/producto/producto/producto.component';
import { HomeComponent } from './components/home/home/home.component';
import { TipoDocumentoComponent } from './components/tipoDocumento/tipo-documento/tipo-documento.component';
import { EstadosPedidoComponent } from './components/estadosPedido/estados-pedido/estados-pedido.component';
import { PedidoComponent } from './components/pedidos/pedido/pedido.component';
import { ClienteComponent } from './components/clientes/cliente/cliente.component';

const routes: Routes = [
  {
    path:'',
    component:HomeComponent,
  },
  {
    path:'home',
    redirectTo:'/',
  },
  {
    path:'categorias',
    component:CategoriaComponent,
  },
  {
    path:'productos',
    component:ProductoComponent,
  },
  {
    path:'tipos-documento',
    component:TipoDocumentoComponent,
  },
  {
    path:'estados-pedido',
    component:EstadosPedidoComponent,
  },
  {
    path:'pedidos',
    component:PedidoComponent,
  },
  {
    path:'clientes',
    component:ClienteComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
