import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../situation.test-samples';

import { SituationFormService } from './situation-form.service';

describe('Situation Form Service', () => {
  let service: SituationFormService;

  beforeEach(() => {
    service = TestBed.inject(SituationFormService);
  });

  describe('Service methods', () => {
    describe('createSituationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSituationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            situationProDebutDate: expect.any(Object),
            situationProfinDate: expect.any(Object),
            codeSituation: expect.any(Object),
            commentaire: expect.any(Object),
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

      it('passing ISituation should create a new form with FormGroup', () => {
        const formGroup = service.createSituationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            situationProDebutDate: expect.any(Object),
            situationProfinDate: expect.any(Object),
            codeSituation: expect.any(Object),
            commentaire: expect.any(Object),
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

    describe('getSituation', () => {
      it('should return NewSituation for default Situation initial value', () => {
        const formGroup = service.createSituationFormGroup(sampleWithNewData);

        const situation = service.getSituation(formGroup);

        expect(situation).toMatchObject(sampleWithNewData);
      });

      it('should return NewSituation for empty Situation initial value', () => {
        const formGroup = service.createSituationFormGroup();

        const situation = service.getSituation(formGroup);

        expect(situation).toMatchObject({});
      });

      it('should return ISituation', () => {
        const formGroup = service.createSituationFormGroup(sampleWithRequiredData);

        const situation = service.getSituation(formGroup);

        expect(situation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISituation should not enable id FormControl', () => {
        const formGroup = service.createSituationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSituation should disable id FormControl', () => {
        const formGroup = service.createSituationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
