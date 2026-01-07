import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IDemarche, NewDemarche } from '../demarche.model';

export type PartialUpdateDemarche = Partial<IDemarche> & Pick<IDemarche, 'id'>;

type RestOf<T extends IDemarche | NewDemarche> = Omit<T, 'demarcheDate'> & {
  demarcheDate?: string | null;
};

export type RestDemarche = RestOf<IDemarche>;

export type NewRestDemarche = RestOf<NewDemarche>;

export type PartialUpdateRestDemarche = RestOf<PartialUpdateDemarche>;

export type EntityResponseType = HttpResponse<IDemarche>;
export type EntityArrayResponseType = HttpResponse<IDemarche[]>;

@Injectable({ providedIn: 'root' })
export class DemarcheService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demarches');

  create(demarche: NewDemarche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demarche);
    return this.http
      .post<RestDemarche>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(demarche: IDemarche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demarche);
    return this.http
      .put<RestDemarche>(`${this.resourceUrl}/${encodeURIComponent(this.getDemarcheIdentifier(demarche))}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(demarche: PartialUpdateDemarche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demarche);
    return this.http
      .patch<RestDemarche>(`${this.resourceUrl}/${encodeURIComponent(this.getDemarcheIdentifier(demarche))}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDemarche>(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDemarche[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  getDemarcheIdentifier(demarche: Pick<IDemarche, 'id'>): number {
    return demarche.id;
  }

  compareDemarche(o1: Pick<IDemarche, 'id'> | null, o2: Pick<IDemarche, 'id'> | null): boolean {
    return o1 && o2 ? this.getDemarcheIdentifier(o1) === this.getDemarcheIdentifier(o2) : o1 === o2;
  }

  addDemarcheToCollectionIfMissing<Type extends Pick<IDemarche, 'id'>>(
    demarcheCollection: Type[],
    ...demarchesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const demarches: Type[] = demarchesToCheck.filter(isPresent);
    if (demarches.length > 0) {
      const demarcheCollectionIdentifiers = demarcheCollection.map(demarcheItem => this.getDemarcheIdentifier(demarcheItem));
      const demarchesToAdd = demarches.filter(demarcheItem => {
        const demarcheIdentifier = this.getDemarcheIdentifier(demarcheItem);
        if (demarcheCollectionIdentifiers.includes(demarcheIdentifier)) {
          return false;
        }
        demarcheCollectionIdentifiers.push(demarcheIdentifier);
        return true;
      });
      return [...demarchesToAdd, ...demarcheCollection];
    }
    return demarcheCollection;
  }

  protected convertDateFromClient<T extends IDemarche | NewDemarche | PartialUpdateDemarche>(demarche: T): RestOf<T> {
    return {
      ...demarche,
      demarcheDate: demarche.demarcheDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restDemarche: RestDemarche): IDemarche {
    return {
      ...restDemarche,
      demarcheDate: restDemarche.demarcheDate ? dayjs(restDemarche.demarcheDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDemarche>): HttpResponse<IDemarche> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDemarche[]>): HttpResponse<IDemarche[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
