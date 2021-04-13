import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './alumno.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAlumnoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AlumnoDetail = (props: IAlumnoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { alumnoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alumnoDetailsHeading">
          <Translate contentKey="controlApp.alumno.detail.title">Alumno</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{alumnoEntity.id}</dd>
          <dt>
            <span id="idAlumno">
              <Translate contentKey="controlApp.alumno.idAlumno">Id Alumno</Translate>
            </span>
          </dt>
          <dd>{alumnoEntity.idAlumno}</dd>
          <dt>
            <span id="alumno">
              <Translate contentKey="controlApp.alumno.alumno">Alumno</Translate>
            </span>
          </dt>
          <dd>{alumnoEntity.alumno}</dd>
          <dt>
            <span id="estatus">
              <Translate contentKey="controlApp.alumno.estatus">Estatus</Translate>
            </span>
          </dt>
          <dd>{alumnoEntity.estatus}</dd>
          <dt>
            <Translate contentKey="controlApp.alumno.materia">Materia</Translate>
          </dt>
          <dd>{alumnoEntity.materia ? alumnoEntity.materia.idMateria : ''}</dd>
        </dl>
        <Button tag={Link} to="/alumno" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alumno/${alumnoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ alumno }: IRootState) => ({
  alumnoEntity: alumno.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AlumnoDetail);
