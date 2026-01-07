import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IRechercheSolvable, NewRechercheSolvable } from '../recherche-solvable.model';

export type PartialUpdateRechercheSolvable = Partial<IRechercheSolvable> & Pick<IRechercheSolvable, 'id'>;

export type EntityResponseType = HttpResponse<IRechercheSolvable>;
export type EntityArrayResponseType = HttpResponse<IRechercheSolvable[]>;

@Injectable({ providedIn: 'root' })
export class RechercheSolvableService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/recherche-solvables');

  create(rechercheSolvable: NewRechercheSolvable): Observable<EntityResponseType> {
    return this.http.post<IRechercheSolvable>(this.resourceUrl, rechercheSolvable, { observe: 'response' });
  }

  update(rechercheSolvable: IRechercheSolvable): Observable<EntityResponseType> {
    return this.http.put<IRechercheSolvable>(
      `${this.resourceUrl}/${encodeURIComponent(this.getRechercheSolvableIdentifier(rechercheSolvable))}`,
      rechercheSolvable,
      { observe: 'response' },
    );
  }

  partialUpdate(rechercheSolvable: PartialUpdateRechercheSolvable): Observable<EntityResponseType> {
    return this.http.patch<IRechercheSolvable>(
      `${this.resourceUrl}/${encodeURIComponent(this.getRechercheSolvableIdentifier(rechercheSolvable))}`,
      rechercheSolvable,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRechercheSolvable>(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRechercheSolvable[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  getRechercheSolvableIdentifier(rechercheSolvable: Pick<IRechercheSolvable, 'id'>): number {
    return rechercheSolvable.id;
  }

  compareRechercheSolvable(o1: Pick<IRechercheSolvable, 'id'> | null, o2: Pick<IRechercheSolvable, 'id'> | null): boolean {
    return o1 && o2 ? this.getRechercheSolvableIdentifier(o1) === this.getRechercheSolvableIdentifier(o2) : o1 === o2;
  }

  addRechercheSolvableToCollectionIfMissing<Type extends Pick<IRechercheSolvable, 'id'>>(
    rechercheSolvableCollection: Type[],
    ...rechercheSolvablesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const rechercheSolvables: Type[] = rechercheSolvablesToCheck.filter(isPresent);
    if (rechercheSolvables.length > 0) {
      const rechercheSolvableCollectionIdentifiers = rechercheSolvableCollection.map(rechercheSolvableItem =>
        this.getRechercheSolvableIdentifier(rechercheSolvableItem),
      );
      const rechercheSolvablesToAdd = rechercheSolvables.filter(rechercheSolvableItem => {
        const rechercheSolvableIdentifier = this.getRechercheSolvableIdentifier(rechercheSolvableItem);
        if (rechercheSolvableCollectionIdentifiers.includes(rechercheSolvableIdentifier)) {
          return false;
        }
        rechercheSolvableCollectionIdentifiers.push(rechercheSolvableIdentifier);
        return true;
      });
      return [...rechercheSolvablesToAdd, ...rechercheSolvableCollection];
    }
    return rechercheSolvableCollection;
  }
}
