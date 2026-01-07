import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FormatMediumDatePipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
import { ISituation } from '../situation.model';

@Component({
  selector: 'jhi-situation-detail',
  templateUrl: './situation-detail.html',
  imports: [SharedModule, RouterLink, FormatMediumDatePipe],
})
export class SituationDetail {
  situation = input<ISituation | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
