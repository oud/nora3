import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEnfant } from '../enfant.model';
import { EnfantService } from '../service/enfant.service';

const enfantResolve = (route: ActivatedRouteSnapshot): Observable<null | IEnfant> => {
  const id = route.params.id;
  if (id) {
    return inject(EnfantService)
      .find(id)
      .pipe(
        mergeMap((enfant: HttpResponse<IEnfant>) => {
          if (enfant.body) {
            return of(enfant.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default enfantResolve;
