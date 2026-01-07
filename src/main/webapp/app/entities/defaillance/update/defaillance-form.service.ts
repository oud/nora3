import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDefaillance, NewDefaillance } from '../defaillance.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDefaillance for edit and NewDefaillanceFormGroupInput for create.
 */
type DefaillanceFormGroupInput = IDefaillance | PartialWithRequiredKeyOf<NewDefaillance>;

type DefaillanceFormDefaults = Pick<NewDefaillance, 'id' | 'flagDetteInitiale'>;

type DefaillanceFormGroupContent = {
  id: FormControl<IDefaillance['id'] | NewDefaillance['id']>;
  moisDefaillance: FormControl<IDefaillance['moisDefaillance']>;
  montantPADue: FormControl<IDefaillance['montantPADue']>;
  montantPAVersee: FormControl<IDefaillance['montantPAVersee']>;
  flagDetteInitiale: FormControl<IDefaillance['flagDetteInitiale']>;
  codeAgent: FormControl<IDefaillance['codeAgent']>;
  userCreation: FormControl<IDefaillance['userCreation']>;
  creationDate: FormControl<IDefaillance['creationDate']>;
  userMaj: FormControl<IDefaillance['userMaj']>;
  majDate: FormControl<IDefaillance['majDate']>;
  numMaj: FormControl<IDefaillance['numMaj']>;
  dossier: FormControl<IDefaillance['dossier']>;
};

export type DefaillanceFormGroup = FormGroup<DefaillanceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DefaillanceFormService {
  createDefaillanceFormGroup(defaillance?: DefaillanceFormGroupInput): DefaillanceFormGroup {
    const defaillanceRawValue = {
      ...this.getFormDefaults(),
      ...(defaillance ?? { id: null }),
    };
    return new FormGroup<DefaillanceFormGroupContent>({
      id: new FormControl(
        { value: defaillanceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      moisDefaillance: new FormControl(defaillanceRawValue.moisDefaillance),
      montantPADue: new FormControl(defaillanceRawValue.montantPADue),
      montantPAVersee: new FormControl(defaillanceRawValue.montantPAVersee),
      flagDetteInitiale: new FormControl(defaillanceRawValue.flagDetteInitiale),
      codeAgent: new FormControl(defaillanceRawValue.codeAgent),
      userCreation: new FormControl(defaillanceRawValue.userCreation),
      creationDate: new FormControl(defaillanceRawValue.creationDate),
      userMaj: new FormControl(defaillanceRawValue.userMaj),
      majDate: new FormControl(defaillanceRawValue.majDate),
      numMaj: new FormControl(defaillanceRawValue.numMaj),
      dossier: new FormControl(defaillanceRawValue.dossier),
    });
  }

  getDefaillance(form: DefaillanceFormGroup): IDefaillance | NewDefaillance {
    return form.getRawValue() as IDefaillance | NewDefaillance;
  }

  resetForm(form: DefaillanceFormGroup, defaillance: DefaillanceFormGroupInput): void {
    const defaillanceRawValue = { ...this.getFormDefaults(), ...defaillance };
    form.reset({
      ...defaillanceRawValue,
      id: { value: defaillanceRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): DefaillanceFormDefaults {
    return {
      id: null,
      flagDetteInitiale: false,
    };
  }
}
