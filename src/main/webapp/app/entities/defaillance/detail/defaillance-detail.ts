import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FormatMediumDatePipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
import { IDefaillance } from '../defaillance.model';

@Component({
  selector: 'jhi-defaillance-detail',
  templateUrl: './defaillance-detail.html',
  imports: [SharedModule, RouterLink, FormatMediumDatePipe],
})
export class DefaillanceDetail {
  defaillance = input<IDefaillance | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
