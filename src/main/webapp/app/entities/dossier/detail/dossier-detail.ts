import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IDossier } from '../dossier.model';

@Component({
  selector: 'jhi-dossier-detail',
  templateUrl: './dossier-detail.html',
  imports: [SharedModule, RouterLink],
})
export class DossierDetail {
  dossier = input<IDossier | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
