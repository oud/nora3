import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import StatutResolve from './route/statut-routing-resolve.service';

const statutRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/statut').then(m => m.Statut),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/statut-detail').then(m => m.StatutDetail),
    resolve: {
      statut: StatutResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/statut-update').then(m => m.StatutUpdate),
    resolve: {
      statut: StatutResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/statut-update').then(m => m.StatutUpdate),
    resolve: {
      statut: StatutResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default statutRoute;
