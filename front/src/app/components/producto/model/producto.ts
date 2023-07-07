import { Categoria } from "../../categoria/model/categoria";

export interface Producto {

    id?:number;
    referencia:string;
    nombre:string;
    descripcion:string;
    precioUnitario:number;
    unidadesDisponibles:number;
    categoria:Categoria;
}
