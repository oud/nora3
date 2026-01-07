import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import { IEnfant } from '../enfant.model';
import { EnfantService } from '../service/enfant.service';

import { EnfantFormService } from './enfant-form.service';
import { EnfantUpdate } from './enfant-update';

describe('Enfant Management Update Component', () => {
  let comp: EnfantUpdate;
  let fixture: ComponentFixture<EnfantUpdate>;
  let activatedRoute: ActivatedRoute;
  let enfantFormService: EnfantFormService;
  let enfantService: EnfantService;
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

    fixture = TestBed.createComponent(EnfantUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    enfantFormService = TestBed.inject(EnfantFormService);
    enfantService = TestBed.inject(EnfantService);
    dossierService = TestBed.inject(DossierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Dossier query and add missing value', () => {
      const enfant: IEnfant = { id: 10156 };
      const dossier: IDossier = { id: 13043 };
      enfant.dossier = dossier;

      const dossierCollection: IDossier[] = [{ id: 13043 }];
      jest.spyOn(dossierService, 'query').mockReturnValue(of(new HttpResponse({ body: dossierCollection })));
      const additionalDossiers = [dossier];
      const expectedCollection: IDossier[] = [...additionalDossiers, ...dossierCollection];
      jest.spyOn(dossierService, 'addDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      expect(dossierService.query).toHaveBeenCalled();
      expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(
        dossierCollection,
        ...additionalDossiers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.dossiersSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const enfant: IEnfant = { id: 10156 };
      const dossier: IDossier = { id: 13043 };
      enfant.dossier = dossier;

      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      expect(comp.dossiersSharedCollection()).toContainEqual(dossier);
      expect(comp.enfant).toEqual(enfant);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnfant>>();
      const enfant = { id: 8992 };
      jest.spyOn(enfantFormService, 'getEnfant').mockReturnValue(enfant);
      jest.spyOn(enfantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enfant }));
      saveSubject.complete();

      // THEN
      expect(enfantFormService.getEnfant).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(enfantService.update).toHaveBeenCalledWith(expect.objectContaining(enfant));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnfant>>();
      const enfant = { id: 8992 };
      jest.spyOn(enfantFormService, 'getEnfant').mockReturnValue({ id: null });
      jest.spyOn(enfantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enfant: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enfant }));
      saveSubject.complete();

      // THEN
      expect(enfantFormService.getEnfant).toHaveBeenCalled();
      expect(enfantService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnfant>>();
      const enfant = { id: 8992 };
      jest.spyOn(enfantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(enfantService.update).toHaveBeenCalled();
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
