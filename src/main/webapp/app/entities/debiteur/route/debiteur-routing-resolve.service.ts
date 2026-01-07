import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDebiteur } from '../debiteur.model';
import { DebiteurService } from '../service/debiteur.service';

const debiteurResolve = (route: ActivatedRouteSnapshot): Observable<null | IDebiteur> => {
  const id = route.params.id;
  if (id) {
    return inject(DebiteurService)
      .find(id)
      .pipe(
        mergeMap((debiteur: HttpResponse<IDebiteur>) => {
          if (debiteur.body) {
            return of(debiteur.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default debiteurResolve;
