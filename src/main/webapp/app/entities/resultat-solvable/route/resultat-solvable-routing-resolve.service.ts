import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResultatSolvable } from '../resultat-solvable.model';
import { ResultatSolvableService } from '../service/resultat-solvable.service';

const resultatSolvableResolve = (route: ActivatedRouteSnapshot): Observable<null | IResultatSolvable> => {
  const id = route.params.id;
  if (id) {
    return inject(ResultatSolvableService)
      .find(id)
      .pipe(
        mergeMap((resultatSolvable: HttpResponse<IResultatSolvable>) => {
          if (resultatSolvable.body) {
            return of(resultatSolvable.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default resultatSolvableResolve;
