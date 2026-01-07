import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import SharedModule from 'app/shared/shared.module';
import { SituationService } from '../service/situation.service';
import { ISituation } from '../situation.model';

@Component({
  templateUrl: './situation-delete-dialog.html',
  imports: [SharedModule, FormsModule],
})
export class SituationDeleteDialog {
  situation?: ISituation;

  protected situationService = inject(SituationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.situationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
