import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IStatut, NewStatut } from '../statut.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStatut for edit and NewStatutFormGroupInput for create.
 */
type StatutFormGroupInput = IStatut | PartialWithRequiredKeyOf<NewStatut>;

type StatutFormDefaults = Pick<NewStatut, 'id'>;

type StatutFormGroupContent = {
  id: FormControl<IStatut['id'] | NewStatut['id']>;
  statutDebutDate: FormControl<IStatut['statutDebutDate']>;
  statutFinDate: FormControl<IStatut['statutFinDate']>;
  codeStatut: FormControl<IStatut['codeStatut']>;
  motifStatut: FormControl<IStatut['motifStatut']>;
  codeAgent: FormControl<IStatut['codeAgent']>;
  userCreation: FormControl<IStatut['userCreation']>;
  creationDate: FormControl<IStatut['creationDate']>;
  userMaj: FormControl<IStatut['userMaj']>;
  majDate: FormControl<IStatut['majDate']>;
  numMaj: FormControl<IStatut['numMaj']>;
  dossier: FormControl<IStatut['dossier']>;
};

export type StatutFormGroup = FormGroup<StatutFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StatutFormService {
  createStatutFormGroup(statut?: StatutFormGroupInput): StatutFormGroup {
    const statutRawValue = {
      ...this.getFormDefaults(),
      ...(statut ?? { id: null }),
    };
    return new FormGroup<StatutFormGroupContent>({
      id: new FormControl(
        { value: statutRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      statutDebutDate: new FormControl(statutRawValue.statutDebutDate),
      statutFinDate: new FormControl(statutRawValue.statutFinDate),
      codeStatut: new FormControl(statutRawValue.codeStatut),
      motifStatut: new FormControl(statutRawValue.motifStatut),
      codeAgent: new FormControl(statutRawValue.codeAgent),
      userCreation: new FormControl(statutRawValue.userCreation),
      creationDate: new FormControl(statutRawValue.creationDate),
      userMaj: new FormControl(statutRawValue.userMaj),
      majDate: new FormControl(statutRawValue.majDate),
      numMaj: new FormControl(statutRawValue.numMaj),
      dossier: new FormControl(statutRawValue.dossier),
    });
  }

  getStatut(form: StatutFormGroup): IStatut | NewStatut {
    return form.getRawValue() as IStatut | NewStatut;
  }

  resetForm(form: StatutFormGroup, statut: StatutFormGroupInput): void {
    const statutRawValue = { ...this.getFormDefaults(), ...statut };
    form.reset({
      ...statutRawValue,
      id: { value: statutRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): StatutFormDefaults {
    return {
      id: null,
    };
  }
}
