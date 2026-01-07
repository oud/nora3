import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import SharedModule from 'app/shared/shared.module';
import { IEnfant } from '../enfant.model';
import { EnfantService } from '../service/enfant.service';

import { EnfantFormGroup, EnfantFormService } from './enfant-form.service';

@Component({
  selector: 'jhi-enfant-update',
  templateUrl: './enfant-update.html',
  imports: [SharedModule, ReactiveFormsModule],
})
export class EnfantUpdate implements OnInit {
  isSaving = false;
  enfant: IEnfant | null = null;

  dossiersSharedCollection = signal<IDossier[]>([]);

  protected enfantService = inject(EnfantService);
  protected enfantFormService = inject(EnfantFormService);
  protected dossierService = inject(DossierService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EnfantFormGroup = this.enfantFormService.createEnfantFormGroup();

  compareDossier = (o1: IDossier | null, o2: IDossier | null): boolean => this.dossierService.compareDossier(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enfant }) => {
      this.enfant = enfant;
      if (enfant) {
        this.updateForm(enfant);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enfant = this.enfantFormService.getEnfant(this.editForm);
    if (enfant.id === null) {
      this.subscribeToSaveResponse(this.enfantService.create(enfant));
    } else {
      this.subscribeToSaveResponse(this.enfantService.update(enfant));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnfant>>): void {
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

  protected updateForm(enfant: IEnfant): void {
    this.enfant = enfant;
    this.enfantFormService.resetForm(this.editForm, enfant);

    this.dossiersSharedCollection.set(
      this.dossierService.addDossierToCollectionIfMissing<IDossier>(this.dossiersSharedCollection(), enfant.dossier),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dossierService
      .query()
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing<IDossier>(dossiers, this.enfant?.dossier)))
      .subscribe((dossiers: IDossier[]) => this.dossiersSharedCollection.set(dossiers));
  }
}
