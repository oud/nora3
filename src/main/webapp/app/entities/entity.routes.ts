import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'nora3App.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'dossier',
    data: { pageTitle: 'nora3App.dossier.home.title' },
    loadChildren: () => import('./dossier/dossier.routes'),
  },
  {
    path: 'statut',
    data: { pageTitle: 'nora3App.statut.home.title' },
    loadChildren: () => import('./statut/statut.routes'),
  },
  {
    path: 'creancier',
    data: { pageTitle: 'nora3App.creancier.home.title' },
    loadChildren: () => import('./creancier/creancier.routes'),
  },
  {
    path: 'debiteur',
    data: { pageTitle: 'nora3App.debiteur.home.title' },
    loadChildren: () => import('./debiteur/debiteur.routes'),
  },
  {
    path: 'enfant',
    data: { pageTitle: 'nora3App.enfant.home.title' },
    loadChildren: () => import('./enfant/enfant.routes'),
  },
  {
    path: 'demarche',
    data: { pageTitle: 'nora3App.demarche.home.title' },
    loadChildren: () => import('./demarche/demarche.routes'),
  },
  {
    path: 'situation',
    data: { pageTitle: 'nora3App.situation.home.title' },
    loadChildren: () => import('./situation/situation.routes'),
  },
  {
    path: 'recherche-solvable',
    data: { pageTitle: 'nora3App.rechercheSolvable.home.title' },
    loadChildren: () => import('./recherche-solvable/recherche-solvable.routes'),
  },
  {
    path: 'resultat-solvable',
    data: { pageTitle: 'nora3App.resultatSolvable.home.title' },
    loadChildren: () => import('./resultat-solvable/resultat-solvable.routes'),
  },
  {
    path: 'defaillance',
    data: { pageTitle: 'nora3App.defaillance.home.title' },
    loadChildren: () => import('./defaillance/defaillance.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
