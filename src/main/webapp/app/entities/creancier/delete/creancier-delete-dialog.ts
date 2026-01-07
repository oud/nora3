import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import SharedModule from 'app/shared/shared.module';
import { ICreancier } from '../creancier.model';
import { CreancierService } from '../service/creancier.service';

@Component({
  templateUrl: './creancier-delete-dialog.html',
  imports: [SharedModule, FormsModule],
})
export class CreancierDeleteDialog {
  creancier?: ICreancier;

  protected creancierService = inject(CreancierService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.creancierService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
