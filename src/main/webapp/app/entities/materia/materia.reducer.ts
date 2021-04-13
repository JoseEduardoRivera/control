import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMateria, defaultValue } from 'app/shared/model/materia.model';

export const ACTION_TYPES = {
  FETCH_MATERIA_LIST: 'materia/FETCH_MATERIA_LIST',
  FETCH_MATERIA: 'materia/FETCH_MATERIA',
  CREATE_MATERIA: 'materia/CREATE_MATERIA',
  UPDATE_MATERIA: 'materia/UPDATE_MATERIA',
  PARTIAL_UPDATE_MATERIA: 'materia/PARTIAL_UPDATE_MATERIA',
  DELETE_MATERIA: 'materia/DELETE_MATERIA',
  RESET: 'materia/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMateria>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MateriaState = Readonly<typeof initialState>;

// Reducer

export default (state: MateriaState = initialState, action): MateriaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MATERIA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MATERIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MATERIA):
    case REQUEST(ACTION_TYPES.UPDATE_MATERIA):
    case REQUEST(ACTION_TYPES.DELETE_MATERIA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_MATERIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MATERIA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MATERIA):
    case FAILURE(ACTION_TYPES.CREATE_MATERIA):
    case FAILURE(ACTION_TYPES.UPDATE_MATERIA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_MATERIA):
    case FAILURE(ACTION_TYPES.DELETE_MATERIA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MATERIA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MATERIA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MATERIA):
    case SUCCESS(ACTION_TYPES.UPDATE_MATERIA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_MATERIA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MATERIA):
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

const apiUrl = 'api/materias';

// Actions

export const getEntities: ICrudGetAllAction<IMateria> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MATERIA_LIST,
    payload: axios.get<IMateria>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMateria> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MATERIA,
    payload: axios.get<IMateria>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMateria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MATERIA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMateria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MATERIA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IMateria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_MATERIA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMateria> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MATERIA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
