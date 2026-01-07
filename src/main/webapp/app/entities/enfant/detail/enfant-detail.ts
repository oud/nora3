import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IEnfant } from '../enfant.model';

@Component({
  selector: 'jhi-enfant-detail',
  templateUrl: './enfant-detail.html',
  imports: [SharedModule, RouterLink],
})
export class EnfantDetail {
  enfant = input<IEnfant | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
