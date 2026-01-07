import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import SharedModule from 'app/shared/shared.module';
import { IDebiteur } from '../debiteur.model';
import { DebiteurService } from '../service/debiteur.service';

@Component({
  templateUrl: './debiteur-delete-dialog.html',
  imports: [SharedModule, FormsModule],
})
export class DebiteurDeleteDialog {
  debiteur?: IDebiteur;

  protected debiteurService = inject(DebiteurService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.debiteurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
