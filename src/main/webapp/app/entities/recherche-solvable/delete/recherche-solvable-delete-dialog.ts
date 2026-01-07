import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import SharedModule from 'app/shared/shared.module';
import { IRechercheSolvable } from '../recherche-solvable.model';
import { RechercheSolvableService } from '../service/recherche-solvable.service';

@Component({
  templateUrl: './recherche-solvable-delete-dialog.html',
  imports: [SharedModule, FormsModule],
})
export class RechercheSolvableDeleteDialog {
  rechercheSolvable?: IRechercheSolvable;

  protected rechercheSolvableService = inject(RechercheSolvableService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rechercheSolvableService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
