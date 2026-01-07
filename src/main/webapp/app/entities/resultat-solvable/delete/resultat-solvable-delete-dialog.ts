import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import SharedModule from 'app/shared/shared.module';
import { IResultatSolvable } from '../resultat-solvable.model';
import { ResultatSolvableService } from '../service/resultat-solvable.service';

@Component({
  templateUrl: './resultat-solvable-delete-dialog.html',
  imports: [SharedModule, FormsModule],
})
export class ResultatSolvableDeleteDialog {
  resultatSolvable?: IResultatSolvable;

  protected resultatSolvableService = inject(ResultatSolvableService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultatSolvableService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
