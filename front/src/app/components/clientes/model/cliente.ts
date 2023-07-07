import { TipoDocumento } from "../../tipoDocumento/model/tipodocumento";

export interface Cliente {
    id?: number;
    apellidos:string;
    documento:string;
    estado:string;
    nombres: string;
    tipoDocumento:TipoDocumento;

}
