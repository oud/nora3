import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import SharedModule from 'app/shared/shared.module';
import { IDemarche } from '../demarche.model';
import { DemarcheService } from '../service/demarche.service';

@Component({
  templateUrl: './demarche-delete-dialog.html',
  imports: [SharedModule, FormsModule],
})
export class DemarcheDeleteDialog {
  demarche?: IDemarche;

  protected demarcheService = inject(DemarcheService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demarcheService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
