import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './materia.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMateriaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MateriaDetail = (props: IMateriaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { materiaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materiaDetailsHeading">
          <Translate contentKey="controlApp.materia.detail.title">Materia</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{materiaEntity.id}</dd>
          <dt>
            <span id="idMateria">
              <Translate contentKey="controlApp.materia.idMateria">Id Materia</Translate>
            </span>
          </dt>
          <dd>{materiaEntity.idMateria}</dd>
          <dt>
            <span id="materia">
              <Translate contentKey="controlApp.materia.materia">Materia</Translate>
            </span>
          </dt>
          <dd>{materiaEntity.materia}</dd>
          <dt>
            <span id="abreviatura">
              <Translate contentKey="controlApp.materia.abreviatura">Abreviatura</Translate>
            </span>
          </dt>
          <dd>{materiaEntity.abreviatura}</dd>
          <dt>
            <span id="estatus">
              <Translate contentKey="controlApp.materia.estatus">Estatus</Translate>
            </span>
          </dt>
          <dd>{materiaEntity.estatus}</dd>
        </dl>
        <Button tag={Link} to="/materia" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/materia/${materiaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ materia }: IRootState) => ({
  materiaEntity: materia.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MateriaDetail);
