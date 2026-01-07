import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import SituationResolve from './route/situation-routing-resolve.service';

const situationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/situation').then(m => m.Situation),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/situation-detail').then(m => m.SituationDetail),
    resolve: {
      situation: SituationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/situation-update').then(m => m.SituationUpdate),
    resolve: {
      situation: SituationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/situation-update').then(m => m.SituationUpdate),
    resolve: {
      situation: SituationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default situationRoute;
