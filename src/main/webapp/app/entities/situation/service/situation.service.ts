import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ISituation, NewSituation } from '../situation.model';

export type PartialUpdateSituation = Partial<ISituation> & Pick<ISituation, 'id'>;

type RestOf<T extends ISituation | NewSituation> = Omit<T, 'situationProDebutDate' | 'situationProfinDate'> & {
  situationProDebutDate?: string | null;
  situationProfinDate?: string | null;
};

export type RestSituation = RestOf<ISituation>;

export type NewRestSituation = RestOf<NewSituation>;

export type PartialUpdateRestSituation = RestOf<PartialUpdateSituation>;

export type EntityResponseType = HttpResponse<ISituation>;
export type EntityArrayResponseType = HttpResponse<ISituation[]>;

@Injectable({ providedIn: 'root' })
export class SituationService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/situations');

  create(situation: NewSituation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situation);
    return this.http
      .post<RestSituation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(situation: ISituation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situation);
    return this.http
      .put<RestSituation>(`${this.resourceUrl}/${encodeURIComponent(this.getSituationIdentifier(situation))}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(situation: PartialUpdateSituation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situation);
    return this.http
      .patch<RestSituation>(`${this.resourceUrl}/${encodeURIComponent(this.getSituationIdentifier(situation))}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSituation>(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSituation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  getSituationIdentifier(situation: Pick<ISituation, 'id'>): number {
    return situation.id;
  }

  compareSituation(o1: Pick<ISituation, 'id'> | null, o2: Pick<ISituation, 'id'> | null): boolean {
    return o1 && o2 ? this.getSituationIdentifier(o1) === this.getSituationIdentifier(o2) : o1 === o2;
  }

  addSituationToCollectionIfMissing<Type extends Pick<ISituation, 'id'>>(
    situationCollection: Type[],
    ...situationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const situations: Type[] = situationsToCheck.filter(isPresent);
    if (situations.length > 0) {
      const situationCollectionIdentifiers = situationCollection.map(situationItem => this.getSituationIdentifier(situationItem));
      const situationsToAdd = situations.filter(situationItem => {
        const situationIdentifier = this.getSituationIdentifier(situationItem);
        if (situationCollectionIdentifiers.includes(situationIdentifier)) {
          return false;
        }
        situationCollectionIdentifiers.push(situationIdentifier);
        return true;
      });
      return [...situationsToAdd, ...situationCollection];
    }
    return situationCollection;
  }

  protected convertDateFromClient<T extends ISituation | NewSituation | PartialUpdateSituation>(situation: T): RestOf<T> {
    return {
      ...situation,
      situationProDebutDate: situation.situationProDebutDate?.format(DATE_FORMAT) ?? null,
      situationProfinDate: situation.situationProfinDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restSituation: RestSituation): ISituation {
    return {
      ...restSituation,
      situationProDebutDate: restSituation.situationProDebutDate ? dayjs(restSituation.situationProDebutDate) : undefined,
      situationProfinDate: restSituation.situationProfinDate ? dayjs(restSituation.situationProfinDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSituation>): HttpResponse<ISituation> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSituation[]>): HttpResponse<ISituation[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
