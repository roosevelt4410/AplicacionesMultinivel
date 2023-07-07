import { Component, TemplateRef, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Producto } from '../model/producto';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ProductoService } from '../service/producto.service';
import Swal from 'sweetalert2';
import { Categoria } from '../../categoria/model/categoria';
import { CategoriaServiceService } from '../../categoria/service/categoria-service.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-producto',
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.css']
})
export class ProductoComponent {
  listColumnas:string[] = ['id','descripcion','nombre','precioUnitario','referencia','unidadesDisponibles','categoria','opciones'];
  listaProductos:Producto[]=[];
  dataSource: MatTableDataSource<Producto> = new MatTableDataSource<Producto>([]);
  producto:Producto;
  categorias:Categoria[]=[];

  form!:FormGroup;

  isForm!: Promise<any>;

  selectedProduct: Producto;
  @ViewChild('detalleProductoTemplate', { static: true }) detalleProductoTemplate: TemplateRef<any>;

  constructor(
    private productoService:ProductoService, 
    private formBuilder:FormBuilder, 
    private categoriaService:CategoriaServiceService,
    private dialog: MatDialog){

  }

  ngOnInit():void{
    this.cargarData();
    this.iniciarCategorias();
    this.iniciarFormulario();
  }
  iniciarCategorias():void{
    this.categoriaService.obtenerCategorias().subscribe((response:Categoria[])=>{
      this.categorias = response;
    });
  }

  cargarData():void{
    this.productoService.obtenerProducto().subscribe((response:Producto[])=>{
      console.log(response);
      this.dataSource=new MatTableDataSource<Producto>(response);
      this.listaProductos =response;
    });
  }

  verDetalle(producto: Producto): void {
    this.selectedProduct = producto;
    this.dialog.open(this.detalleProductoTemplate);
  }


  iniciarFormulario(): void {
    this.isForm = Promise.resolve(
      this.form = this.formBuilder.group({
        nombre: new FormControl(null, [Validators.required]),
        descripcion: new FormControl(null, [Validators.required]),
        precioUnitario: new FormControl(null, [Validators.required]),
        referencia: new FormControl(null, [Validators.required]),
        unidadesDisponibles: new FormControl(null, [Validators.required]),
        categoria: new FormControl(null, [Validators.required]),
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
      let producto:Producto ={
        id:0,
        descripcion:values.descripcion,
        nombre:values.nombre,
        precioUnitario:values.precioUnitario,
        referencia:values.referencia,
        unidadesDisponibles:values.unidadesDisponibles,
        categoria:values.categoria.id
      }
      console.log(producto);
      this.productoService.crearProducto(producto).subscribe((response:Producto)=>{
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
    if(this.producto && this.producto.id){
      this.actualizarProducto();
    }else{
      this.agregar();
    }
  }


  eliminar(productoId:number):void{
    
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
            'Eliminado!',
            'El producto ha sido eliminado.',
            'success'
          )
          this.productoService.eliminarProducto(productoId).subscribe(response=>{
            this.cargarData();
          })
        }
      })
  }


  editar(producto:Producto):void{
    this.form.get('nombre').setValue(producto.nombre);
    this.form.get('descripcion').setValue(producto.descripcion);
    this.form.get('referencia').setValue(producto.referencia);
    this.form.get('unidadesDisponibles').setValue(producto.unidadesDisponibles);
    this.form.get('precioUnitario').setValue(producto.precioUnitario);
  
    const categoriaSeleccionada = this.categorias.find(categoria => categoria.id === producto.categoria.id);
    this.form.get('categoria').setValue(categoriaSeleccionada);
  
    this.producto = producto;
  }

  actualizarProducto():void{
  const values = this.form.value;
  const producto: Producto = {
    id: this.producto.id,
    nombre: values.nombre,
    descripcion: values.descripcion,
    precioUnitario: values.precioUnitario,
    referencia: values.referencia,
    unidadesDisponibles: values.unidadesDisponibles,
    categoria: values.categoria.id
    };
    this.productoService.actualizarProducto(producto).subscribe((response=>{
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

  cerrarDetalleProducto(): void {
    this.selectedProduct = null;
    this.dialog.closeAll();
  }

}


