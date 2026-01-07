import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDossier, NewDossier } from '../dossier.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDossier for edit and NewDossierFormGroupInput for create.
 */
type DossierFormGroupInput = IDossier | PartialWithRequiredKeyOf<NewDossier>;

type DossierFormDefaults = Pick<NewDossier, 'id'>;

type DossierFormGroupContent = {
  id: FormControl<IDossier['id'] | NewDossier['id']>;
  numDossierNor: FormControl<IDossier['numDossierNor']>;
  numDossierGaia: FormControl<IDossier['numDossierGaia']>;
  receptionDateNor: FormControl<IDossier['receptionDateNor']>;
  validationDateNor: FormControl<IDossier['validationDateNor']>;
  codeOrganisme: FormControl<IDossier['codeOrganisme']>;
  codeAgent: FormControl<IDossier['codeAgent']>;
  userCreation: FormControl<IDossier['userCreation']>;
  creationDate: FormControl<IDossier['creationDate']>;
  userMaj: FormControl<IDossier['userMaj']>;
  majDate: FormControl<IDossier['majDate']>;
  numMaj: FormControl<IDossier['numMaj']>;
  debiteur: FormControl<IDossier['debiteur']>;
  creancier: FormControl<IDossier['creancier']>;
};

export type DossierFormGroup = FormGroup<DossierFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DossierFormService {
  createDossierFormGroup(dossier?: DossierFormGroupInput): DossierFormGroup {
    const dossierRawValue = {
      ...this.getFormDefaults(),
      ...(dossier ?? { id: null }),
    };
    return new FormGroup<DossierFormGroupContent>({
      id: new FormControl(
        { value: dossierRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      numDossierNor: new FormControl(dossierRawValue.numDossierNor),
      numDossierGaia: new FormControl(dossierRawValue.numDossierGaia),
      receptionDateNor: new FormControl(dossierRawValue.receptionDateNor),
      validationDateNor: new FormControl(dossierRawValue.validationDateNor),
      codeOrganisme: new FormControl(dossierRawValue.codeOrganisme),
      codeAgent: new FormControl(dossierRawValue.codeAgent),
      userCreation: new FormControl(dossierRawValue.userCreation),
      creationDate: new FormControl(dossierRawValue.creationDate),
      userMaj: new FormControl(dossierRawValue.userMaj),
      majDate: new FormControl(dossierRawValue.majDate),
      numMaj: new FormControl(dossierRawValue.numMaj),
      debiteur: new FormControl(dossierRawValue.debiteur),
      creancier: new FormControl(dossierRawValue.creancier),
    });
  }

  getDossier(form: DossierFormGroup): IDossier | NewDossier {
    return form.getRawValue() as IDossier | NewDossier;
  }

  resetForm(form: DossierFormGroup, dossier: DossierFormGroupInput): void {
    const dossierRawValue = { ...this.getFormDefaults(), ...dossier };
    form.reset({
      ...dossierRawValue,
      id: { value: dossierRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): DossierFormDefaults {
    return {
      id: null,
    };
  }
}
