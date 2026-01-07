import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../dossier.test-samples';

import { DossierFormService } from './dossier-form.service';

describe('Dossier Form Service', () => {
  let service: DossierFormService;

  beforeEach(() => {
    service = TestBed.inject(DossierFormService);
  });

  describe('Service methods', () => {
    describe('createDossierFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDossierFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numDossierNor: expect.any(Object),
            numDossierGaia: expect.any(Object),
            receptionDateNor: expect.any(Object),
            validationDateNor: expect.any(Object),
            codeOrganisme: expect.any(Object),
            codeAgent: expect.any(Object),
            userCreation: expect.any(Object),
            creationDate: expect.any(Object),
            userMaj: expect.any(Object),
            majDate: expect.any(Object),
            numMaj: expect.any(Object),
            debiteur: expect.any(Object),
            creancier: expect.any(Object),
          }),
        );
      });

      it('passing IDossier should create a new form with FormGroup', () => {
        const formGroup = service.createDossierFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numDossierNor: expect.any(Object),
            numDossierGaia: expect.any(Object),
            receptionDateNor: expect.any(Object),
            validationDateNor: expect.any(Object),
            codeOrganisme: expect.any(Object),
            codeAgent: expect.any(Object),
            userCreation: expect.any(Object),
            creationDate: expect.any(Object),
            userMaj: expect.any(Object),
            majDate: expect.any(Object),
            numMaj: expect.any(Object),
            debiteur: expect.any(Object),
            creancier: expect.any(Object),
          }),
        );
      });
    });

    describe('getDossier', () => {
      it('should return NewDossier for default Dossier initial value', () => {
        const formGroup = service.createDossierFormGroup(sampleWithNewData);

        const dossier = service.getDossier(formGroup);

        expect(dossier).toMatchObject(sampleWithNewData);
      });

      it('should return NewDossier for empty Dossier initial value', () => {
        const formGroup = service.createDossierFormGroup();

        const dossier = service.getDossier(formGroup);

        expect(dossier).toMatchObject({});
      });

      it('should return IDossier', () => {
        const formGroup = service.createDossierFormGroup(sampleWithRequiredData);

        const dossier = service.getDossier(formGroup);

        expect(dossier).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDossier should not enable id FormControl', () => {
        const formGroup = service.createDossierFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDossier should disable id FormControl', () => {
        const formGroup = service.createDossierFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
