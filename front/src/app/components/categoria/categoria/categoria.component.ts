import { Component, ViewChild, TemplateRef } from '@angular/core';
import { CategoriaServiceService } from '../service/categoria-service.service';
import { Categoria } from './../model/categoria';
import { MatTableDataSource } from '@angular/material/table';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-categoria',
  templateUrl: './categoria.component.html',
  styleUrls: ['./categoria.component.css']
})
export class CategoriaComponent {

  listColumnas: string[] = ['id', 'nombre', 'descripcion', 'opciones'];
  listaCategorias:Categoria[]=[];
  dataSource: MatTableDataSource<Categoria> = new MatTableDataSource<Categoria>([]);
  categoria:Categoria;

  form!:FormGroup;

  isForm!: Promise<any>;
  selectedCategoria: Categoria;
  @ViewChild('detalleCategoriaTemplate', { static: true }) detalleCategoriaTemplate: TemplateRef<any>;
  
  
  
  constructor(private categoriaService:CategoriaServiceService, 
    private formBuilder:FormBuilder,
    private dialog: MatDialog){

  }

  ngOnInit():void{
    this.cargarData();
    this.iniciarFormulario();
  }


  cargarData():void{
    this.categoriaService.obtenerCategorias().subscribe((response:Categoria[])=>{
      this.dataSource=new MatTableDataSource<Categoria>(response);
      this.listaCategorias =response;
    });
  }

  verDetalle(categoria: Categoria): void {
    this.selectedCategoria = categoria;
    this.dialog.open(this.detalleCategoriaTemplate);
  }


  iniciarFormulario(): void {
    this.isForm = Promise.resolve(
      this.form = this.formBuilder.group({
        nombre: new FormControl(null, [Validators.required]),
        descripcion: new FormControl(null, [Validators.required])
      })
    );
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
      let categoria:Categoria ={
        id:0,
        nombre:values.nombre,
        descripcion:values.descripcion
      }
      this.categoriaService.crearCategoria(categoria).subscribe((response:Categoria)=>{
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Has guardado correctamente la categoria !',
          showConfirmButton: false,
          timer: 1000
        }).then(()=>{
          this.cargarData();
        })
      });
    }
  }


  eliminar(categoriaId:number):void{
    this.categoriaService.eliminarCategoria(categoriaId).subscribe(response=>{
      Swal.fire({
        title: 'Estas seguro?',
        text: "No puedes revertir este cambio!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#673AB7',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Si, Eliminarla!'
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire(
            'Eliminada!',
            'La categoria ha sido eliminada.',
            'success'
          )
        }
        this.cargarData();
      })
    })
  }


  editar(categoria:Categoria):void{
    this.form.get('nombre').setValue(categoria.nombre);
    this.form.get('descripcion').setValue(categoria.descripcion);
    this.categoria = categoria;
  }

  procesarOperacion():void{
    if(this.categoria && this.categoria.id){
      this.actualizarCategoria();
    }else{
      this.agregar();
    }
  }

  actualizarCategoria():void{
    const values = this.form.value;
    let categoria: Categoria={
      id: this.categoria.id,
      nombre:values.nombre,
      descripcion:values.descripcion
    };
    this.categoriaService.actualizarCategoria(categoria).subscribe((response=>{
      Swal.fire({
        title: 'Esta seguro que desea actualizar la categoria?',
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

  cerrarDetalleCategoria(): void {
    this.selectedCategoria = null;
    this.dialog.closeAll();
  }


  

}
