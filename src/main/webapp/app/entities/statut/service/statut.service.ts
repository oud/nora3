import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IStatut, NewStatut } from '../statut.model';

export type PartialUpdateStatut = Partial<IStatut> & Pick<IStatut, 'id'>;

export type EntityResponseType = HttpResponse<IStatut>;
export type EntityArrayResponseType = HttpResponse<IStatut[]>;

@Injectable({ providedIn: 'root' })
export class StatutService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/statuts');

  create(statut: NewStatut): Observable<EntityResponseType> {
    return this.http.post<IStatut>(this.resourceUrl, statut, { observe: 'response' });
  }

  update(statut: IStatut): Observable<EntityResponseType> {
    return this.http.put<IStatut>(`${this.resourceUrl}/${encodeURIComponent(this.getStatutIdentifier(statut))}`, statut, {
      observe: 'response',
    });
  }

  partialUpdate(statut: PartialUpdateStatut): Observable<EntityResponseType> {
    return this.http.patch<IStatut>(`${this.resourceUrl}/${encodeURIComponent(this.getStatutIdentifier(statut))}`, statut, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatut>(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatut[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  getStatutIdentifier(statut: Pick<IStatut, 'id'>): number {
    return statut.id;
  }

  compareStatut(o1: Pick<IStatut, 'id'> | null, o2: Pick<IStatut, 'id'> | null): boolean {
    return o1 && o2 ? this.getStatutIdentifier(o1) === this.getStatutIdentifier(o2) : o1 === o2;
  }

  addStatutToCollectionIfMissing<Type extends Pick<IStatut, 'id'>>(
    statutCollection: Type[],
    ...statutsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const statuts: Type[] = statutsToCheck.filter(isPresent);
    if (statuts.length > 0) {
      const statutCollectionIdentifiers = statutCollection.map(statutItem => this.getStatutIdentifier(statutItem));
      const statutsToAdd = statuts.filter(statutItem => {
        const statutIdentifier = this.getStatutIdentifier(statutItem);
        if (statutCollectionIdentifiers.includes(statutIdentifier)) {
          return false;
        }
        statutCollectionIdentifiers.push(statutIdentifier);
        return true;
      });
      return [...statutsToAdd, ...statutCollection];
    }
    return statutCollection;
  }
}
