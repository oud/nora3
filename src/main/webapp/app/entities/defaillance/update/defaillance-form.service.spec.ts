import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../defaillance.test-samples';

import { DefaillanceFormService } from './defaillance-form.service';

describe('Defaillance Form Service', () => {
  let service: DefaillanceFormService;

  beforeEach(() => {
    service = TestBed.inject(DefaillanceFormService);
  });

  describe('Service methods', () => {
    describe('createDefaillanceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDefaillanceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            moisDefaillance: expect.any(Object),
            montantPADue: expect.any(Object),
            montantPAVersee: expect.any(Object),
            flagDetteInitiale: expect.any(Object),
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

      it('passing IDefaillance should create a new form with FormGroup', () => {
        const formGroup = service.createDefaillanceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            moisDefaillance: expect.any(Object),
            montantPADue: expect.any(Object),
            montantPAVersee: expect.any(Object),
            flagDetteInitiale: expect.any(Object),
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

    describe('getDefaillance', () => {
      it('should return NewDefaillance for default Defaillance initial value', () => {
        const formGroup = service.createDefaillanceFormGroup(sampleWithNewData);

        const defaillance = service.getDefaillance(formGroup);

        expect(defaillance).toMatchObject(sampleWithNewData);
      });

      it('should return NewDefaillance for empty Defaillance initial value', () => {
        const formGroup = service.createDefaillanceFormGroup();

        const defaillance = service.getDefaillance(formGroup);

        expect(defaillance).toMatchObject({});
      });

      it('should return IDefaillance', () => {
        const formGroup = service.createDefaillanceFormGroup(sampleWithRequiredData);

        const defaillance = service.getDefaillance(formGroup);

        expect(defaillance).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDefaillance should not enable id FormControl', () => {
        const formGroup = service.createDefaillanceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDefaillance should disable id FormControl', () => {
        const formGroup = service.createDefaillanceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
