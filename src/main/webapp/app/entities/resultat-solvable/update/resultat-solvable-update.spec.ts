import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IRechercheSolvable } from 'app/entities/recherche-solvable/recherche-solvable.model';
import { RechercheSolvableService } from 'app/entities/recherche-solvable/service/recherche-solvable.service';
import { IResultatSolvable } from '../resultat-solvable.model';
import { ResultatSolvableService } from '../service/resultat-solvable.service';

import { ResultatSolvableFormService } from './resultat-solvable-form.service';
import { ResultatSolvableUpdate } from './resultat-solvable-update';

describe('ResultatSolvable Management Update Component', () => {
  let comp: ResultatSolvableUpdate;
  let fixture: ComponentFixture<ResultatSolvableUpdate>;
  let activatedRoute: ActivatedRoute;
  let resultatSolvableFormService: ResultatSolvableFormService;
  let resultatSolvableService: ResultatSolvableService;
  let rechercheSolvableService: RechercheSolvableService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    });

    fixture = TestBed.createComponent(ResultatSolvableUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resultatSolvableFormService = TestBed.inject(ResultatSolvableFormService);
    resultatSolvableService = TestBed.inject(ResultatSolvableService);
    rechercheSolvableService = TestBed.inject(RechercheSolvableService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call RechercheSolvable query and add missing value', () => {
      const resultatSolvable: IResultatSolvable = { id: 29889 };
      const rechercheSolvable: IRechercheSolvable = { id: 7543 };
      resultatSolvable.rechercheSolvable = rechercheSolvable;

      const rechercheSolvableCollection: IRechercheSolvable[] = [{ id: 7543 }];
      jest.spyOn(rechercheSolvableService, 'query').mockReturnValue(of(new HttpResponse({ body: rechercheSolvableCollection })));
      const additionalRechercheSolvables = [rechercheSolvable];
      const expectedCollection: IRechercheSolvable[] = [...additionalRechercheSolvables, ...rechercheSolvableCollection];
      jest.spyOn(rechercheSolvableService, 'addRechercheSolvableToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resultatSolvable });
      comp.ngOnInit();

      expect(rechercheSolvableService.query).toHaveBeenCalled();
      expect(rechercheSolvableService.addRechercheSolvableToCollectionIfMissing).toHaveBeenCalledWith(
        rechercheSolvableCollection,
        ...additionalRechercheSolvables.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.rechercheSolvablesSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const resultatSolvable: IResultatSolvable = { id: 29889 };
      const rechercheSolvable: IRechercheSolvable = { id: 7543 };
      resultatSolvable.rechercheSolvable = rechercheSolvable;

      activatedRoute.data = of({ resultatSolvable });
      comp.ngOnInit();

      expect(comp.rechercheSolvablesSharedCollection()).toContainEqual(rechercheSolvable);
      expect(comp.resultatSolvable).toEqual(resultatSolvable);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResultatSolvable>>();
      const resultatSolvable = { id: 3195 };
      jest.spyOn(resultatSolvableFormService, 'getResultatSolvable').mockReturnValue(resultatSolvable);
      jest.spyOn(resultatSolvableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultatSolvable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resultatSolvable }));
      saveSubject.complete();

      // THEN
      expect(resultatSolvableFormService.getResultatSolvable).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(resultatSolvableService.update).toHaveBeenCalledWith(expect.objectContaining(resultatSolvable));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResultatSolvable>>();
      const resultatSolvable = { id: 3195 };
      jest.spyOn(resultatSolvableFormService, 'getResultatSolvable').mockReturnValue({ id: null });
      jest.spyOn(resultatSolvableService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultatSolvable: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resultatSolvable }));
      saveSubject.complete();

      // THEN
      expect(resultatSolvableFormService.getResultatSolvable).toHaveBeenCalled();
      expect(resultatSolvableService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResultatSolvable>>();
      const resultatSolvable = { id: 3195 };
      jest.spyOn(resultatSolvableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultatSolvable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resultatSolvableService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRechercheSolvable', () => {
      it('should forward to rechercheSolvableService', () => {
        const entity = { id: 7543 };
        const entity2 = { id: 3658 };
        jest.spyOn(rechercheSolvableService, 'compareRechercheSolvable');
        comp.compareRechercheSolvable(entity, entity2);
        expect(rechercheSolvableService.compareRechercheSolvable).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
