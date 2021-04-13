import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Alumno from './alumno';
import AlumnoDetail from './alumno-detail';
import AlumnoUpdate from './alumno-update';
import AlumnoDeleteDialog from './alumno-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AlumnoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AlumnoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AlumnoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Alumno} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AlumnoDeleteDialog} />
  </>
);

export default Routes;
