import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRechercheSolvable } from '../recherche-solvable.model';
import { RechercheSolvableService } from '../service/recherche-solvable.service';

const rechercheSolvableResolve = (route: ActivatedRouteSnapshot): Observable<null | IRechercheSolvable> => {
  const id = route.params.id;
  if (id) {
    return inject(RechercheSolvableService)
      .find(id)
      .pipe(
        mergeMap((rechercheSolvable: HttpResponse<IRechercheSolvable>) => {
          if (rechercheSolvable.body) {
            return of(rechercheSolvable.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default rechercheSolvableResolve;
