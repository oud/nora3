import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import DefaillanceResolve from './route/defaillance-routing-resolve.service';

const defaillanceRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/defaillance').then(m => m.Defaillance),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/defaillance-detail').then(m => m.DefaillanceDetail),
    resolve: {
      defaillance: DefaillanceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/defaillance-update').then(m => m.DefaillanceUpdate),
    resolve: {
      defaillance: DefaillanceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/defaillance-update').then(m => m.DefaillanceUpdate),
    resolve: {
      defaillance: DefaillanceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default defaillanceRoute;
