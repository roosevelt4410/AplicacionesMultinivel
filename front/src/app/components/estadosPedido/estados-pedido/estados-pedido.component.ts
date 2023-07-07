import { Component, TemplateRef, ViewChild } from '@angular/core';
import { EstadoPedido } from '../model/estado-pedido';
import { MatTableDataSource } from '@angular/material/table';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { EstadoPedidoService } from '../service/estado-pedido.service';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-estados-pedido',
  templateUrl: './estados-pedido.component.html',
  styleUrls: ['./estados-pedido.component.css']
})
export class EstadosPedidoComponent {
  listColumnas:string[] = ['id','descripcion','opciones'];
  listaEstadosPedido:EstadoPedido[]=[];
  dataSource: MatTableDataSource<EstadoPedido> = new MatTableDataSource<EstadoPedido>([]);
  estadoPedido:EstadoPedido;
  form!:FormGroup;
  isForm!: Promise<any>;
  selectedEstadoPedido: EstadoPedido;
  @ViewChild('detalleEstadoPedidoTemplate', { static: true }) detalleEstadoPedido: TemplateRef<any>;

  constructor(
    private estadoPedidoService:EstadoPedidoService, 
    private FormBuilder:FormBuilder, 
    private dialog: MatDialog){

  }

  ngOnInit():void{
    this.cargarData();
    this.iniciarFormulario();
  }

  iniciarFormulario(): void {
    this.isForm = Promise.resolve(
      this.form = this.FormBuilder.group({
        descripcion: new FormControl(null, [Validators.required]),
      })
    );
  }

  cargarData():void{
    this.estadoPedidoService.obtenerEstadosPedidos().subscribe((response:EstadoPedido[])=>{
      this.dataSource=new MatTableDataSource<EstadoPedido>(response);
      this.listaEstadosPedido =response;
    });
  }

  agregar():void{
    if(this.form.invalid){
      Swal.fire({
        icon: 'error',
        title: 'Error...',
        text: 'Por favor ingresa todos los campos!',
      /*   footer: '<h3>Por favor ingresa todos los campos</h3>', */
        confirmButtonColor:'#673AB7',
        cancelButtonText:"Aceptar",
        confirmButtonText:"Aceptar"
      })
    }else{
      const values = this.form.value;
      let estadoPedido:EstadoPedido ={
        id:0,
        descripcion:values.descripcion,
      }
      this.estadoPedidoService.crearEstadosPedidos(estadoPedido).subscribe((response:EstadoPedido)=>{
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Has guardado correctamente la categoria !',
          showConfirmButton: false,
          timer: 2000
        }).then(()=>{
          this.cargarData();
        })
      });
    }
  }

  procesarOperacion():void{
    if(this.estadoPedido && this.estadoPedido.id){
      this.actualizarTipoDocumento();
    }else{
      this.agregar();
    }
  }


  eliminar(estadoPedidoId:number):void{
    
      Swal.fire({
        title: 'Estas seguro?',
        text: "No puedes revertir este cambio!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#673AB7',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Si, Eliminarlo!'
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire(
            'Eliminado!',
            'El estado pedido ha sido eliminado.',
            'success'
          )
          this.estadoPedidoService.eliminarEstadosPedidos(estadoPedidoId).subscribe(response=>{
            this.cargarData();
          })
        }
      })
  }


  editar(estadoPedido:EstadoPedido):void{
    this.form.get('descripcion').setValue(estadoPedido.descripcion);
    this.estadoPedido = estadoPedido;
  }

  actualizarTipoDocumento():void{
  const values = this.form.value;
  const estadoPedido: EstadoPedido = {
    id: this.estadoPedido.id,
    descripcion: values.descripcion,
    };
    this.estadoPedidoService.actualizarEstadosPedidos(estadoPedido).subscribe((response=>{
      Swal.fire({
        title: 'Esta seguro que desea actualizar el estado de pedido?',
        showDenyButton: true,
        showCancelButton: true,
        confirmButtonText: 'Save',
        denyButtonText: `No guardar`,
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire('Actualizado!', '', 'success')
          this.cargarData();
        } else if (result.isDenied) {
          Swal.fire('Cambios no actualizados', '', 'info')
        }
      })
    }))
  }

  verDetalle(estadoPedido: EstadoPedido): void {
    this.selectedEstadoPedido = estadoPedido;
    this.dialog.open(this.detalleEstadoPedido);
  }

  cerrarDetalleEstadoPedido(): void {
    this.selectedEstadoPedido = null;
    this.dialog.closeAll();
  }


}
