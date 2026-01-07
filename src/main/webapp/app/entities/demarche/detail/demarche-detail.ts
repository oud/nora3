import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FormatMediumDatePipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
import { IDemarche } from '../demarche.model';

@Component({
  selector: 'jhi-demarche-detail',
  templateUrl: './demarche-detail.html',
  imports: [SharedModule, RouterLink, FormatMediumDatePipe],
})
export class DemarcheDetail {
  demarche = input<IDemarche | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
