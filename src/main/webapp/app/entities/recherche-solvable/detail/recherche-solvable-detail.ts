import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IRechercheSolvable } from '../recherche-solvable.model';

@Component({
  selector: 'jhi-recherche-solvable-detail',
  templateUrl: './recherche-solvable-detail.html',
  imports: [SharedModule, RouterLink],
})
export class RechercheSolvableDetail {
  rechercheSolvable = input<IRechercheSolvable | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
