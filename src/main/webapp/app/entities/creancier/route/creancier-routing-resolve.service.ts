import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICreancier } from '../creancier.model';
import { CreancierService } from '../service/creancier.service';

const creancierResolve = (route: ActivatedRouteSnapshot): Observable<null | ICreancier> => {
  const id = route.params.id;
  if (id) {
    return inject(CreancierService)
      .find(id)
      .pipe(
        mergeMap((creancier: HttpResponse<ICreancier>) => {
          if (creancier.body) {
            return of(creancier.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default creancierResolve;
