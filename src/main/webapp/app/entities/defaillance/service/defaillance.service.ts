import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IDefaillance, NewDefaillance } from '../defaillance.model';

export type PartialUpdateDefaillance = Partial<IDefaillance> & Pick<IDefaillance, 'id'>;

type RestOf<T extends IDefaillance | NewDefaillance> = Omit<T, 'moisDefaillance'> & {
  moisDefaillance?: string | null;
};

export type RestDefaillance = RestOf<IDefaillance>;

export type NewRestDefaillance = RestOf<NewDefaillance>;

export type PartialUpdateRestDefaillance = RestOf<PartialUpdateDefaillance>;

export type EntityResponseType = HttpResponse<IDefaillance>;
export type EntityArrayResponseType = HttpResponse<IDefaillance[]>;

@Injectable({ providedIn: 'root' })
export class DefaillanceService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/defaillances');

  create(defaillance: NewDefaillance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(defaillance);
    return this.http
      .post<RestDefaillance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(defaillance: IDefaillance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(defaillance);
    return this.http
      .put<RestDefaillance>(`${this.resourceUrl}/${encodeURIComponent(this.getDefaillanceIdentifier(defaillance))}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(defaillance: PartialUpdateDefaillance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(defaillance);
    return this.http
      .patch<RestDefaillance>(`${this.resourceUrl}/${encodeURIComponent(this.getDefaillanceIdentifier(defaillance))}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDefaillance>(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDefaillance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  getDefaillanceIdentifier(defaillance: Pick<IDefaillance, 'id'>): number {
    return defaillance.id;
  }

  compareDefaillance(o1: Pick<IDefaillance, 'id'> | null, o2: Pick<IDefaillance, 'id'> | null): boolean {
    return o1 && o2 ? this.getDefaillanceIdentifier(o1) === this.getDefaillanceIdentifier(o2) : o1 === o2;
  }

  addDefaillanceToCollectionIfMissing<Type extends Pick<IDefaillance, 'id'>>(
    defaillanceCollection: Type[],
    ...defaillancesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const defaillances: Type[] = defaillancesToCheck.filter(isPresent);
    if (defaillances.length > 0) {
      const defaillanceCollectionIdentifiers = defaillanceCollection.map(defaillanceItem => this.getDefaillanceIdentifier(defaillanceItem));
      const defaillancesToAdd = defaillances.filter(defaillanceItem => {
        const defaillanceIdentifier = this.getDefaillanceIdentifier(defaillanceItem);
        if (defaillanceCollectionIdentifiers.includes(defaillanceIdentifier)) {
          return false;
        }
        defaillanceCollectionIdentifiers.push(defaillanceIdentifier);
        return true;
      });
      return [...defaillancesToAdd, ...defaillanceCollection];
    }
    return defaillanceCollection;
  }

  protected convertDateFromClient<T extends IDefaillance | NewDefaillance | PartialUpdateDefaillance>(defaillance: T): RestOf<T> {
    return {
      ...defaillance,
      moisDefaillance: defaillance.moisDefaillance?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restDefaillance: RestDefaillance): IDefaillance {
    return {
      ...restDefaillance,
      moisDefaillance: restDefaillance.moisDefaillance ? dayjs(restDefaillance.moisDefaillance) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDefaillance>): HttpResponse<IDefaillance> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDefaillance[]>): HttpResponse<IDefaillance[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
