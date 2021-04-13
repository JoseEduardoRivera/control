import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMaestro, defaultValue } from 'app/shared/model/maestro.model';

export const ACTION_TYPES = {
  FETCH_MAESTRO_LIST: 'maestro/FETCH_MAESTRO_LIST',
  FETCH_MAESTRO: 'maestro/FETCH_MAESTRO',
  CREATE_MAESTRO: 'maestro/CREATE_MAESTRO',
  UPDATE_MAESTRO: 'maestro/UPDATE_MAESTRO',
  PARTIAL_UPDATE_MAESTRO: 'maestro/PARTIAL_UPDATE_MAESTRO',
  DELETE_MAESTRO: 'maestro/DELETE_MAESTRO',
  RESET: 'maestro/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMaestro>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MaestroState = Readonly<typeof initialState>;

// Reducer

export default (state: MaestroState = initialState, action): MaestroState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MAESTRO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MAESTRO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MAESTRO):
    case REQUEST(ACTION_TYPES.UPDATE_MAESTRO):
    case REQUEST(ACTION_TYPES.DELETE_MAESTRO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_MAESTRO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MAESTRO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MAESTRO):
    case FAILURE(ACTION_TYPES.CREATE_MAESTRO):
    case FAILURE(ACTION_TYPES.UPDATE_MAESTRO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_MAESTRO):
    case FAILURE(ACTION_TYPES.DELETE_MAESTRO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MAESTRO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MAESTRO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MAESTRO):
    case SUCCESS(ACTION_TYPES.UPDATE_MAESTRO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_MAESTRO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MAESTRO):
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

const apiUrl = 'api/maestros';

// Actions

export const getEntities: ICrudGetAllAction<IMaestro> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MAESTRO_LIST,
    payload: axios.get<IMaestro>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMaestro> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MAESTRO,
    payload: axios.get<IMaestro>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMaestro> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MAESTRO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMaestro> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MAESTRO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IMaestro> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_MAESTRO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMaestro> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MAESTRO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
