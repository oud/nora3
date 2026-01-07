import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../resultat-solvable.test-samples';

import { ResultatSolvableFormService } from './resultat-solvable-form.service';

describe('ResultatSolvable Form Service', () => {
  let service: ResultatSolvableFormService;

  beforeEach(() => {
    service = TestBed.inject(ResultatSolvableFormService);
  });

  describe('Service methods', () => {
    describe('createResultatSolvableFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResultatSolvableFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            moisSolvabiliteDate: expect.any(Object),
            codeEtatSolvabilite: expect.any(Object),
            codeAgent: expect.any(Object),
            userCreation: expect.any(Object),
            creationDate: expect.any(Object),
            userMaj: expect.any(Object),
            majDate: expect.any(Object),
            numMaj: expect.any(Object),
            rechercheSolvable: expect.any(Object),
          }),
        );
      });

      it('passing IResultatSolvable should create a new form with FormGroup', () => {
        const formGroup = service.createResultatSolvableFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            moisSolvabiliteDate: expect.any(Object),
            codeEtatSolvabilite: expect.any(Object),
            codeAgent: expect.any(Object),
            userCreation: expect.any(Object),
            creationDate: expect.any(Object),
            userMaj: expect.any(Object),
            majDate: expect.any(Object),
            numMaj: expect.any(Object),
            rechercheSolvable: expect.any(Object),
          }),
        );
      });
    });

    describe('getResultatSolvable', () => {
      it('should return NewResultatSolvable for default ResultatSolvable initial value', () => {
        const formGroup = service.createResultatSolvableFormGroup(sampleWithNewData);

        const resultatSolvable = service.getResultatSolvable(formGroup);

        expect(resultatSolvable).toMatchObject(sampleWithNewData);
      });

      it('should return NewResultatSolvable for empty ResultatSolvable initial value', () => {
        const formGroup = service.createResultatSolvableFormGroup();

        const resultatSolvable = service.getResultatSolvable(formGroup);

        expect(resultatSolvable).toMatchObject({});
      });

      it('should return IResultatSolvable', () => {
        const formGroup = service.createResultatSolvableFormGroup(sampleWithRequiredData);

        const resultatSolvable = service.getResultatSolvable(formGroup);

        expect(resultatSolvable).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResultatSolvable should not enable id FormControl', () => {
        const formGroup = service.createResultatSolvableFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResultatSolvable should disable id FormControl', () => {
        const formGroup = service.createResultatSolvableFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
