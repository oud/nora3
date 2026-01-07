import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { ICreancier } from '../creancier.model';
import { CreancierService } from '../service/creancier.service';

import { CreancierFormGroup, CreancierFormService } from './creancier-form.service';

@Component({
  selector: 'jhi-creancier-update',
  templateUrl: './creancier-update.html',
  imports: [SharedModule, ReactiveFormsModule],
})
export class CreancierUpdate implements OnInit {
  isSaving = false;
  creancier: ICreancier | null = null;

  protected creancierService = inject(CreancierService);
  protected creancierFormService = inject(CreancierFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CreancierFormGroup = this.creancierFormService.createCreancierFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ creancier }) => {
      this.creancier = creancier;
      if (creancier) {
        this.updateForm(creancier);
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving = true;
    const creancier = this.creancierFormService.getCreancier(this.editForm);
    if (creancier.id === null) {
      this.subscribeToSaveResponse(this.creancierService.create(creancier));
    } else {
      this.subscribeToSaveResponse(this.creancierService.update(creancier));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreancier>>): void {
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

  protected updateForm(creancier: ICreancier): void {
    this.creancier = creancier;
    this.creancierFormService.resetForm(this.editForm, creancier);
  }
}
