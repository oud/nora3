import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { ResultatSolvableDetail } from './resultat-solvable-detail';

describe('ResultatSolvable Management Detail Component', () => {
  let comp: ResultatSolvableDetail;
  let fixture: ComponentFixture<ResultatSolvableDetail>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./resultat-solvable-detail').then(m => m.ResultatSolvableDetail),
              resolve: { resultatSolvable: () => of({ id: 3195 }) },
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
    fixture = TestBed.createComponent(ResultatSolvableDetail);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load resultatSolvable on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ResultatSolvableDetail);

      // THEN
      expect(instance.resultatSolvable()).toEqual(expect.objectContaining({ id: 3195 }));
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
