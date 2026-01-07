import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { StatutService } from '../service/statut.service';
import { IStatut } from '../statut.model';

const statutResolve = (route: ActivatedRouteSnapshot): Observable<null | IStatut> => {
  const id = route.params.id;
  if (id) {
    return inject(StatutService)
      .find(id)
      .pipe(
        mergeMap((statut: HttpResponse<IStatut>) => {
          if (statut.body) {
            return of(statut.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default statutResolve;
