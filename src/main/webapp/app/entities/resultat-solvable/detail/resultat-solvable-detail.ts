import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FormatMediumDatePipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
import { IResultatSolvable } from '../resultat-solvable.model';

@Component({
  selector: 'jhi-resultat-solvable-detail',
  templateUrl: './resultat-solvable-detail.html',
  imports: [SharedModule, RouterLink, FormatMediumDatePipe],
})
export class ResultatSolvableDetail {
  resultatSolvable = input<IResultatSolvable | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
