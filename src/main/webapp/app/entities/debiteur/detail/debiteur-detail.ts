import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IDebiteur } from '../debiteur.model';

@Component({
  selector: 'jhi-debiteur-detail',
  templateUrl: './debiteur-detail.html',
  imports: [SharedModule, RouterLink],
})
export class DebiteurDetail {
  debiteur = input<IDebiteur | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
