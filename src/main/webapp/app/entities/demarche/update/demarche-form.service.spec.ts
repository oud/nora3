import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../demarche.test-samples';

import { DemarcheFormService } from './demarche-form.service';

describe('Demarche Form Service', () => {
  let service: DemarcheFormService;

  beforeEach(() => {
    service = TestBed.inject(DemarcheFormService);
  });

  describe('Service methods', () => {
    describe('createDemarcheFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDemarcheFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            demarcheDate: expect.any(Object),
            numDemarche: expect.any(Object),
            codeOrigine: expect.any(Object),
            codeStatut: expect.any(Object),
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

      it('passing IDemarche should create a new form with FormGroup', () => {
        const formGroup = service.createDemarcheFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            demarcheDate: expect.any(Object),
            numDemarche: expect.any(Object),
            codeOrigine: expect.any(Object),
            codeStatut: expect.any(Object),
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

    describe('getDemarche', () => {
      it('should return NewDemarche for default Demarche initial value', () => {
        const formGroup = service.createDemarcheFormGroup(sampleWithNewData);

        const demarche = service.getDemarche(formGroup);

        expect(demarche).toMatchObject(sampleWithNewData);
      });

      it('should return NewDemarche for empty Demarche initial value', () => {
        const formGroup = service.createDemarcheFormGroup();

        const demarche = service.getDemarche(formGroup);

        expect(demarche).toMatchObject({});
      });

      it('should return IDemarche', () => {
        const formGroup = service.createDemarcheFormGroup(sampleWithRequiredData);

        const demarche = service.getDemarche(formGroup);

        expect(demarche).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDemarche should not enable id FormControl', () => {
        const formGroup = service.createDemarcheFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDemarche should disable id FormControl', () => {
        const formGroup = service.createDemarcheFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
