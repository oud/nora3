import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IEnfant, NewEnfant } from '../enfant.model';

export type PartialUpdateEnfant = Partial<IEnfant> & Pick<IEnfant, 'id'>;

export type EntityResponseType = HttpResponse<IEnfant>;
export type EntityArrayResponseType = HttpResponse<IEnfant[]>;

@Injectable({ providedIn: 'root' })
export class EnfantService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/enfants');

  create(enfant: NewEnfant): Observable<EntityResponseType> {
    return this.http.post<IEnfant>(this.resourceUrl, enfant, { observe: 'response' });
  }

  update(enfant: IEnfant): Observable<EntityResponseType> {
    return this.http.put<IEnfant>(`${this.resourceUrl}/${encodeURIComponent(this.getEnfantIdentifier(enfant))}`, enfant, {
      observe: 'response',
    });
  }

  partialUpdate(enfant: PartialUpdateEnfant): Observable<EntityResponseType> {
    return this.http.patch<IEnfant>(`${this.resourceUrl}/${encodeURIComponent(this.getEnfantIdentifier(enfant))}`, enfant, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnfant>(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnfant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  getEnfantIdentifier(enfant: Pick<IEnfant, 'id'>): number {
    return enfant.id;
  }

  compareEnfant(o1: Pick<IEnfant, 'id'> | null, o2: Pick<IEnfant, 'id'> | null): boolean {
    return o1 && o2 ? this.getEnfantIdentifier(o1) === this.getEnfantIdentifier(o2) : o1 === o2;
  }

  addEnfantToCollectionIfMissing<Type extends Pick<IEnfant, 'id'>>(
    enfantCollection: Type[],
    ...enfantsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const enfants: Type[] = enfantsToCheck.filter(isPresent);
    if (enfants.length > 0) {
      const enfantCollectionIdentifiers = enfantCollection.map(enfantItem => this.getEnfantIdentifier(enfantItem));
      const enfantsToAdd = enfants.filter(enfantItem => {
        const enfantIdentifier = this.getEnfantIdentifier(enfantItem);
        if (enfantCollectionIdentifiers.includes(enfantIdentifier)) {
          return false;
        }
        enfantCollectionIdentifiers.push(enfantIdentifier);
        return true;
      });
      return [...enfantsToAdd, ...enfantCollection];
    }
    return enfantCollection;
  }
}
