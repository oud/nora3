import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICreancier, NewCreancier } from '../creancier.model';

export type PartialUpdateCreancier = Partial<ICreancier> & Pick<ICreancier, 'id'>;

type RestOf<T extends ICreancier | NewCreancier> = Omit<T, 'situationFamilialeDebutDate'> & {
  situationFamilialeDebutDate?: string | null;
};

export type RestCreancier = RestOf<ICreancier>;

export type NewRestCreancier = RestOf<NewCreancier>;

export type PartialUpdateRestCreancier = RestOf<PartialUpdateCreancier>;

export type EntityResponseType = HttpResponse<ICreancier>;
export type EntityArrayResponseType = HttpResponse<ICreancier[]>;

@Injectable({ providedIn: 'root' })
export class CreancierService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/creanciers');

  create(creancier: NewCreancier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(creancier);
    return this.http
      .post<RestCreancier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(creancier: ICreancier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(creancier);
    return this.http
      .put<RestCreancier>(`${this.resourceUrl}/${encodeURIComponent(this.getCreancierIdentifier(creancier))}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(creancier: PartialUpdateCreancier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(creancier);
    return this.http
      .patch<RestCreancier>(`${this.resourceUrl}/${encodeURIComponent(this.getCreancierIdentifier(creancier))}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCreancier>(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCreancier[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  getCreancierIdentifier(creancier: Pick<ICreancier, 'id'>): number {
    return creancier.id;
  }

  compareCreancier(o1: Pick<ICreancier, 'id'> | null, o2: Pick<ICreancier, 'id'> | null): boolean {
    return o1 && o2 ? this.getCreancierIdentifier(o1) === this.getCreancierIdentifier(o2) : o1 === o2;
  }

  addCreancierToCollectionIfMissing<Type extends Pick<ICreancier, 'id'>>(
    creancierCollection: Type[],
    ...creanciersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const creanciers: Type[] = creanciersToCheck.filter(isPresent);
    if (creanciers.length > 0) {
      const creancierCollectionIdentifiers = creancierCollection.map(creancierItem => this.getCreancierIdentifier(creancierItem));
      const creanciersToAdd = creanciers.filter(creancierItem => {
        const creancierIdentifier = this.getCreancierIdentifier(creancierItem);
        if (creancierCollectionIdentifiers.includes(creancierIdentifier)) {
          return false;
        }
        creancierCollectionIdentifiers.push(creancierIdentifier);
        return true;
      });
      return [...creanciersToAdd, ...creancierCollection];
    }
    return creancierCollection;
  }

  protected convertDateFromClient<T extends ICreancier | NewCreancier | PartialUpdateCreancier>(creancier: T): RestOf<T> {
    return {
      ...creancier,
      situationFamilialeDebutDate: creancier.situationFamilialeDebutDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restCreancier: RestCreancier): ICreancier {
    return {
      ...restCreancier,
      situationFamilialeDebutDate: restCreancier.situationFamilialeDebutDate ? dayjs(restCreancier.situationFamilialeDebutDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCreancier>): HttpResponse<ICreancier> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCreancier[]>): HttpResponse<ICreancier[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
