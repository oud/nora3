import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IDebiteur } from 'app/entities/debiteur/debiteur.model';
import { DebiteurService } from 'app/entities/debiteur/service/debiteur.service';
import { IRechercheSolvable } from '../recherche-solvable.model';
import { RechercheSolvableService } from '../service/recherche-solvable.service';

import { RechercheSolvableFormService } from './recherche-solvable-form.service';
import { RechercheSolvableUpdate } from './recherche-solvable-update';

describe('RechercheSolvable Management Update Component', () => {
  let comp: RechercheSolvableUpdate;
  let fixture: ComponentFixture<RechercheSolvableUpdate>;
  let activatedRoute: ActivatedRoute;
  let rechercheSolvableFormService: RechercheSolvableFormService;
  let rechercheSolvableService: RechercheSolvableService;
  let debiteurService: DebiteurService;

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

    fixture = TestBed.createComponent(RechercheSolvableUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    rechercheSolvableFormService = TestBed.inject(RechercheSolvableFormService);
    rechercheSolvableService = TestBed.inject(RechercheSolvableService);
    debiteurService = TestBed.inject(DebiteurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Debiteur query and add missing value', () => {
      const rechercheSolvable: IRechercheSolvable = { id: 3658 };
      const debiteur: IDebiteur = { id: 18158 };
      rechercheSolvable.debiteur = debiteur;

      const debiteurCollection: IDebiteur[] = [{ id: 18158 }];
      jest.spyOn(debiteurService, 'query').mockReturnValue(of(new HttpResponse({ body: debiteurCollection })));
      const additionalDebiteurs = [debiteur];
      const expectedCollection: IDebiteur[] = [...additionalDebiteurs, ...debiteurCollection];
      jest.spyOn(debiteurService, 'addDebiteurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rechercheSolvable });
      comp.ngOnInit();

      expect(debiteurService.query).toHaveBeenCalled();
      expect(debiteurService.addDebiteurToCollectionIfMissing).toHaveBeenCalledWith(
        debiteurCollection,
        ...additionalDebiteurs.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.debiteursSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const rechercheSolvable: IRechercheSolvable = { id: 3658 };
      const debiteur: IDebiteur = { id: 18158 };
      rechercheSolvable.debiteur = debiteur;

      activatedRoute.data = of({ rechercheSolvable });
      comp.ngOnInit();

      expect(comp.debiteursSharedCollection()).toContainEqual(debiteur);
      expect(comp.rechercheSolvable).toEqual(rechercheSolvable);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRechercheSolvable>>();
      const rechercheSolvable = { id: 7543 };
      jest.spyOn(rechercheSolvableFormService, 'getRechercheSolvable').mockReturnValue(rechercheSolvable);
      jest.spyOn(rechercheSolvableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rechercheSolvable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rechercheSolvable }));
      saveSubject.complete();

      // THEN
      expect(rechercheSolvableFormService.getRechercheSolvable).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(rechercheSolvableService.update).toHaveBeenCalledWith(expect.objectContaining(rechercheSolvable));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRechercheSolvable>>();
      const rechercheSolvable = { id: 7543 };
      jest.spyOn(rechercheSolvableFormService, 'getRechercheSolvable').mockReturnValue({ id: null });
      jest.spyOn(rechercheSolvableService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rechercheSolvable: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rechercheSolvable }));
      saveSubject.complete();

      // THEN
      expect(rechercheSolvableFormService.getRechercheSolvable).toHaveBeenCalled();
      expect(rechercheSolvableService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRechercheSolvable>>();
      const rechercheSolvable = { id: 7543 };
      jest.spyOn(rechercheSolvableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rechercheSolvable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(rechercheSolvableService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDebiteur', () => {
      it('should forward to debiteurService', () => {
        const entity = { id: 18158 };
        const entity2 = { id: 6034 };
        jest.spyOn(debiteurService, 'compareDebiteur');
        comp.compareDebiteur(entity, entity2);
        expect(debiteurService.compareDebiteur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
