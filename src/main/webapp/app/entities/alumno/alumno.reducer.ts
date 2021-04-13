import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAlumno, defaultValue } from 'app/shared/model/alumno.model';

export const ACTION_TYPES = {
  FETCH_ALUMNO_LIST: 'alumno/FETCH_ALUMNO_LIST',
  FETCH_ALUMNO: 'alumno/FETCH_ALUMNO',
  CREATE_ALUMNO: 'alumno/CREATE_ALUMNO',
  UPDATE_ALUMNO: 'alumno/UPDATE_ALUMNO',
  PARTIAL_UPDATE_ALUMNO: 'alumno/PARTIAL_UPDATE_ALUMNO',
  DELETE_ALUMNO: 'alumno/DELETE_ALUMNO',
  RESET: 'alumno/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAlumno>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AlumnoState = Readonly<typeof initialState>;

// Reducer

export default (state: AlumnoState = initialState, action): AlumnoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ALUMNO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ALUMNO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ALUMNO):
    case REQUEST(ACTION_TYPES.UPDATE_ALUMNO):
    case REQUEST(ACTION_TYPES.DELETE_ALUMNO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ALUMNO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ALUMNO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ALUMNO):
    case FAILURE(ACTION_TYPES.CREATE_ALUMNO):
    case FAILURE(ACTION_TYPES.UPDATE_ALUMNO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ALUMNO):
    case FAILURE(ACTION_TYPES.DELETE_ALUMNO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ALUMNO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ALUMNO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ALUMNO):
    case SUCCESS(ACTION_TYPES.UPDATE_ALUMNO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ALUMNO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ALUMNO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/alumnos';

// Actions

export const getEntities: ICrudGetAllAction<IAlumno> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ALUMNO_LIST,
    payload: axios.get<IAlumno>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAlumno> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ALUMNO,
    payload: axios.get<IAlumno>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAlumno> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ALUMNO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAlumno> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ALUMNO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAlumno> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ALUMNO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAlumno> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ALUMNO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
