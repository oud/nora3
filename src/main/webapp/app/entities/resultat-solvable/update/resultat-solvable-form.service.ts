import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IResultatSolvable, NewResultatSolvable } from '../resultat-solvable.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResultatSolvable for edit and NewResultatSolvableFormGroupInput for create.
 */
type ResultatSolvableFormGroupInput = IResultatSolvable | PartialWithRequiredKeyOf<NewResultatSolvable>;

type ResultatSolvableFormDefaults = Pick<NewResultatSolvable, 'id'>;

type ResultatSolvableFormGroupContent = {
  id: FormControl<IResultatSolvable['id'] | NewResultatSolvable['id']>;
  moisSolvabiliteDate: FormControl<IResultatSolvable['moisSolvabiliteDate']>;
  codeEtatSolvabilite: FormControl<IResultatSolvable['codeEtatSolvabilite']>;
  codeAgent: FormControl<IResultatSolvable['codeAgent']>;
  userCreation: FormControl<IResultatSolvable['userCreation']>;
  creationDate: FormControl<IResultatSolvable['creationDate']>;
  userMaj: FormControl<IResultatSolvable['userMaj']>;
  majDate: FormControl<IResultatSolvable['majDate']>;
  numMaj: FormControl<IResultatSolvable['numMaj']>;
  rechercheSolvable: FormControl<IResultatSolvable['rechercheSolvable']>;
};

export type ResultatSolvableFormGroup = FormGroup<ResultatSolvableFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResultatSolvableFormService {
  createResultatSolvableFormGroup(resultatSolvable?: ResultatSolvableFormGroupInput): ResultatSolvableFormGroup {
    const resultatSolvableRawValue = {
      ...this.getFormDefaults(),
      ...(resultatSolvable ?? { id: null }),
    };
    return new FormGroup<ResultatSolvableFormGroupContent>({
      id: new FormControl(
        { value: resultatSolvableRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      moisSolvabiliteDate: new FormControl(resultatSolvableRawValue.moisSolvabiliteDate),
      codeEtatSolvabilite: new FormControl(resultatSolvableRawValue.codeEtatSolvabilite),
      codeAgent: new FormControl(resultatSolvableRawValue.codeAgent),
      userCreation: new FormControl(resultatSolvableRawValue.userCreation),
      creationDate: new FormControl(resultatSolvableRawValue.creationDate),
      userMaj: new FormControl(resultatSolvableRawValue.userMaj),
      majDate: new FormControl(resultatSolvableRawValue.majDate),
      numMaj: new FormControl(resultatSolvableRawValue.numMaj),
      rechercheSolvable: new FormControl(resultatSolvableRawValue.rechercheSolvable),
    });
  }

  getResultatSolvable(form: ResultatSolvableFormGroup): IResultatSolvable | NewResultatSolvable {
    return form.getRawValue() as IResultatSolvable | NewResultatSolvable;
  }

  resetForm(form: ResultatSolvableFormGroup, resultatSolvable: ResultatSolvableFormGroupInput): void {
    const resultatSolvableRawValue = { ...this.getFormDefaults(), ...resultatSolvable };
    form.reset({
      ...resultatSolvableRawValue,
      id: { value: resultatSolvableRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): ResultatSolvableFormDefaults {
    return {
      id: null,
    };
  }
}
