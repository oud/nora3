import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDossier } from '../dossier.model';
import { DossierService } from '../service/dossier.service';

const dossierResolve = (route: ActivatedRouteSnapshot): Observable<null | IDossier> => {
  const id = route.params.id;
  if (id) {
    return inject(DossierService)
      .find(id)
      .pipe(
        mergeMap((dossier: HttpResponse<IDossier>) => {
          if (dossier.body) {
            return of(dossier.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default dossierResolve;
