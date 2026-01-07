import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import { IDefaillance } from '../defaillance.model';
import { DefaillanceService } from '../service/defaillance.service';

import { DefaillanceFormService } from './defaillance-form.service';
import { DefaillanceUpdate } from './defaillance-update';

describe('Defaillance Management Update Component', () => {
  let comp: DefaillanceUpdate;
  let fixture: ComponentFixture<DefaillanceUpdate>;
  let activatedRoute: ActivatedRoute;
  let defaillanceFormService: DefaillanceFormService;
  let defaillanceService: DefaillanceService;
  let dossierService: DossierService;

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

    fixture = TestBed.createComponent(DefaillanceUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    defaillanceFormService = TestBed.inject(DefaillanceFormService);
    defaillanceService = TestBed.inject(DefaillanceService);
    dossierService = TestBed.inject(DossierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Dossier query and add missing value', () => {
      const defaillance: IDefaillance = { id: 10347 };
      const dossier: IDossier = { id: 13043 };
      defaillance.dossier = dossier;

      const dossierCollection: IDossier[] = [{ id: 13043 }];
      jest.spyOn(dossierService, 'query').mockReturnValue(of(new HttpResponse({ body: dossierCollection })));
      const additionalDossiers = [dossier];
      const expectedCollection: IDossier[] = [...additionalDossiers, ...dossierCollection];
      jest.spyOn(dossierService, 'addDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ defaillance });
      comp.ngOnInit();

      expect(dossierService.query).toHaveBeenCalled();
      expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(
        dossierCollection,
        ...additionalDossiers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.dossiersSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const defaillance: IDefaillance = { id: 10347 };
      const dossier: IDossier = { id: 13043 };
      defaillance.dossier = dossier;

      activatedRoute.data = of({ defaillance });
      comp.ngOnInit();

      expect(comp.dossiersSharedCollection()).toContainEqual(dossier);
      expect(comp.defaillance).toEqual(defaillance);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDefaillance>>();
      const defaillance = { id: 5429 };
      jest.spyOn(defaillanceFormService, 'getDefaillance').mockReturnValue(defaillance);
      jest.spyOn(defaillanceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ defaillance });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: defaillance }));
      saveSubject.complete();

      // THEN
      expect(defaillanceFormService.getDefaillance).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(defaillanceService.update).toHaveBeenCalledWith(expect.objectContaining(defaillance));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDefaillance>>();
      const defaillance = { id: 5429 };
      jest.spyOn(defaillanceFormService, 'getDefaillance').mockReturnValue({ id: null });
      jest.spyOn(defaillanceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ defaillance: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: defaillance }));
      saveSubject.complete();

      // THEN
      expect(defaillanceFormService.getDefaillance).toHaveBeenCalled();
      expect(defaillanceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDefaillance>>();
      const defaillance = { id: 5429 };
      jest.spyOn(defaillanceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ defaillance });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(defaillanceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDossier', () => {
      it('should forward to dossierService', () => {
        const entity = { id: 13043 };
        const entity2 = { id: 13501 };
        jest.spyOn(dossierService, 'compareDossier');
        comp.compareDossier(entity, entity2);
        expect(dossierService.compareDossier).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
