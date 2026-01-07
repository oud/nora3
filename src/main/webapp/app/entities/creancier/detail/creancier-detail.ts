import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FormatMediumDatePipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
import { ICreancier } from '../creancier.model';

@Component({
  selector: 'jhi-creancier-detail',
  templateUrl: './creancier-detail.html',
  imports: [SharedModule, RouterLink, FormatMediumDatePipe],
})
export class CreancierDetail {
  creancier = input<ICreancier | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
