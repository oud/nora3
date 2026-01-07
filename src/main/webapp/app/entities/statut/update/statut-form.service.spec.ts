import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../statut.test-samples';

import { StatutFormService } from './statut-form.service';

describe('Statut Form Service', () => {
  let service: StatutFormService;

  beforeEach(() => {
    service = TestBed.inject(StatutFormService);
  });

  describe('Service methods', () => {
    describe('createStatutFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStatutFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            statutDebutDate: expect.any(Object),
            statutFinDate: expect.any(Object),
            codeStatut: expect.any(Object),
            motifStatut: expect.any(Object),
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

      it('passing IStatut should create a new form with FormGroup', () => {
        const formGroup = service.createStatutFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            statutDebutDate: expect.any(Object),
            statutFinDate: expect.any(Object),
            codeStatut: expect.any(Object),
            motifStatut: expect.any(Object),
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

    describe('getStatut', () => {
      it('should return NewStatut for default Statut initial value', () => {
        const formGroup = service.createStatutFormGroup(sampleWithNewData);

        const statut = service.getStatut(formGroup);

        expect(statut).toMatchObject(sampleWithNewData);
      });

      it('should return NewStatut for empty Statut initial value', () => {
        const formGroup = service.createStatutFormGroup();

        const statut = service.getStatut(formGroup);

        expect(statut).toMatchObject({});
      });

      it('should return IStatut', () => {
        const formGroup = service.createStatutFormGroup(sampleWithRequiredData);

        const statut = service.getStatut(formGroup);

        expect(statut).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStatut should not enable id FormControl', () => {
        const formGroup = service.createStatutFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStatut should disable id FormControl', () => {
        const formGroup = service.createStatutFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
