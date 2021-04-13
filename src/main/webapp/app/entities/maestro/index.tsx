import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Maestro from './maestro';
import MaestroDetail from './maestro-detail';
import MaestroUpdate from './maestro-update';
import MaestroDeleteDialog from './maestro-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MaestroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MaestroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MaestroDetail} />
      <ErrorBoundaryRoute path={match.url} component={Maestro} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MaestroDeleteDialog} />
  </>
);

export default Routes;
