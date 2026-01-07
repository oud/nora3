import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { IDebiteur } from '../debiteur.model';
import { DebiteurService } from '../service/debiteur.service';

import { DebiteurFormGroup, DebiteurFormService } from './debiteur-form.service';

@Component({
  selector: 'jhi-debiteur-update',
  templateUrl: './debiteur-update.html',
  imports: [SharedModule, ReactiveFormsModule],
})
export class DebiteurUpdate implements OnInit {
  isSaving = false;
  debiteur: IDebiteur | null = null;

  protected debiteurService = inject(DebiteurService);
  protected debiteurFormService = inject(DebiteurFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DebiteurFormGroup = this.debiteurFormService.createDebiteurFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ debiteur }) => {
      this.debiteur = debiteur;
      if (debiteur) {
        this.updateForm(debiteur);
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving = true;
    const debiteur = this.debiteurFormService.getDebiteur(this.editForm);
    if (debiteur.id === null) {
      this.subscribeToSaveResponse(this.debiteurService.create(debiteur));
    } else {
      this.subscribeToSaveResponse(this.debiteurService.update(debiteur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDebiteur>>): void {
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

  protected updateForm(debiteur: IDebiteur): void {
    this.debiteur = debiteur;
    this.debiteurFormService.resetForm(this.editForm, debiteur);
  }
}
