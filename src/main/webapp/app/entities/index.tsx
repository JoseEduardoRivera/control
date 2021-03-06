import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Maestro from './maestro';
import Materia from './materia';
import Alumno from './alumno';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}maestro`} component={Maestro} />
      <ErrorBoundaryRoute path={`${match.url}materia`} component={Materia} />
      <ErrorBoundaryRoute path={`${match.url}alumno`} component={Alumno} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
