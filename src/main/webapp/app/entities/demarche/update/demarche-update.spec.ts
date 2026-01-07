import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import { IDemarche } from '../demarche.model';
import { DemarcheService } from '../service/demarche.service';

import { DemarcheFormService } from './demarche-form.service';
import { DemarcheUpdate } from './demarche-update';

describe('Demarche Management Update Component', () => {
  let comp: DemarcheUpdate;
  let fixture: ComponentFixture<DemarcheUpdate>;
  let activatedRoute: ActivatedRoute;
  let demarcheFormService: DemarcheFormService;
  let demarcheService: DemarcheService;
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

    fixture = TestBed.createComponent(DemarcheUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demarcheFormService = TestBed.inject(DemarcheFormService);
    demarcheService = TestBed.inject(DemarcheService);
    dossierService = TestBed.inject(DossierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Dossier query and add missing value', () => {
      const demarche: IDemarche = { id: 10476 };
      const dossier: IDossier = { id: 13043 };
      demarche.dossier = dossier;

      const dossierCollection: IDossier[] = [{ id: 13043 }];
      jest.spyOn(dossierService, 'query').mockReturnValue(of(new HttpResponse({ body: dossierCollection })));
      const additionalDossiers = [dossier];
      const expectedCollection: IDossier[] = [...additionalDossiers, ...dossierCollection];
      jest.spyOn(dossierService, 'addDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demarche });
      comp.ngOnInit();

      expect(dossierService.query).toHaveBeenCalled();
      expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(
        dossierCollection,
        ...additionalDossiers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.dossiersSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const demarche: IDemarche = { id: 10476 };
      const dossier: IDossier = { id: 13043 };
      demarche.dossier = dossier;

      activatedRoute.data = of({ demarche });
      comp.ngOnInit();

      expect(comp.dossiersSharedCollection()).toContainEqual(dossier);
      expect(comp.demarche).toEqual(demarche);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemarche>>();
      const demarche = { id: 7199 };
      jest.spyOn(demarcheFormService, 'getDemarche').mockReturnValue(demarche);
      jest.spyOn(demarcheService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demarche });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demarche }));
      saveSubject.complete();

      // THEN
      expect(demarcheFormService.getDemarche).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(demarcheService.update).toHaveBeenCalledWith(expect.objectContaining(demarche));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemarche>>();
      const demarche = { id: 7199 };
      jest.spyOn(demarcheFormService, 'getDemarche').mockReturnValue({ id: null });
      jest.spyOn(demarcheService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demarche: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demarche }));
      saveSubject.complete();

      // THEN
      expect(demarcheFormService.getDemarche).toHaveBeenCalled();
      expect(demarcheService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemarche>>();
      const demarche = { id: 7199 };
      jest.spyOn(demarcheService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demarche });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demarcheService.update).toHaveBeenCalled();
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
