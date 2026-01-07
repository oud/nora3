import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import { StatutService } from '../service/statut.service';
import { IStatut } from '../statut.model';

import { StatutFormService } from './statut-form.service';
import { StatutUpdate } from './statut-update';

describe('Statut Management Update Component', () => {
  let comp: StatutUpdate;
  let fixture: ComponentFixture<StatutUpdate>;
  let activatedRoute: ActivatedRoute;
  let statutFormService: StatutFormService;
  let statutService: StatutService;
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

    fixture = TestBed.createComponent(StatutUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    statutFormService = TestBed.inject(StatutFormService);
    statutService = TestBed.inject(StatutService);
    dossierService = TestBed.inject(DossierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Dossier query and add missing value', () => {
      const statut: IStatut = { id: 9043 };
      const dossier: IDossier = { id: 13043 };
      statut.dossier = dossier;

      const dossierCollection: IDossier[] = [{ id: 13043 }];
      jest.spyOn(dossierService, 'query').mockReturnValue(of(new HttpResponse({ body: dossierCollection })));
      const additionalDossiers = [dossier];
      const expectedCollection: IDossier[] = [...additionalDossiers, ...dossierCollection];
      jest.spyOn(dossierService, 'addDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ statut });
      comp.ngOnInit();

      expect(dossierService.query).toHaveBeenCalled();
      expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(
        dossierCollection,
        ...additionalDossiers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.dossiersSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const statut: IStatut = { id: 9043 };
      const dossier: IDossier = { id: 13043 };
      statut.dossier = dossier;

      activatedRoute.data = of({ statut });
      comp.ngOnInit();

      expect(comp.dossiersSharedCollection()).toContainEqual(dossier);
      expect(comp.statut).toEqual(statut);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatut>>();
      const statut = { id: 10699 };
      jest.spyOn(statutFormService, 'getStatut').mockReturnValue(statut);
      jest.spyOn(statutService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statut });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statut }));
      saveSubject.complete();

      // THEN
      expect(statutFormService.getStatut).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(statutService.update).toHaveBeenCalledWith(expect.objectContaining(statut));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatut>>();
      const statut = { id: 10699 };
      jest.spyOn(statutFormService, 'getStatut').mockReturnValue({ id: null });
      jest.spyOn(statutService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statut: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statut }));
      saveSubject.complete();

      // THEN
      expect(statutFormService.getStatut).toHaveBeenCalled();
      expect(statutService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatut>>();
      const statut = { id: 10699 };
      jest.spyOn(statutService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statut });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(statutService.update).toHaveBeenCalled();
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
