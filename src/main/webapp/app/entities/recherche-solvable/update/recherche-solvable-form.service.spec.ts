import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../recherche-solvable.test-samples';

import { RechercheSolvableFormService } from './recherche-solvable-form.service';

describe('RechercheSolvable Form Service', () => {
  let service: RechercheSolvableFormService;

  beforeEach(() => {
    service = TestBed.inject(RechercheSolvableFormService);
  });

  describe('Service methods', () => {
    describe('createRechercheSolvableFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRechercheSolvableFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            rechercheSolvabiliteDebutDate: expect.any(Object),
            codeAgent: expect.any(Object),
            userCreation: expect.any(Object),
            creationDate: expect.any(Object),
            userMaj: expect.any(Object),
            majDate: expect.any(Object),
            numMaj: expect.any(Object),
            debiteur: expect.any(Object),
          }),
        );
      });

      it('passing IRechercheSolvable should create a new form with FormGroup', () => {
        const formGroup = service.createRechercheSolvableFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            rechercheSolvabiliteDebutDate: expect.any(Object),
            codeAgent: expect.any(Object),
            userCreation: expect.any(Object),
            creationDate: expect.any(Object),
            userMaj: expect.any(Object),
            majDate: expect.any(Object),
            numMaj: expect.any(Object),
            debiteur: expect.any(Object),
          }),
        );
      });
    });

    describe('getRechercheSolvable', () => {
      it('should return NewRechercheSolvable for default RechercheSolvable initial value', () => {
        const formGroup = service.createRechercheSolvableFormGroup(sampleWithNewData);

        const rechercheSolvable = service.getRechercheSolvable(formGroup);

        expect(rechercheSolvable).toMatchObject(sampleWithNewData);
      });

      it('should return NewRechercheSolvable for empty RechercheSolvable initial value', () => {
        const formGroup = service.createRechercheSolvableFormGroup();

        const rechercheSolvable = service.getRechercheSolvable(formGroup);

        expect(rechercheSolvable).toMatchObject({});
      });

      it('should return IRechercheSolvable', () => {
        const formGroup = service.createRechercheSolvableFormGroup(sampleWithRequiredData);

        const rechercheSolvable = service.getRechercheSolvable(formGroup);

        expect(rechercheSolvable).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRechercheSolvable should not enable id FormControl', () => {
        const formGroup = service.createRechercheSolvableFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRechercheSolvable should disable id FormControl', () => {
        const formGroup = service.createRechercheSolvableFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
