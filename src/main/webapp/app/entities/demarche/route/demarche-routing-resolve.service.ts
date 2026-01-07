import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemarche } from '../demarche.model';
import { DemarcheService } from '../service/demarche.service';

const demarcheResolve = (route: ActivatedRouteSnapshot): Observable<null | IDemarche> => {
  const id = route.params.id;
  if (id) {
    return inject(DemarcheService)
      .find(id)
      .pipe(
        mergeMap((demarche: HttpResponse<IDemarche>) => {
          if (demarche.body) {
            return of(demarche.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default demarcheResolve;
