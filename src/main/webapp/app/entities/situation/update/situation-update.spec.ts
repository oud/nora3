import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IDebiteur } from 'app/entities/debiteur/debiteur.model';
import { DebiteurService } from 'app/entities/debiteur/service/debiteur.service';
import { SituationService } from '../service/situation.service';
import { ISituation } from '../situation.model';

import { SituationFormService } from './situation-form.service';
import { SituationUpdate } from './situation-update';

describe('Situation Management Update Component', () => {
  let comp: SituationUpdate;
  let fixture: ComponentFixture<SituationUpdate>;
  let activatedRoute: ActivatedRoute;
  let situationFormService: SituationFormService;
  let situationService: SituationService;
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

    fixture = TestBed.createComponent(SituationUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    situationFormService = TestBed.inject(SituationFormService);
    situationService = TestBed.inject(SituationService);
    debiteurService = TestBed.inject(DebiteurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Debiteur query and add missing value', () => {
      const situation: ISituation = { id: 12449 };
      const debiteur: IDebiteur = { id: 18158 };
      situation.debiteur = debiteur;

      const debiteurCollection: IDebiteur[] = [{ id: 18158 }];
      jest.spyOn(debiteurService, 'query').mockReturnValue(of(new HttpResponse({ body: debiteurCollection })));
      const additionalDebiteurs = [debiteur];
      const expectedCollection: IDebiteur[] = [...additionalDebiteurs, ...debiteurCollection];
      jest.spyOn(debiteurService, 'addDebiteurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ situation });
      comp.ngOnInit();

      expect(debiteurService.query).toHaveBeenCalled();
      expect(debiteurService.addDebiteurToCollectionIfMissing).toHaveBeenCalledWith(
        debiteurCollection,
        ...additionalDebiteurs.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.debiteursSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const situation: ISituation = { id: 12449 };
      const debiteur: IDebiteur = { id: 18158 };
      situation.debiteur = debiteur;

      activatedRoute.data = of({ situation });
      comp.ngOnInit();

      expect(comp.debiteursSharedCollection()).toContainEqual(debiteur);
      expect(comp.situation).toEqual(situation);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISituation>>();
      const situation = { id: 28437 };
      jest.spyOn(situationFormService, 'getSituation').mockReturnValue(situation);
      jest.spyOn(situationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ situation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: situation }));
      saveSubject.complete();

      // THEN
      expect(situationFormService.getSituation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(situationService.update).toHaveBeenCalledWith(expect.objectContaining(situation));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISituation>>();
      const situation = { id: 28437 };
      jest.spyOn(situationFormService, 'getSituation').mockReturnValue({ id: null });
      jest.spyOn(situationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ situation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: situation }));
      saveSubject.complete();

      // THEN
      expect(situationFormService.getSituation).toHaveBeenCalled();
      expect(situationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISituation>>();
      const situation = { id: 28437 };
      jest.spyOn(situationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ situation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(situationService.update).toHaveBeenCalled();
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
