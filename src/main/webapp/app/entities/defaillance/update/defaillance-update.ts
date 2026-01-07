import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import SharedModule from 'app/shared/shared.module';
import { IDefaillance } from '../defaillance.model';
import { DefaillanceService } from '../service/defaillance.service';

import { DefaillanceFormGroup, DefaillanceFormService } from './defaillance-form.service';

@Component({
  selector: 'jhi-defaillance-update',
  templateUrl: './defaillance-update.html',
  imports: [SharedModule, ReactiveFormsModule],
})
export class DefaillanceUpdate implements OnInit {
  isSaving = false;
  defaillance: IDefaillance | null = null;

  dossiersSharedCollection = signal<IDossier[]>([]);

  protected defaillanceService = inject(DefaillanceService);
  protected defaillanceFormService = inject(DefaillanceFormService);
  protected dossierService = inject(DossierService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DefaillanceFormGroup = this.defaillanceFormService.createDefaillanceFormGroup();

  compareDossier = (o1: IDossier | null, o2: IDossier | null): boolean => this.dossierService.compareDossier(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ defaillance }) => {
      this.defaillance = defaillance;
      if (defaillance) {
        this.updateForm(defaillance);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving = true;
    const defaillance = this.defaillanceFormService.getDefaillance(this.editForm);
    if (defaillance.id === null) {
      this.subscribeToSaveResponse(this.defaillanceService.create(defaillance));
    } else {
      this.subscribeToSaveResponse(this.defaillanceService.update(defaillance));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDefaillance>>): void {
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

  protected updateForm(defaillance: IDefaillance): void {
    this.defaillance = defaillance;
    this.defaillanceFormService.resetForm(this.editForm, defaillance);

    this.dossiersSharedCollection.set(
      this.dossierService.addDossierToCollectionIfMissing<IDossier>(this.dossiersSharedCollection(), defaillance.dossier),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dossierService
      .query()
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(
        map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing<IDossier>(dossiers, this.defaillance?.dossier)),
      )
      .subscribe((dossiers: IDossier[]) => this.dossiersSharedCollection.set(dossiers));
  }
}
