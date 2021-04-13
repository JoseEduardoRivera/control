import { IAlumno } from 'app/shared/model/alumno.model';
import { EstatusRegistro } from 'app/shared/model/enumerations/estatus-registro.model';

export interface IMateria {
  id?: number;
  idMateria?: string;
  materia?: string;
  abreviatura?: string;
  estatus?: EstatusRegistro;
  alumnos?: IAlumno[] | null;
}

export const defaultValue: Readonly<IMateria> = {};
