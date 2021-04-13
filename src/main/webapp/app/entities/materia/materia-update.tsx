import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './materia.reducer';
import { IMateria } from 'app/shared/model/materia.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMateriaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MateriaUpdate = (props: IMateriaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { materiaEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/materia' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...materiaEntity,
        ...values,
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
          <h2 id="controlApp.materia.home.createOrEditLabel" data-cy="MateriaCreateUpdateHeading">
            <Translate contentKey="controlApp.materia.home.createOrEditLabel">Create or edit a Materia</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : materiaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="materia-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="materia-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idMateriaLabel" for="materia-idMateria">
                  <Translate contentKey="controlApp.materia.idMateria">Id Materia</Translate>
                </Label>
                <AvField
                  id="materia-idMateria"
                  data-cy="idMateria"
                  type="text"
                  name="idMateria"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="materiaLabel" for="materia-materia">
                  <Translate contentKey="controlApp.materia.materia">Materia</Translate>
                </Label>
                <AvField
                  id="materia-materia"
                  data-cy="materia"
                  type="text"
                  name="materia"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="abreviaturaLabel" for="materia-abreviatura">
                  <Translate contentKey="controlApp.materia.abreviatura">Abreviatura</Translate>
                </Label>
                <AvField
                  id="materia-abreviatura"
                  data-cy="abreviatura"
                  type="text"
                  name="abreviatura"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estatusLabel" for="materia-estatus">
                  <Translate contentKey="controlApp.materia.estatus">Estatus</Translate>
                </Label>
                <AvInput
                  id="materia-estatus"
                  data-cy="estatus"
                  type="select"
                  className="form-control"
                  name="estatus"
                  value={(!isNew && materiaEntity.estatus) || 'ACTIVO'}
                >
                  <option value="ACTIVO">{translate('controlApp.EstatusRegistro.ACTIVO')}</option>
                  <option value="DESACTIVADO">{translate('controlApp.EstatusRegistro.DESACTIVADO')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/materia" replace color="info">
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
  materiaEntity: storeState.materia.entity,
  loading: storeState.materia.loading,
  updating: storeState.materia.updating,
  updateSuccess: storeState.materia.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MateriaUpdate);
