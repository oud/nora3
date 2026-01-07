import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import DebiteurResolve from './route/debiteur-routing-resolve.service';

const debiteurRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/debiteur').then(m => m.Debiteur),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/debiteur-detail').then(m => m.DebiteurDetail),
    resolve: {
      debiteur: DebiteurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/debiteur-update').then(m => m.DebiteurUpdate),
    resolve: {
      debiteur: DebiteurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/debiteur-update').then(m => m.DebiteurUpdate),
    resolve: {
      debiteur: DebiteurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default debiteurRoute;
