import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './maestro.reducer';
import { IMaestro } from 'app/shared/model/maestro.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMaestroUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MaestroUpdate = (props: IMaestroUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { maestroEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/maestro' + props.location.search);
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
        ...maestroEntity,
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
          <h2 id="controlApp.maestro.home.createOrEditLabel" data-cy="MaestroCreateUpdateHeading">
            <Translate contentKey="controlApp.maestro.home.createOrEditLabel">Create or edit a Maestro</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : maestroEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="maestro-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="maestro-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idMaestroLabel" for="maestro-idMaestro">
                  <Translate contentKey="controlApp.maestro.idMaestro">Id Maestro</Translate>
                </Label>
                <AvField
                  id="maestro-idMaestro"
                  data-cy="idMaestro"
                  type="text"
                  name="idMaestro"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="maestroLabel" for="maestro-maestro">
                  <Translate contentKey="controlApp.maestro.maestro">Maestro</Translate>
                </Label>
                <AvField
                  id="maestro-maestro"
                  data-cy="maestro"
                  type="text"
                  name="maestro"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estatusLabel" for="maestro-estatus">
                  <Translate contentKey="controlApp.maestro.estatus">Estatus</Translate>
                </Label>
                <AvInput
                  id="maestro-estatus"
                  data-cy="estatus"
                  type="select"
                  className="form-control"
                  name="estatus"
                  value={(!isNew && maestroEntity.estatus) || 'ACTIVO'}
                >
                  <option value="ACTIVO">{translate('controlApp.EstatusRegistro.ACTIVO')}</option>
                  <option value="DESACTIVADO">{translate('controlApp.EstatusRegistro.DESACTIVADO')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/maestro" replace color="info">
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
  maestroEntity: storeState.maestro.entity,
  loading: storeState.maestro.loading,
  updating: storeState.maestro.updating,
  updateSuccess: storeState.maestro.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MaestroUpdate);
