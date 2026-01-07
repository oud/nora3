import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { SituationService } from '../service/situation.service';
import { ISituation } from '../situation.model';

const situationResolve = (route: ActivatedRouteSnapshot): Observable<null | ISituation> => {
  const id = route.params.id;
  if (id) {
    return inject(SituationService)
      .find(id)
      .pipe(
        mergeMap((situation: HttpResponse<ISituation>) => {
          if (situation.body) {
            return of(situation.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default situationResolve;
