import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Materia from './materia';
import MateriaDetail from './materia-detail';
import MateriaUpdate from './materia-update';
import MateriaDeleteDialog from './materia-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MateriaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MateriaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MateriaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Materia} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MateriaDeleteDialog} />
  </>
);

export default Routes;
