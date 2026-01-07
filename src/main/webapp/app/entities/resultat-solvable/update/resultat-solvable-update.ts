import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRechercheSolvable } from 'app/entities/recherche-solvable/recherche-solvable.model';
import { RechercheSolvableService } from 'app/entities/recherche-solvable/service/recherche-solvable.service';
import SharedModule from 'app/shared/shared.module';
import { IResultatSolvable } from '../resultat-solvable.model';
import { ResultatSolvableService } from '../service/resultat-solvable.service';

import { ResultatSolvableFormGroup, ResultatSolvableFormService } from './resultat-solvable-form.service';

@Component({
  selector: 'jhi-resultat-solvable-update',
  templateUrl: './resultat-solvable-update.html',
  imports: [SharedModule, ReactiveFormsModule],
})
export class ResultatSolvableUpdate implements OnInit {
  isSaving = false;
  resultatSolvable: IResultatSolvable | null = null;

  rechercheSolvablesSharedCollection = signal<IRechercheSolvable[]>([]);

  protected resultatSolvableService = inject(ResultatSolvableService);
  protected resultatSolvableFormService = inject(ResultatSolvableFormService);
  protected rechercheSolvableService = inject(RechercheSolvableService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ResultatSolvableFormGroup = this.resultatSolvableFormService.createResultatSolvableFormGroup();

  compareRechercheSolvable = (o1: IRechercheSolvable | null, o2: IRechercheSolvable | null): boolean =>
    this.rechercheSolvableService.compareRechercheSolvable(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultatSolvable }) => {
      this.resultatSolvable = resultatSolvable;
      if (resultatSolvable) {
        this.updateForm(resultatSolvable);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultatSolvable = this.resultatSolvableFormService.getResultatSolvable(this.editForm);
    if (resultatSolvable.id === null) {
      this.subscribeToSaveResponse(this.resultatSolvableService.create(resultatSolvable));
    } else {
      this.subscribeToSaveResponse(this.resultatSolvableService.update(resultatSolvable));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultatSolvable>>): void {
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

  protected updateForm(resultatSolvable: IResultatSolvable): void {
    this.resultatSolvable = resultatSolvable;
    this.resultatSolvableFormService.resetForm(this.editForm, resultatSolvable);

    this.rechercheSolvablesSharedCollection.set(
      this.rechercheSolvableService.addRechercheSolvableToCollectionIfMissing<IRechercheSolvable>(
        this.rechercheSolvablesSharedCollection(),
        resultatSolvable.rechercheSolvable,
      ),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.rechercheSolvableService
      .query()
      .pipe(map((res: HttpResponse<IRechercheSolvable[]>) => res.body ?? []))
      .pipe(
        map((rechercheSolvables: IRechercheSolvable[]) =>
          this.rechercheSolvableService.addRechercheSolvableToCollectionIfMissing<IRechercheSolvable>(
            rechercheSolvables,
            this.resultatSolvable?.rechercheSolvable,
          ),
        ),
      )
      .subscribe((rechercheSolvables: IRechercheSolvable[]) => this.rechercheSolvablesSharedCollection.set(rechercheSolvables));
  }
}
