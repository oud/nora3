import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import SharedModule from 'app/shared/shared.module';
import { IDossier } from '../dossier.model';
import { DossierService } from '../service/dossier.service';

@Component({
  templateUrl: './dossier-delete-dialog.html',
  imports: [SharedModule, FormsModule],
})
export class DossierDeleteDialog {
  dossier?: IDossier;

  protected dossierService = inject(DossierService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dossierService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
