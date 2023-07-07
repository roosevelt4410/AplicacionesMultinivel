import { Component, TemplateRef, ViewChild } from '@angular/core';
import { TipoDocumento } from '../model/tipodocumento';
import { MatTableDataSource } from '@angular/material/table';
import { TipoDocumentoService } from '../service/tipo-documento.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-tipo-documento',
  templateUrl: './tipo-documento.component.html',
  styleUrls: ['./tipo-documento.component.css']
})
export class TipoDocumentoComponent {

  listColumnas:string[] = ['id','descripcion','opciones'];
  listaTipoDocumentos:TipoDocumento[]=[];
  dataSource: MatTableDataSource<TipoDocumento> = new MatTableDataSource<TipoDocumento>([]);
  tipoDocumento:TipoDocumento;
  form!:FormGroup;
  isForm!: Promise<any>;
  selectedTipoDocumento: TipoDocumento;
  @ViewChild('detalleTipoDocumentoTemplate', { static: true }) detalleTipoDocumentoTemplate: TemplateRef<any>;



  constructor(
    private tipoDocumentoService:TipoDocumentoService, 
    private formBuilder:FormBuilder, 
    private dialog: MatDialog){

  }

  ngOnInit():void{
    this.cargarData();
    this.iniciarFormulario();
  }

  iniciarFormulario(): void {
    this.isForm = Promise.resolve(
      this.form = this.formBuilder.group({
        descripcion: new FormControl(null, [Validators.required]),
      })
    );
  }

  cargarData():void{
    this.tipoDocumentoService.obtenerTipoDocumentos().subscribe((response:TipoDocumento[])=>{
      this.dataSource=new MatTableDataSource<TipoDocumento>(response);
      this.listaTipoDocumentos =response;
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
      let tipoDocumento:TipoDocumento ={
        id:0,
        descripcion:values.descripcion,
      }
      this.tipoDocumentoService.crearTipoDocumento(tipoDocumento).subscribe((response:TipoDocumento)=>{
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
    if(this.tipoDocumento && this.tipoDocumento.id){
      this.actualizarTipoDocumento();
    }else{
      this.agregar();
    }
  }


  eliminar(tipoDocumentoId:number):void{
    
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
            'El tipoDocumento ha sido eliminado.',
            'success'
          )
          this.tipoDocumentoService.eliminarTipoDocumento(tipoDocumentoId).subscribe(response=>{
            this.cargarData();
          })
        }
      })
  }


  editar(tipoDocumento:TipoDocumento):void{
    this.form.get('descripcion').setValue(tipoDocumento.descripcion);
    this.tipoDocumento = tipoDocumento;
  }

  actualizarTipoDocumento():void{
  const values = this.form.value;
  const tipoDocumento: TipoDocumento = {
    id: this.tipoDocumento.id,
    descripcion: values.descripcion,
    };
    this.tipoDocumentoService.actualizarTipoDocumento(tipoDocumento).subscribe((response=>{
      Swal.fire({
        title: 'Esta seguro que desea actualizar el tipo de documento?',
        showDenyButton: true,
        showCancelButton: true,
        confirmButtonText: 'Save',
        denyButtonText: `No guardar`,
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire('Actualizada!', '', 'success')
          this.cargarData();
        } else if (result.isDenied) {
          Swal.fire('Cambios no actualizados', '', 'info')
        }
      })
    }))
  }

  verDetalle(tipoDocumento: TipoDocumento): void {
    this.selectedTipoDocumento = tipoDocumento;
    this.dialog.open(this.detalleTipoDocumentoTemplate);
  }

  cerrarDetalleTipoDocumento(): void {
    this.selectedTipoDocumento = null;
    this.dialog.closeAll();
  }
}
