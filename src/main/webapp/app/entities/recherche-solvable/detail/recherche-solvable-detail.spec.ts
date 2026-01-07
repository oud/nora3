import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { RechercheSolvableDetail } from './recherche-solvable-detail';

describe('RechercheSolvable Management Detail Component', () => {
  let comp: RechercheSolvableDetail;
  let fixture: ComponentFixture<RechercheSolvableDetail>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./recherche-solvable-detail').then(m => m.RechercheSolvableDetail),
              resolve: { rechercheSolvable: () => of({ id: 7543 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    });
    const library = TestBed.inject(FaIconLibrary);
    library.addIcons(faArrowLeft);
    library.addIcons(faPencilAlt);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RechercheSolvableDetail);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load rechercheSolvable on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RechercheSolvableDetail);

      // THEN
      expect(instance.rechercheSolvable()).toEqual(expect.objectContaining({ id: 7543 }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(globalThis.history.back).toHaveBeenCalled();
    });
  });
});
