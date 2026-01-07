import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../enfant.test-samples';

import { EnfantFormService } from './enfant-form.service';

describe('Enfant Form Service', () => {
  let service: EnfantFormService;

  beforeEach(() => {
    service = TestBed.inject(EnfantFormService);
  });

  describe('Service methods', () => {
    describe('createEnfantFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEnfantFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nir: expect.any(Object),
            cleNir: expect.any(Object),
            numPersonneGaia: expect.any(Object),
            codeAgent: expect.any(Object),
            userCreation: expect.any(Object),
            creationDate: expect.any(Object),
            userMaj: expect.any(Object),
            majDate: expect.any(Object),
            numMaj: expect.any(Object),
            dossier: expect.any(Object),
          }),
        );
      });

      it('passing IEnfant should create a new form with FormGroup', () => {
        const formGroup = service.createEnfantFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nir: expect.any(Object),
            cleNir: expect.any(Object),
            numPersonneGaia: expect.any(Object),
            codeAgent: expect.any(Object),
            userCreation: expect.any(Object),
            creationDate: expect.any(Object),
            userMaj: expect.any(Object),
            majDate: expect.any(Object),
            numMaj: expect.any(Object),
            dossier: expect.any(Object),
          }),
        );
      });
    });

    describe('getEnfant', () => {
      it('should return NewEnfant for default Enfant initial value', () => {
        const formGroup = service.createEnfantFormGroup(sampleWithNewData);

        const enfant = service.getEnfant(formGroup);

        expect(enfant).toMatchObject(sampleWithNewData);
      });

      it('should return NewEnfant for empty Enfant initial value', () => {
        const formGroup = service.createEnfantFormGroup();

        const enfant = service.getEnfant(formGroup);

        expect(enfant).toMatchObject({});
      });

      it('should return IEnfant', () => {
        const formGroup = service.createEnfantFormGroup(sampleWithRequiredData);

        const enfant = service.getEnfant(formGroup);

        expect(enfant).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEnfant should not enable id FormControl', () => {
        const formGroup = service.createEnfantFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEnfant should disable id FormControl', () => {
        const formGroup = service.createEnfantFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
