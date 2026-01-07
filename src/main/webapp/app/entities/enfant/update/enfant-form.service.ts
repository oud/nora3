import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEnfant, NewEnfant } from '../enfant.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEnfant for edit and NewEnfantFormGroupInput for create.
 */
type EnfantFormGroupInput = IEnfant | PartialWithRequiredKeyOf<NewEnfant>;

type EnfantFormDefaults = Pick<NewEnfant, 'id'>;

type EnfantFormGroupContent = {
  id: FormControl<IEnfant['id'] | NewEnfant['id']>;
  nir: FormControl<IEnfant['nir']>;
  cleNir: FormControl<IEnfant['cleNir']>;
  numPersonneGaia: FormControl<IEnfant['numPersonneGaia']>;
  codeAgent: FormControl<IEnfant['codeAgent']>;
  userCreation: FormControl<IEnfant['userCreation']>;
  creationDate: FormControl<IEnfant['creationDate']>;
  userMaj: FormControl<IEnfant['userMaj']>;
  majDate: FormControl<IEnfant['majDate']>;
  numMaj: FormControl<IEnfant['numMaj']>;
  dossier: FormControl<IEnfant['dossier']>;
};

export type EnfantFormGroup = FormGroup<EnfantFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EnfantFormService {
  createEnfantFormGroup(enfant?: EnfantFormGroupInput): EnfantFormGroup {
    const enfantRawValue = {
      ...this.getFormDefaults(),
      ...(enfant ?? { id: null }),
    };
    return new FormGroup<EnfantFormGroupContent>({
      id: new FormControl(
        { value: enfantRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nir: new FormControl(enfantRawValue.nir),
      cleNir: new FormControl(enfantRawValue.cleNir),
      numPersonneGaia: new FormControl(enfantRawValue.numPersonneGaia),
      codeAgent: new FormControl(enfantRawValue.codeAgent),
      userCreation: new FormControl(enfantRawValue.userCreation),
      creationDate: new FormControl(enfantRawValue.creationDate),
      userMaj: new FormControl(enfantRawValue.userMaj),
      majDate: new FormControl(enfantRawValue.majDate),
      numMaj: new FormControl(enfantRawValue.numMaj),
      dossier: new FormControl(enfantRawValue.dossier),
    });
  }

  getEnfant(form: EnfantFormGroup): IEnfant | NewEnfant {
    return form.getRawValue() as IEnfant | NewEnfant;
  }

  resetForm(form: EnfantFormGroup, enfant: EnfantFormGroupInput): void {
    const enfantRawValue = { ...this.getFormDefaults(), ...enfant };
    form.reset({
      ...enfantRawValue,
      id: { value: enfantRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): EnfantFormDefaults {
    return {
      id: null,
    };
  }
}
