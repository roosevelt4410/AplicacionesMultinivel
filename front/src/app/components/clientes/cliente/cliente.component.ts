import { Component, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { Cliente } from '../model/cliente';
import { MatTableDataSource } from '@angular/material/table';
import { ClienteService } from '../service/cliente.service';
import { MatDialog } from '@angular/material/dialog';
import { TipoDocumentoService } from '../../tipoDocumento/service/tipo-documento.service';
import { TipoDocumento } from '../../tipoDocumento/model/tipodocumento';
import { Observable, map } from 'rxjs';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css']
})
export class ClienteComponent {
  listColumnas: string[] = ['id', 'apellidos', 'documento', 'estado', 'nombres', 'tipoDocumento', 'opciones'];
  listaClientes: Cliente[] = [];
  dataSource: MatTableDataSource<Cliente> = new MatTableDataSource<Cliente>([]);
  cliente: Cliente;
  tipoDocumentos:TipoDocumento[] = [];
  
  form!: FormGroup;
  
  isForm!: Promise<any>;
  selectedCliente: Cliente;
  @ViewChild('detalleClienteTemplate', { static: true }) detalleClienteTemplate: TemplateRef<any>;
  
  constructor(private clienteService: ClienteService,
              private formBuilder: FormBuilder,
              private dialog: MatDialog,
              private tipoDocumentoService:TipoDocumentoService) {}
  
  ngOnInit(): void {
    this.cargarData();
    this.iniciarTiposDocumento();
    this.iniciarFormulario();
    
  }

  iniciarTiposDocumento():void{
    this.tipoDocumentoService.obtenerTipoDocumentos().subscribe((response:TipoDocumento[])=>{
      this.tipoDocumentos = response;
    });
  }
  
  cargarData(): void {
    this.clienteService.obtenerClientes().subscribe((response: Cliente[]) => {
      this.dataSource = new MatTableDataSource<Cliente>(response);
      this.listaClientes = response;
      console.log(this.listaClientes);
    });
  }

  verDetalle(cliente: Cliente): void {
    this.selectedCliente = cliente;
    this.dialog.open(this.detalleClienteTemplate);
  }
  
  iniciarFormulario(): void {
    this.isForm = Promise.resolve(
      this.form = this.formBuilder.group({
        apellidos: new FormControl(null, [Validators.required]),
        documento: new FormControl(null, [Validators.required]),
        estado: new FormControl(null, [Validators.required]),
        nombres: new FormControl(null, [Validators.required]),
        tipoDocumento: new FormControl(null, [Validators.required])
      })
    );
  }

  procesarOperacion():void{
    if(this.cliente && this.cliente.id){
      this.actualizarCliente();
    }else{
      this.agregar();
    }
  }
  
  agregar(): void {
    if (this.form.invalid) {
      Swal.fire({
        icon: 'error',
        title: 'Error...',
        text: 'Por favor ingresa todos los campos!',
        confirmButtonColor: '#673AB7',
        cancelButtonText: 'Aceptar',
        confirmButtonText: 'Aceptar'
      });
    } else {
      const values = this.form.value;
      
      let cliente: Cliente = {
        id: 0,
        apellidos: values.apellidos,
        documento: values.documento,
        estado: values.estado,
        nombres: values.nombres,
        tipoDocumento: values.tipoDocumento.id
      };
      this.clienteService.crearCliente(cliente).subscribe((response: Cliente) => {
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Has guardado correctamente el cliente!',
          showConfirmButton: false,
          timer: 1000
        }).then(() => {
          this.cargarData();
        });
      });
    }
  }


  eliminar(clienteId: number): void {
    this.clienteService.eliminarCliente(clienteId).subscribe(response => {
      Swal.fire({
        title: '¿Estás seguro?',
        text: '¡No podrás revertir este cambio!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#673AB7',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminarlo'
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire(
            '¡Eliminado!',
            'El cliente ha sido eliminado.',
            'success'
          );
        }
        this.cargarData();
      });
    });
  }

  obtenerDescripcionTipoDocumentoo(id: number): TipoDocumento {
    const tipoDocumentoEncontrado = this.tipoDocumentos.find(tipoDocumento => tipoDocumento.id === id);
    return tipoDocumentoEncontrado;
  }

  obtenerDescripcionTipoDocumento(id: number): string {
    const tipoDocumentoEncontrado = this.tipoDocumentos.find(tipoDocumento => tipoDocumento.id === id);
    if (tipoDocumentoEncontrado) {
      return tipoDocumentoEncontrado.descripcion;
    } else {
      return 'Tipo de documento desconocido';
    }
  }
  
  editarr(cliente: Cliente): void {
    debugger;
    this.form.get('apellidos').setValue(cliente.apellidos);
    this.form.get('documento').setValue(cliente.documento);
    this.form.get('estado').setValue(cliente.estado);
    this.form.get('nombres').setValue(cliente.nombres);
  
    const tipoDocumentoSeleccionado = this.tipoDocumentos.find(tipoDocumento => tipoDocumento.id === cliente.tipoDocumento.id);

    this.form.get('tipoDocumento').setValue(tipoDocumentoSeleccionado);
    
    this.cliente = cliente;
  }
  
  
  
  actualizarCliente(): void {
    const values = this.form.value;
    let cliente: Cliente = {
      id: this.cliente.id,
      apellidos: values.apellidos,
      documento: values.documento,
      estado: values.estado,
      nombres: values.nombres,
      tipoDocumento: values.tipoDocumento.id
    };
    this.clienteService.actualizarCliente(cliente).subscribe(() => {
      Swal.fire({
        title: '¿Estás seguro de que deseas actualizar el cliente?',
        showDenyButton: true,
        showCancelButton: true,
        confirmButtonText: 'Guardar',
        denyButtonText: 'No guardar'
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire('¡Actualizado!', '', 'success');
          this.cargarData();
        } else if (result.isDenied) {
          Swal.fire('Cambios no guardados', '', 'info');
        }
      });
    });
  }
  
  cerrarDetalleCliente(): void {
    this.selectedCliente = null;
    this.dialog.closeAll();
  }
  
}
