import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICreancier } from 'app/entities/creancier/creancier.model';
import { CreancierService } from 'app/entities/creancier/service/creancier.service';
import { IDebiteur } from 'app/entities/debiteur/debiteur.model';
import { DebiteurService } from 'app/entities/debiteur/service/debiteur.service';
import SharedModule from 'app/shared/shared.module';
import { IDossier } from '../dossier.model';
import { DossierService } from '../service/dossier.service';

import { DossierFormGroup, DossierFormService } from './dossier-form.service';

@Component({
  selector: 'jhi-dossier-update',
  templateUrl: './dossier-update.html',
  imports: [SharedModule, ReactiveFormsModule],
})
export class DossierUpdate implements OnInit {
  isSaving = false;
  dossier: IDossier | null = null;

  debiteursCollection = signal<IDebiteur[]>([]);
  creanciersCollection = signal<ICreancier[]>([]);

  protected dossierService = inject(DossierService);
  protected dossierFormService = inject(DossierFormService);
  protected debiteurService = inject(DebiteurService);
  protected creancierService = inject(CreancierService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DossierFormGroup = this.dossierFormService.createDossierFormGroup();

  compareDebiteur = (o1: IDebiteur | null, o2: IDebiteur | null): boolean => this.debiteurService.compareDebiteur(o1, o2);

  compareCreancier = (o1: ICreancier | null, o2: ICreancier | null): boolean => this.creancierService.compareCreancier(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dossier }) => {
      this.dossier = dossier;
      if (dossier) {
        this.updateForm(dossier);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dossier = this.dossierFormService.getDossier(this.editForm);
    if (dossier.id === null) {
      this.subscribeToSaveResponse(this.dossierService.create(dossier));
    } else {
      this.subscribeToSaveResponse(this.dossierService.update(dossier));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDossier>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(dossier: IDossier): void {
    this.dossier = dossier;
    this.dossierFormService.resetForm(this.editForm, dossier);

    this.debiteursCollection.set(
      this.debiteurService.addDebiteurToCollectionIfMissing<IDebiteur>(this.debiteursCollection(), dossier.debiteur),
    );
    this.creanciersCollection.set(
      this.creancierService.addCreancierToCollectionIfMissing<ICreancier>(this.creanciersCollection(), dossier.creancier),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.debiteurService
      .query({ filter: 'dossier-is-null' })
      .pipe(map((res: HttpResponse<IDebiteur[]>) => res.body ?? []))
      .pipe(
        map((debiteurs: IDebiteur[]) =>
          this.debiteurService.addDebiteurToCollectionIfMissing<IDebiteur>(debiteurs, this.dossier?.debiteur),
        ),
      )
      .subscribe((debiteurs: IDebiteur[]) => this.debiteursCollection.set(debiteurs));

    this.creancierService
      .query({ filter: 'dossier-is-null' })
      .pipe(map((res: HttpResponse<ICreancier[]>) => res.body ?? []))
      .pipe(
        map((creanciers: ICreancier[]) =>
          this.creancierService.addCreancierToCollectionIfMissing<ICreancier>(creanciers, this.dossier?.creancier),
        ),
      )
      .subscribe((creanciers: ICreancier[]) => this.creanciersCollection.set(creanciers));
  }
}
