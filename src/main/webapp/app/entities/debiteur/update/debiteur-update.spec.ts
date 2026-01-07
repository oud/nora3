import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IDebiteur } from '../debiteur.model';
import { DebiteurService } from '../service/debiteur.service';

import { DebiteurFormService } from './debiteur-form.service';
import { DebiteurUpdate } from './debiteur-update';

describe('Debiteur Management Update Component', () => {
  let comp: DebiteurUpdate;
  let fixture: ComponentFixture<DebiteurUpdate>;
  let activatedRoute: ActivatedRoute;
  let debiteurFormService: DebiteurFormService;
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

    fixture = TestBed.createComponent(DebiteurUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    debiteurFormService = TestBed.inject(DebiteurFormService);
    debiteurService = TestBed.inject(DebiteurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const debiteur: IDebiteur = { id: 6034 };

      activatedRoute.data = of({ debiteur });
      comp.ngOnInit();

      expect(comp.debiteur).toEqual(debiteur);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDebiteur>>();
      const debiteur = { id: 18158 };
      jest.spyOn(debiteurFormService, 'getDebiteur').mockReturnValue(debiteur);
      jest.spyOn(debiteurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ debiteur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: debiteur }));
      saveSubject.complete();

      // THEN
      expect(debiteurFormService.getDebiteur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(debiteurService.update).toHaveBeenCalledWith(expect.objectContaining(debiteur));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDebiteur>>();
      const debiteur = { id: 18158 };
      jest.spyOn(debiteurFormService, 'getDebiteur').mockReturnValue({ id: null });
      jest.spyOn(debiteurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ debiteur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: debiteur }));
      saveSubject.complete();

      // THEN
      expect(debiteurFormService.getDebiteur).toHaveBeenCalled();
      expect(debiteurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDebiteur>>();
      const debiteur = { id: 18158 };
      jest.spyOn(debiteurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ debiteur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(debiteurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
