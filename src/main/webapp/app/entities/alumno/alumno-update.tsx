import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMateria } from 'app/shared/model/materia.model';
import { getEntities as getMaterias } from 'app/entities/materia/materia.reducer';
import { getEntity, updateEntity, createEntity, reset } from './alumno.reducer';
import { IAlumno } from 'app/shared/model/alumno.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAlumnoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AlumnoUpdate = (props: IAlumnoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { alumnoEntity, materias, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/alumno' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMaterias();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...alumnoEntity,
        ...values,
        materia: materias.find(it => it.id.toString() === values.materiaId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="controlApp.alumno.home.createOrEditLabel" data-cy="AlumnoCreateUpdateHeading">
            <Translate contentKey="controlApp.alumno.home.createOrEditLabel">Create or edit a Alumno</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : alumnoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="alumno-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="alumno-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idAlumnoLabel" for="alumno-idAlumno">
                  <Translate contentKey="controlApp.alumno.idAlumno">Id Alumno</Translate>
                </Label>
                <AvField
                  id="alumno-idAlumno"
                  data-cy="idAlumno"
                  type="text"
                  name="idAlumno"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="alumnoLabel" for="alumno-alumno">
                  <Translate contentKey="controlApp.alumno.alumno">Alumno</Translate>
                </Label>
                <AvField
                  id="alumno-alumno"
                  data-cy="alumno"
                  type="text"
                  name="alumno"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estatusLabel" for="alumno-estatus">
                  <Translate contentKey="controlApp.alumno.estatus">Estatus</Translate>
                </Label>
                <AvInput
                  id="alumno-estatus"
                  data-cy="estatus"
                  type="select"
                  className="form-control"
                  name="estatus"
                  value={(!isNew && alumnoEntity.estatus) || 'ACTIVO'}
                >
                  <option value="ACTIVO">{translate('controlApp.EstatusRegistro.ACTIVO')}</option>
                  <option value="DESACTIVADO">{translate('controlApp.EstatusRegistro.DESACTIVADO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="alumno-materia">
                  <Translate contentKey="controlApp.alumno.materia">Materia</Translate>
                </Label>
                <AvInput id="alumno-materia" data-cy="materia" type="select" className="form-control" name="materiaId">
                  <option value="" key="0" />
                  {materias
                    ? materias.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.idMateria}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/alumno" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  materias: storeState.materia.entities,
  alumnoEntity: storeState.alumno.entity,
  loading: storeState.alumno.loading,
  updating: storeState.alumno.updating,
  updateSuccess: storeState.alumno.updateSuccess,
});

const mapDispatchToProps = {
  getMaterias,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AlumnoUpdate);
