import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import ResultatSolvableResolve from './route/resultat-solvable-routing-resolve.service';

const resultatSolvableRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/resultat-solvable').then(m => m.ResultatSolvable),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/resultat-solvable-detail').then(m => m.ResultatSolvableDetail),
    resolve: {
      resultatSolvable: ResultatSolvableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/resultat-solvable-update').then(m => m.ResultatSolvableUpdate),
    resolve: {
      resultatSolvable: ResultatSolvableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/resultat-solvable-update').then(m => m.ResultatSolvableUpdate),
    resolve: {
      resultatSolvable: ResultatSolvableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default resultatSolvableRoute;
