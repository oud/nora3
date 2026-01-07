import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICreancier } from 'app/entities/creancier/creancier.model';
import { CreancierService } from 'app/entities/creancier/service/creancier.service';
import { IDebiteur } from 'app/entities/debiteur/debiteur.model';
import { DebiteurService } from 'app/entities/debiteur/service/debiteur.service';
import { IDossier } from '../dossier.model';
import { DossierService } from '../service/dossier.service';

import { DossierFormService } from './dossier-form.service';
import { DossierUpdate } from './dossier-update';

describe('Dossier Management Update Component', () => {
  let comp: DossierUpdate;
  let fixture: ComponentFixture<DossierUpdate>;
  let activatedRoute: ActivatedRoute;
  let dossierFormService: DossierFormService;
  let dossierService: DossierService;
  let debiteurService: DebiteurService;
  let creancierService: CreancierService;

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

    fixture = TestBed.createComponent(DossierUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dossierFormService = TestBed.inject(DossierFormService);
    dossierService = TestBed.inject(DossierService);
    debiteurService = TestBed.inject(DebiteurService);
    creancierService = TestBed.inject(CreancierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call debiteur query and add missing value', () => {
      const dossier: IDossier = { id: 13501 };
      const debiteur: IDebiteur = { id: 18158 };
      dossier.debiteur = debiteur;

      const debiteurCollection: IDebiteur[] = [{ id: 18158 }];
      jest.spyOn(debiteurService, 'query').mockReturnValue(of(new HttpResponse({ body: debiteurCollection })));
      const expectedCollection: IDebiteur[] = [debiteur, ...debiteurCollection];
      jest.spyOn(debiteurService, 'addDebiteurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dossier });
      comp.ngOnInit();

      expect(debiteurService.query).toHaveBeenCalled();
      expect(debiteurService.addDebiteurToCollectionIfMissing).toHaveBeenCalledWith(debiteurCollection, debiteur);
      expect(comp.debiteursCollection()).toEqual(expectedCollection);
    });

    it('should call creancier query and add missing value', () => {
      const dossier: IDossier = { id: 13501 };
      const creancier: ICreancier = { id: 32390 };
      dossier.creancier = creancier;

      const creancierCollection: ICreancier[] = [{ id: 32390 }];
      jest.spyOn(creancierService, 'query').mockReturnValue(of(new HttpResponse({ body: creancierCollection })));
      const expectedCollection: ICreancier[] = [creancier, ...creancierCollection];
      jest.spyOn(creancierService, 'addCreancierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dossier });
      comp.ngOnInit();

      expect(creancierService.query).toHaveBeenCalled();
      expect(creancierService.addCreancierToCollectionIfMissing).toHaveBeenCalledWith(creancierCollection, creancier);
      expect(comp.creanciersCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const dossier: IDossier = { id: 13501 };
      const debiteur: IDebiteur = { id: 18158 };
      dossier.debiteur = debiteur;
      const creancier: ICreancier = { id: 32390 };
      dossier.creancier = creancier;

      activatedRoute.data = of({ dossier });
      comp.ngOnInit();

      expect(comp.debiteursCollection()).toContainEqual(debiteur);
      expect(comp.creanciersCollection()).toContainEqual(creancier);
      expect(comp.dossier).toEqual(dossier);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDossier>>();
      const dossier = { id: 13043 };
      jest.spyOn(dossierFormService, 'getDossier').mockReturnValue(dossier);
      jest.spyOn(dossierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dossier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dossier }));
      saveSubject.complete();

      // THEN
      expect(dossierFormService.getDossier).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dossierService.update).toHaveBeenCalledWith(expect.objectContaining(dossier));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDossier>>();
      const dossier = { id: 13043 };
      jest.spyOn(dossierFormService, 'getDossier').mockReturnValue({ id: null });
      jest.spyOn(dossierService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dossier: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dossier }));
      saveSubject.complete();

      // THEN
      expect(dossierFormService.getDossier).toHaveBeenCalled();
      expect(dossierService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDossier>>();
      const dossier = { id: 13043 };
      jest.spyOn(dossierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dossier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dossierService.update).toHaveBeenCalled();
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

    describe('compareCreancier', () => {
      it('should forward to creancierService', () => {
        const entity = { id: 32390 };
        const entity2 = { id: 1793 };
        jest.spyOn(creancierService, 'compareCreancier');
        comp.compareCreancier(entity, entity2);
        expect(creancierService.compareCreancier).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
