import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IStatut } from '../statut.model';

@Component({
  selector: 'jhi-statut-detail',
  templateUrl: './statut-detail.html',
  imports: [SharedModule, RouterLink],
})
export class StatutDetail {
  statut = input<IStatut | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
