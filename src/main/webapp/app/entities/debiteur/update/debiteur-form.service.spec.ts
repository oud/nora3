import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../debiteur.test-samples';

import { DebiteurFormService } from './debiteur-form.service';

describe('Debiteur Form Service', () => {
  let service: DebiteurFormService;

  beforeEach(() => {
    service = TestBed.inject(DebiteurFormService);
  });

  describe('Service methods', () => {
    describe('createDebiteurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDebiteurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nir: expect.any(Object),
            cleNir: expect.any(Object),
            numAllocCristal: expect.any(Object),
            codeOrganismeCristal: expect.any(Object),
            codeAgent: expect.any(Object),
            userCreation: expect.any(Object),
            creationDate: expect.any(Object),
            userMaj: expect.any(Object),
            majDate: expect.any(Object),
            numMaj: expect.any(Object),
          }),
        );
      });

      it('passing IDebiteur should create a new form with FormGroup', () => {
        const formGroup = service.createDebiteurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nir: expect.any(Object),
            cleNir: expect.any(Object),
            numAllocCristal: expect.any(Object),
            codeOrganismeCristal: expect.any(Object),
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

    describe('getDebiteur', () => {
      it('should return NewDebiteur for default Debiteur initial value', () => {
        const formGroup = service.createDebiteurFormGroup(sampleWithNewData);

        const debiteur = service.getDebiteur(formGroup);

        expect(debiteur).toMatchObject(sampleWithNewData);
      });

      it('should return NewDebiteur for empty Debiteur initial value', () => {
        const formGroup = service.createDebiteurFormGroup();

        const debiteur = service.getDebiteur(formGroup);

        expect(debiteur).toMatchObject({});
      });

      it('should return IDebiteur', () => {
        const formGroup = service.createDebiteurFormGroup(sampleWithRequiredData);

        const debiteur = service.getDebiteur(formGroup);

        expect(debiteur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDebiteur should not enable id FormControl', () => {
        const formGroup = service.createDebiteurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDebiteur should disable id FormControl', () => {
        const formGroup = service.createDebiteurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
