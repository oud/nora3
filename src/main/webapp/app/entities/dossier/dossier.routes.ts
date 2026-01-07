import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import DossierResolve from './route/dossier-routing-resolve.service';

const dossierRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/dossier').then(m => m.Dossier),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/dossier-detail').then(m => m.DossierDetail),
    resolve: {
      dossier: DossierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/dossier-update').then(m => m.DossierUpdate),
    resolve: {
      dossier: DossierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/dossier-update').then(m => m.DossierUpdate),
    resolve: {
      dossier: DossierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dossierRoute;
