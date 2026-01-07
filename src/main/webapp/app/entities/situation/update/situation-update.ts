import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDebiteur } from 'app/entities/debiteur/debiteur.model';
import { DebiteurService } from 'app/entities/debiteur/service/debiteur.service';
import SharedModule from 'app/shared/shared.module';
import { SituationService } from '../service/situation.service';
import { ISituation } from '../situation.model';

import { SituationFormGroup, SituationFormService } from './situation-form.service';

@Component({
  selector: 'jhi-situation-update',
  templateUrl: './situation-update.html',
  imports: [SharedModule, ReactiveFormsModule],
})
export class SituationUpdate implements OnInit {
  isSaving = false;
  situation: ISituation | null = null;

  debiteursSharedCollection = signal<IDebiteur[]>([]);

  protected situationService = inject(SituationService);
  protected situationFormService = inject(SituationFormService);
  protected debiteurService = inject(DebiteurService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SituationFormGroup = this.situationFormService.createSituationFormGroup();

  compareDebiteur = (o1: IDebiteur | null, o2: IDebiteur | null): boolean => this.debiteurService.compareDebiteur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ situation }) => {
      this.situation = situation;
      if (situation) {
        this.updateForm(situation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving = true;
    const situation = this.situationFormService.getSituation(this.editForm);
    if (situation.id === null) {
      this.subscribeToSaveResponse(this.situationService.create(situation));
    } else {
      this.subscribeToSaveResponse(this.situationService.update(situation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISituation>>): void {
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

  protected updateForm(situation: ISituation): void {
    this.situation = situation;
    this.situationFormService.resetForm(this.editForm, situation);

    this.debiteursSharedCollection.set(
      this.debiteurService.addDebiteurToCollectionIfMissing<IDebiteur>(this.debiteursSharedCollection(), situation.debiteur),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.debiteurService
      .query()
      .pipe(map((res: HttpResponse<IDebiteur[]>) => res.body ?? []))
      .pipe(
        map((debiteurs: IDebiteur[]) =>
          this.debiteurService.addDebiteurToCollectionIfMissing<IDebiteur>(debiteurs, this.situation?.debiteur),
        ),
      )
      .subscribe((debiteurs: IDebiteur[]) => this.debiteursSharedCollection.set(debiteurs));
  }
}
