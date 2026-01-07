import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import SharedModule from 'app/shared/shared.module';
import { IDemarche } from '../demarche.model';
import { DemarcheService } from '../service/demarche.service';

import { DemarcheFormGroup, DemarcheFormService } from './demarche-form.service';

@Component({
  selector: 'jhi-demarche-update',
  templateUrl: './demarche-update.html',
  imports: [SharedModule, ReactiveFormsModule],
})
export class DemarcheUpdate implements OnInit {
  isSaving = false;
  demarche: IDemarche | null = null;

  dossiersSharedCollection = signal<IDossier[]>([]);

  protected demarcheService = inject(DemarcheService);
  protected demarcheFormService = inject(DemarcheFormService);
  protected dossierService = inject(DossierService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DemarcheFormGroup = this.demarcheFormService.createDemarcheFormGroup();

  compareDossier = (o1: IDossier | null, o2: IDossier | null): boolean => this.dossierService.compareDossier(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demarche }) => {
      this.demarche = demarche;
      if (demarche) {
        this.updateForm(demarche);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demarche = this.demarcheFormService.getDemarche(this.editForm);
    if (demarche.id === null) {
      this.subscribeToSaveResponse(this.demarcheService.create(demarche));
    } else {
      this.subscribeToSaveResponse(this.demarcheService.update(demarche));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemarche>>): void {
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

  protected updateForm(demarche: IDemarche): void {
    this.demarche = demarche;
    this.demarcheFormService.resetForm(this.editForm, demarche);

    this.dossiersSharedCollection.set(
      this.dossierService.addDossierToCollectionIfMissing<IDossier>(this.dossiersSharedCollection(), demarche.dossier),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dossierService
      .query()
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing<IDossier>(dossiers, this.demarche?.dossier)))
      .subscribe((dossiers: IDossier[]) => this.dossiersSharedCollection.set(dossiers));
  }
}
