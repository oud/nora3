import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IRechercheSolvable, NewRechercheSolvable } from '../recherche-solvable.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRechercheSolvable for edit and NewRechercheSolvableFormGroupInput for create.
 */
type RechercheSolvableFormGroupInput = IRechercheSolvable | PartialWithRequiredKeyOf<NewRechercheSolvable>;

type RechercheSolvableFormDefaults = Pick<NewRechercheSolvable, 'id'>;

type RechercheSolvableFormGroupContent = {
  id: FormControl<IRechercheSolvable['id'] | NewRechercheSolvable['id']>;
  rechercheSolvabiliteDebutDate: FormControl<IRechercheSolvable['rechercheSolvabiliteDebutDate']>;
  codeAgent: FormControl<IRechercheSolvable['codeAgent']>;
  userCreation: FormControl<IRechercheSolvable['userCreation']>;
  creationDate: FormControl<IRechercheSolvable['creationDate']>;
  userMaj: FormControl<IRechercheSolvable['userMaj']>;
  majDate: FormControl<IRechercheSolvable['majDate']>;
  numMaj: FormControl<IRechercheSolvable['numMaj']>;
  debiteur: FormControl<IRechercheSolvable['debiteur']>;
};

export type RechercheSolvableFormGroup = FormGroup<RechercheSolvableFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RechercheSolvableFormService {
  createRechercheSolvableFormGroup(rechercheSolvable?: RechercheSolvableFormGroupInput): RechercheSolvableFormGroup {
    const rechercheSolvableRawValue = {
      ...this.getFormDefaults(),
      ...(rechercheSolvable ?? { id: null }),
    };
    return new FormGroup<RechercheSolvableFormGroupContent>({
      id: new FormControl(
        { value: rechercheSolvableRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      rechercheSolvabiliteDebutDate: new FormControl(rechercheSolvableRawValue.rechercheSolvabiliteDebutDate),
      codeAgent: new FormControl(rechercheSolvableRawValue.codeAgent),
      userCreation: new FormControl(rechercheSolvableRawValue.userCreation),
      creationDate: new FormControl(rechercheSolvableRawValue.creationDate),
      userMaj: new FormControl(rechercheSolvableRawValue.userMaj),
      majDate: new FormControl(rechercheSolvableRawValue.majDate),
      numMaj: new FormControl(rechercheSolvableRawValue.numMaj),
      debiteur: new FormControl(rechercheSolvableRawValue.debiteur),
    });
  }

  getRechercheSolvable(form: RechercheSolvableFormGroup): IRechercheSolvable | NewRechercheSolvable {
    return form.getRawValue() as IRechercheSolvable | NewRechercheSolvable;
  }

  resetForm(form: RechercheSolvableFormGroup, rechercheSolvable: RechercheSolvableFormGroupInput): void {
    const rechercheSolvableRawValue = { ...this.getFormDefaults(), ...rechercheSolvable };
    form.reset({
      ...rechercheSolvableRawValue,
      id: { value: rechercheSolvableRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): RechercheSolvableFormDefaults {
    return {
      id: null,
    };
  }
}
