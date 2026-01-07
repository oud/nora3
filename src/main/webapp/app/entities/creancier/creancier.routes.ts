import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CreancierResolve from './route/creancier-routing-resolve.service';

const creancierRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/creancier').then(m => m.Creancier),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/creancier-detail').then(m => m.CreancierDetail),
    resolve: {
      creancier: CreancierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/creancier-update').then(m => m.CreancierUpdate),
    resolve: {
      creancier: CreancierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/creancier-update').then(m => m.CreancierUpdate),
    resolve: {
      creancier: CreancierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default creancierRoute;
