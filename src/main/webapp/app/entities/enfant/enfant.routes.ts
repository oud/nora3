import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import EnfantResolve from './route/enfant-routing-resolve.service';

const enfantRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/enfant').then(m => m.Enfant),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/enfant-detail').then(m => m.EnfantDetail),
    resolve: {
      enfant: EnfantResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/enfant-update').then(m => m.EnfantUpdate),
    resolve: {
      enfant: EnfantResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/enfant-update').then(m => m.EnfantUpdate),
    resolve: {
      enfant: EnfantResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default enfantRoute;
