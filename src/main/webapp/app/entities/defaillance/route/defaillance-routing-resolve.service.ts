import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDefaillance } from '../defaillance.model';
import { DefaillanceService } from '../service/defaillance.service';

const defaillanceResolve = (route: ActivatedRouteSnapshot): Observable<null | IDefaillance> => {
  const id = route.params.id;
  if (id) {
    return inject(DefaillanceService)
      .find(id)
      .pipe(
        mergeMap((defaillance: HttpResponse<IDefaillance>) => {
          if (defaillance.body) {
            return of(defaillance.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default defaillanceResolve;
