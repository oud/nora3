import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IDossier, NewDossier } from '../dossier.model';

export type PartialUpdateDossier = Partial<IDossier> & Pick<IDossier, 'id'>;

export type EntityResponseType = HttpResponse<IDossier>;
export type EntityArrayResponseType = HttpResponse<IDossier[]>;

@Injectable({ providedIn: 'root' })
export class DossierService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dossiers');

  create(dossier: NewDossier): Observable<EntityResponseType> {
    return this.http.post<IDossier>(this.resourceUrl, dossier, { observe: 'response' });
  }

  update(dossier: IDossier): Observable<EntityResponseType> {
    return this.http.put<IDossier>(`${this.resourceUrl}/${encodeURIComponent(this.getDossierIdentifier(dossier))}`, dossier, {
      observe: 'response',
    });
  }

  partialUpdate(dossier: PartialUpdateDossier): Observable<EntityResponseType> {
    return this.http.patch<IDossier>(`${this.resourceUrl}/${encodeURIComponent(this.getDossierIdentifier(dossier))}`, dossier, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDossier>(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDossier[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${encodeURIComponent(id)}`, { observe: 'response' });
  }

  getDossierIdentifier(dossier: Pick<IDossier, 'id'>): number {
    return dossier.id;
  }

  compareDossier(o1: Pick<IDossier, 'id'> | null, o2: Pick<IDossier, 'id'> | null): boolean {
    return o1 && o2 ? this.getDossierIdentifier(o1) === this.getDossierIdentifier(o2) : o1 === o2;
  }

  addDossierToCollectionIfMissing<Type extends Pick<IDossier, 'id'>>(
    dossierCollection: Type[],
    ...dossiersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dossiers: Type[] = dossiersToCheck.filter(isPresent);
    if (dossiers.length > 0) {
      const dossierCollectionIdentifiers = dossierCollection.map(dossierItem => this.getDossierIdentifier(dossierItem));
      const dossiersToAdd = dossiers.filter(dossierItem => {
        const dossierIdentifier = this.getDossierIdentifier(dossierItem);
        if (dossierCollectionIdentifiers.includes(dossierIdentifier)) {
          return false;
        }
        dossierCollectionIdentifiers.push(dossierIdentifier);
        return true;
      });
      return [...dossiersToAdd, ...dossierCollection];
    }
    return dossierCollection;
  }
}
