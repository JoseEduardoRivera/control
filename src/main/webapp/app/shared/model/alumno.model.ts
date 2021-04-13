import { IMateria } from 'app/shared/model/materia.model';
import { EstatusRegistro } from 'app/shared/model/enumerations/estatus-registro.model';

export interface IAlumno {
  id?: number;
  idAlumno?: string;
  alumno?: string;
  estatus?: EstatusRegistro;
  materia?: IMateria | null;
}

export const defaultValue: Readonly<IAlumno> = {};
