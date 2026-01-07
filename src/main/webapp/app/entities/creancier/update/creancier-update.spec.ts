import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICreancier } from '../creancier.model';
import { CreancierService } from '../service/creancier.service';

import { CreancierFormService } from './creancier-form.service';
import { CreancierUpdate } from './creancier-update';

describe('Creancier Management Update Component', () => {
  let comp: CreancierUpdate;
  let fixture: ComponentFixture<CreancierUpdate>;
  let activatedRoute: ActivatedRoute;
  let creancierFormService: CreancierFormService;
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

    fixture = TestBed.createComponent(CreancierUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    creancierFormService = TestBed.inject(CreancierFormService);
    creancierService = TestBed.inject(CreancierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const creancier: ICreancier = { id: 1793 };

      activatedRoute.data = of({ creancier });
      comp.ngOnInit();

      expect(comp.creancier).toEqual(creancier);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICreancier>>();
      const creancier = { id: 32390 };
      jest.spyOn(creancierFormService, 'getCreancier').mockReturnValue(creancier);
      jest.spyOn(creancierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ creancier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: creancier }));
      saveSubject.complete();

      // THEN
      expect(creancierFormService.getCreancier).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(creancierService.update).toHaveBeenCalledWith(expect.objectContaining(creancier));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICreancier>>();
      const creancier = { id: 32390 };
      jest.spyOn(creancierFormService, 'getCreancier').mockReturnValue({ id: null });
      jest.spyOn(creancierService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ creancier: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: creancier }));
      saveSubject.complete();

      // THEN
      expect(creancierFormService.getCreancier).toHaveBeenCalled();
      expect(creancierService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICreancier>>();
      const creancier = { id: 32390 };
      jest.spyOn(creancierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ creancier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(creancierService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
