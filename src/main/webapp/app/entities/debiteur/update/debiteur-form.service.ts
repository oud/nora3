import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDebiteur, NewDebiteur } from '../debiteur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDebiteur for edit and NewDebiteurFormGroupInput for create.
 */
type DebiteurFormGroupInput = IDebiteur | PartialWithRequiredKeyOf<NewDebiteur>;

type DebiteurFormDefaults = Pick<NewDebiteur, 'id'>;

type DebiteurFormGroupContent = {
  id: FormControl<IDebiteur['id'] | NewDebiteur['id']>;
  nir: FormControl<IDebiteur['nir']>;
  cleNir: FormControl<IDebiteur['cleNir']>;
  numAllocCristal: FormControl<IDebiteur['numAllocCristal']>;
  codeOrganismeCristal: FormControl<IDebiteur['codeOrganismeCristal']>;
  codeAgent: FormControl<IDebiteur['codeAgent']>;
  userCreation: FormControl<IDebiteur['userCreation']>;
  creationDate: FormControl<IDebiteur['creationDate']>;
  userMaj: FormControl<IDebiteur['userMaj']>;
  majDate: FormControl<IDebiteur['majDate']>;
  numMaj: FormControl<IDebiteur['numMaj']>;
};

export type DebiteurFormGroup = FormGroup<DebiteurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DebiteurFormService {
  createDebiteurFormGroup(debiteur?: DebiteurFormGroupInput): DebiteurFormGroup {
    const debiteurRawValue = {
      ...this.getFormDefaults(),
      ...(debiteur ?? { id: null }),
    };
    return new FormGroup<DebiteurFormGroupContent>({
      id: new FormControl(
        { value: debiteurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nir: new FormControl(debiteurRawValue.nir),
      cleNir: new FormControl(debiteurRawValue.cleNir),
      numAllocCristal: new FormControl(debiteurRawValue.numAllocCristal),
      codeOrganismeCristal: new FormControl(debiteurRawValue.codeOrganismeCristal),
      codeAgent: new FormControl(debiteurRawValue.codeAgent),
      userCreation: new FormControl(debiteurRawValue.userCreation),
      creationDate: new FormControl(debiteurRawValue.creationDate),
      userMaj: new FormControl(debiteurRawValue.userMaj),
      majDate: new FormControl(debiteurRawValue.majDate),
      numMaj: new FormControl(debiteurRawValue.numMaj),
    });
  }

  getDebiteur(form: DebiteurFormGroup): IDebiteur | NewDebiteur {
    return form.getRawValue() as IDebiteur | NewDebiteur;
  }

  resetForm(form: DebiteurFormGroup, debiteur: DebiteurFormGroupInput): void {
    const debiteurRawValue = { ...this.getFormDefaults(), ...debiteur };
    form.reset({
      ...debiteurRawValue,
      id: { value: debiteurRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): DebiteurFormDefaults {
    return {
      id: null,
    };
  }
}
