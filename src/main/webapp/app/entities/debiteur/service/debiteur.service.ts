import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IDebiteur, NewDebiteur } from '../debiteur.model';

export type PartialUpdateDebiteur = Partial<IDebiteur> & Pick<IDebiteur, 'id'>;

export type EntityResponseType = HttpResponse<IDebiteur>;
export type EntityArrayResponseType = HttpResponse<IDebiteur[]>;

@Injectable({ providedIn: 'root' })
export class DebiteurService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/debiteurs');

  create(debiteur: NewDebiteur): Observable<EntityResponseType> {
    return this.http.post<IDebiteur>(this.resourceUrl, debiteur, { observe: 'response' });
  }

  update(debiteur: IDebiteur): Observable<EntityResponseType> {
    return this.http.put<IDebiteur>(`${this.resourceUrl}/${encodeURIComponent(this.getDebiteurIdentifier(debiteur))}`, debiteur, {
      observe: 'response',
    });
  }

  partialUpdate(debiteur: PartialUpdateDebiteur): Observable<EntityResponseType> {
    return this.http.patch<IDebiteur>(`${this.resourceUrl}/${encodeURIComponent(this.getDebiteurIdentifier(debiteur))}`, debiteur, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDebiteur>(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDebiteur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  getDebiteurIdentifier(debiteur: Pick<IDebiteur, 'id'>): number {
    return debiteur.id;
  }

  compareDebiteur(o1: Pick<IDebiteur, 'id'> | null, o2: Pick<IDebiteur, 'id'> | null): boolean {
    return o1 && o2 ? this.getDebiteurIdentifier(o1) === this.getDebiteurIdentifier(o2) : o1 === o2;
  }

  addDebiteurToCollectionIfMissing<Type extends Pick<IDebiteur, 'id'>>(
    debiteurCollection: Type[],
    ...debiteursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const debiteurs: Type[] = debiteursToCheck.filter(isPresent);
    if (debiteurs.length > 0) {
      const debiteurCollectionIdentifiers = debiteurCollection.map(debiteurItem => this.getDebiteurIdentifier(debiteurItem));
      const debiteursToAdd = debiteurs.filter(debiteurItem => {
        const debiteurIdentifier = this.getDebiteurIdentifier(debiteurItem);
        if (debiteurCollectionIdentifiers.includes(debiteurIdentifier)) {
          return false;
        }
        debiteurCollectionIdentifiers.push(debiteurIdentifier);
        return true;
      });
      return [...debiteursToAdd, ...debiteurCollection];
    }
    return debiteurCollection;
  }
}
