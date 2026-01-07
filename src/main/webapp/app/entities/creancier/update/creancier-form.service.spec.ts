import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../creancier.test-samples';

import { CreancierFormService } from './creancier-form.service';

describe('Creancier Form Service', () => {
  let service: CreancierFormService;

  beforeEach(() => {
    service = TestBed.inject(CreancierFormService);
  });

  describe('Service methods', () => {
    describe('createCreancierFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCreancierFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nir: expect.any(Object),
            cleNir: expect.any(Object),
            numAllocCristal: expect.any(Object),
            numPersonneCristal: expect.any(Object),
            codeOrganismeCristal: expect.any(Object),
            situationFamilialeDebutDate: expect.any(Object),
            codeSituationFamiliale: expect.any(Object),
            codeNationalite: expect.any(Object),
            codeAgent: expect.any(Object),
            userCreation: expect.any(Object),
            creationDate: expect.any(Object),
            userMaj: expect.any(Object),
            majDate: expect.any(Object),
            numMaj: expect.any(Object),
          }),
        );
      });

      it('passing ICreancier should create a new form with FormGroup', () => {
        const formGroup = service.createCreancierFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nir: expect.any(Object),
            cleNir: expect.any(Object),
            numAllocCristal: expect.any(Object),
            numPersonneCristal: expect.any(Object),
            codeOrganismeCristal: expect.any(Object),
            situationFamilialeDebutDate: expect.any(Object),
            codeSituationFamiliale: expect.any(Object),
            codeNationalite: expect.any(Object),
            codeAgent: expect.any(Object),
            userCreation: expect.any(Object),
            creationDate: expect.any(Object),
            userMaj: expect.any(Object),
            majDate: expect.any(Object),
            numMaj: expect.any(Object),
          }),
        );
      });
    });

    describe('getCreancier', () => {
      it('should return NewCreancier for default Creancier initial value', () => {
        const formGroup = service.createCreancierFormGroup(sampleWithNewData);

        const creancier = service.getCreancier(formGroup);

        expect(creancier).toMatchObject(sampleWithNewData);
      });

      it('should return NewCreancier for empty Creancier initial value', () => {
        const formGroup = service.createCreancierFormGroup();

        const creancier = service.getCreancier(formGroup);

        expect(creancier).toMatchObject({});
      });

      it('should return ICreancier', () => {
        const formGroup = service.createCreancierFormGroup(sampleWithRequiredData);

        const creancier = service.getCreancier(formGroup);

        expect(creancier).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICreancier should not enable id FormControl', () => {
        const formGroup = service.createCreancierFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCreancier should disable id FormControl', () => {
        const formGroup = service.createCreancierFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
