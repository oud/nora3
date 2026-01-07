import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IResultatSolvable, NewResultatSolvable } from '../resultat-solvable.model';

export type PartialUpdateResultatSolvable = Partial<IResultatSolvable> & Pick<IResultatSolvable, 'id'>;

type RestOf<T extends IResultatSolvable | NewResultatSolvable> = Omit<T, 'moisSolvabiliteDate'> & {
  moisSolvabiliteDate?: string | null;
};

export type RestResultatSolvable = RestOf<IResultatSolvable>;

export type NewRestResultatSolvable = RestOf<NewResultatSolvable>;

export type PartialUpdateRestResultatSolvable = RestOf<PartialUpdateResultatSolvable>;

export type EntityResponseType = HttpResponse<IResultatSolvable>;
export type EntityArrayResponseType = HttpResponse<IResultatSolvable[]>;

@Injectable({ providedIn: 'root' })
export class ResultatSolvableService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resultat-solvables');

  create(resultatSolvable: NewResultatSolvable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultatSolvable);
    return this.http
      .post<RestResultatSolvable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(resultatSolvable: IResultatSolvable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultatSolvable);
    return this.http
      .put<RestResultatSolvable>(`${this.resourceUrl}/${encodeURIComponent(this.getResultatSolvableIdentifier(resultatSolvable))}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(resultatSolvable: PartialUpdateResultatSolvable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultatSolvable);
    return this.http
      .patch<RestResultatSolvable>(
        `${this.resourceUrl}/${encodeURIComponent(this.getResultatSolvableIdentifier(resultatSolvable))}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestResultatSolvable>(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestResultatSolvable[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  getResultatSolvableIdentifier(resultatSolvable: Pick<IResultatSolvable, 'id'>): number {
    return resultatSolvable.id;
  }

  compareResultatSolvable(o1: Pick<IResultatSolvable, 'id'> | null, o2: Pick<IResultatSolvable, 'id'> | null): boolean {
    return o1 && o2 ? this.getResultatSolvableIdentifier(o1) === this.getResultatSolvableIdentifier(o2) : o1 === o2;
  }

  addResultatSolvableToCollectionIfMissing<Type extends Pick<IResultatSolvable, 'id'>>(
    resultatSolvableCollection: Type[],
    ...resultatSolvablesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const resultatSolvables: Type[] = resultatSolvablesToCheck.filter(isPresent);
    if (resultatSolvables.length > 0) {
      const resultatSolvableCollectionIdentifiers = resultatSolvableCollection.map(resultatSolvableItem =>
        this.getResultatSolvableIdentifier(resultatSolvableItem),
      );
      const resultatSolvablesToAdd = resultatSolvables.filter(resultatSolvableItem => {
        const resultatSolvableIdentifier = this.getResultatSolvableIdentifier(resultatSolvableItem);
        if (resultatSolvableCollectionIdentifiers.includes(resultatSolvableIdentifier)) {
          return false;
        }
        resultatSolvableCollectionIdentifiers.push(resultatSolvableIdentifier);
        return true;
      });
      return [...resultatSolvablesToAdd, ...resultatSolvableCollection];
    }
    return resultatSolvableCollection;
  }

  protected convertDateFromClient<T extends IResultatSolvable | NewResultatSolvable | PartialUpdateResultatSolvable>(
    resultatSolvable: T,
  ): RestOf<T> {
    return {
      ...resultatSolvable,
      moisSolvabiliteDate: resultatSolvable.moisSolvabiliteDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restResultatSolvable: RestResultatSolvable): IResultatSolvable {
    return {
      ...restResultatSolvable,
      moisSolvabiliteDate: restResultatSolvable.moisSolvabiliteDate ? dayjs(restResultatSolvable.moisSolvabiliteDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestResultatSolvable>): HttpResponse<IResultatSolvable> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestResultatSolvable[]>): HttpResponse<IResultatSolvable[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
