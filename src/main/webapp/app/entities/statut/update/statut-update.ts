import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import SharedModule from 'app/shared/shared.module';
import { StatutService } from '../service/statut.service';
import { IStatut } from '../statut.model';

import { StatutFormGroup, StatutFormService } from './statut-form.service';

@Component({
  selector: 'jhi-statut-update',
  templateUrl: './statut-update.html',
  imports: [SharedModule, ReactiveFormsModule],
})
export class StatutUpdate implements OnInit {
  isSaving = false;
  statut: IStatut | null = null;

  dossiersSharedCollection = signal<IDossier[]>([]);

  protected statutService = inject(StatutService);
  protected statutFormService = inject(StatutFormService);
  protected dossierService = inject(DossierService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: StatutFormGroup = this.statutFormService.createStatutFormGroup();

  compareDossier = (o1: IDossier | null, o2: IDossier | null): boolean => this.dossierService.compareDossier(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statut }) => {
      this.statut = statut;
      if (statut) {
        this.updateForm(statut);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statut = this.statutFormService.getStatut(this.editForm);
    if (statut.id === null) {
      this.subscribeToSaveResponse(this.statutService.create(statut));
    } else {
      this.subscribeToSaveResponse(this.statutService.update(statut));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatut>>): void {
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

  protected updateForm(statut: IStatut): void {
    this.statut = statut;
    this.statutFormService.resetForm(this.editForm, statut);

    this.dossiersSharedCollection.set(
      this.dossierService.addDossierToCollectionIfMissing<IDossier>(this.dossiersSharedCollection(), statut.dossier),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dossierService
      .query()
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing<IDossier>(dossiers, this.statut?.dossier)))
      .subscribe((dossiers: IDossier[]) => this.dossiersSharedCollection.set(dossiers));
  }
}
