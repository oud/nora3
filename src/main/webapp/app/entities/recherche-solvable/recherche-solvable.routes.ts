import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import RechercheSolvableResolve from './route/recherche-solvable-routing-resolve.service';

const rechercheSolvableRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/recherche-solvable').then(m => m.RechercheSolvable),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/recherche-solvable-detail').then(m => m.RechercheSolvableDetail),
    resolve: {
      rechercheSolvable: RechercheSolvableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/recherche-solvable-update').then(m => m.RechercheSolvableUpdate),
    resolve: {
      rechercheSolvable: RechercheSolvableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/recherche-solvable-update').then(m => m.RechercheSolvableUpdate),
    resolve: {
      rechercheSolvable: RechercheSolvableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default rechercheSolvableRoute;
