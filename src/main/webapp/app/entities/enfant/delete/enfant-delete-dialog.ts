import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import SharedModule from 'app/shared/shared.module';
import { IEnfant } from '../enfant.model';
import { EnfantService } from '../service/enfant.service';

@Component({
  templateUrl: './enfant-delete-dialog.html',
  imports: [SharedModule, FormsModule],
})
export class EnfantDeleteDialog {
  enfant?: IEnfant;

  protected enfantService = inject(EnfantService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enfantService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
