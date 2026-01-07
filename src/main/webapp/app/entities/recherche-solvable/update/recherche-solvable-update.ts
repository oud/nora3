import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDebiteur } from 'app/entities/debiteur/debiteur.model';
import { DebiteurService } from 'app/entities/debiteur/service/debiteur.service';
import SharedModule from 'app/shared/shared.module';
import { IRechercheSolvable } from '../recherche-solvable.model';
import { RechercheSolvableService } from '../service/recherche-solvable.service';

import { RechercheSolvableFormGroup, RechercheSolvableFormService } from './recherche-solvable-form.service';

@Component({
  selector: 'jhi-recherche-solvable-update',
  templateUrl: './recherche-solvable-update.html',
  imports: [SharedModule, ReactiveFormsModule],
})
export class RechercheSolvableUpdate implements OnInit {
  isSaving = false;
  rechercheSolvable: IRechercheSolvable | null = null;

  debiteursSharedCollection = signal<IDebiteur[]>([]);

  protected rechercheSolvableService = inject(RechercheSolvableService);
  protected rechercheSolvableFormService = inject(RechercheSolvableFormService);
  protected debiteurService = inject(DebiteurService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RechercheSolvableFormGroup = this.rechercheSolvableFormService.createRechercheSolvableFormGroup();

  compareDebiteur = (o1: IDebiteur | null, o2: IDebiteur | null): boolean => this.debiteurService.compareDebiteur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rechercheSolvable }) => {
      this.rechercheSolvable = rechercheSolvable;
      if (rechercheSolvable) {
        this.updateForm(rechercheSolvable);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rechercheSolvable = this.rechercheSolvableFormService.getRechercheSolvable(this.editForm);
    if (rechercheSolvable.id === null) {
      this.subscribeToSaveResponse(this.rechercheSolvableService.create(rechercheSolvable));
    } else {
      this.subscribeToSaveResponse(this.rechercheSolvableService.update(rechercheSolvable));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRechercheSolvable>>): void {
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

  protected updateForm(rechercheSolvable: IRechercheSolvable): void {
    this.rechercheSolvable = rechercheSolvable;
    this.rechercheSolvableFormService.resetForm(this.editForm, rechercheSolvable);

    this.debiteursSharedCollection.set(
      this.debiteurService.addDebiteurToCollectionIfMissing<IDebiteur>(this.debiteursSharedCollection(), rechercheSolvable.debiteur),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.debiteurService
      .query()
      .pipe(map((res: HttpResponse<IDebiteur[]>) => res.body ?? []))
      .pipe(
        map((debiteurs: IDebiteur[]) =>
          this.debiteurService.addDebiteurToCollectionIfMissing<IDebiteur>(debiteurs, this.rechercheSolvable?.debiteur),
        ),
      )
      .subscribe((debiteurs: IDebiteur[]) => this.debiteursSharedCollection.set(debiteurs));
  }
}
