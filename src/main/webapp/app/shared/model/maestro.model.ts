import { EstatusRegistro } from 'app/shared/model/enumerations/estatus-registro.model';

export interface IMaestro {
  id?: number;
  idMaestro?: string;
  maestro?: string;
  estatus?: EstatusRegistro;
}

export const defaultValue: Readonly<IMaestro> = {};
