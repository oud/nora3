import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import DemarcheResolve from './route/demarche-routing-resolve.service';

const demarcheRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/demarche').then(m => m.Demarche),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/demarche-detail').then(m => m.DemarcheDetail),
    resolve: {
      demarche: DemarcheResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/demarche-update').then(m => m.DemarcheUpdate),
    resolve: {
      demarche: DemarcheResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/demarche-update').then(m => m.DemarcheUpdate),
    resolve: {
      demarche: DemarcheResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default demarcheRoute;
