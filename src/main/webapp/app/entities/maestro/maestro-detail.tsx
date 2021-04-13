import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './maestro.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMaestroDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MaestroDetail = (props: IMaestroDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { maestroEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="maestroDetailsHeading">
          <Translate contentKey="controlApp.maestro.detail.title">Maestro</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{maestroEntity.id}</dd>
          <dt>
            <span id="idMaestro">
              <Translate contentKey="controlApp.maestro.idMaestro">Id Maestro</Translate>
            </span>
          </dt>
          <dd>{maestroEntity.idMaestro}</dd>
          <dt>
            <span id="maestro">
              <Translate contentKey="controlApp.maestro.maestro">Maestro</Translate>
            </span>
          </dt>
          <dd>{maestroEntity.maestro}</dd>
          <dt>
            <span id="estatus">
              <Translate contentKey="controlApp.maestro.estatus">Estatus</Translate>
            </span>
          </dt>
          <dd>{maestroEntity.estatus}</dd>
        </dl>
        <Button tag={Link} to="/maestro" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/maestro/${maestroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ maestro }: IRootState) => ({
  maestroEntity: maestro.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MaestroDetail);
