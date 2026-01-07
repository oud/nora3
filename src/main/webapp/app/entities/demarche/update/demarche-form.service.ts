import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDemarche, NewDemarche } from '../demarche.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDemarche for edit and NewDemarcheFormGroupInput for create.
 */
type DemarcheFormGroupInput = IDemarche | PartialWithRequiredKeyOf<NewDemarche>;

type DemarcheFormDefaults = Pick<NewDemarche, 'id'>;

type DemarcheFormGroupContent = {
  id: FormControl<IDemarche['id'] | NewDemarche['id']>;
  demarcheDate: FormControl<IDemarche['demarcheDate']>;
  numDemarche: FormControl<IDemarche['numDemarche']>;
  codeOrigine: FormControl<IDemarche['codeOrigine']>;
  codeStatut: FormControl<IDemarche['codeStatut']>;
  codeAgent: FormControl<IDemarche['codeAgent']>;
  userCreation: FormControl<IDemarche['userCreation']>;
  creationDate: FormControl<IDemarche['creationDate']>;
  userMaj: FormControl<IDemarche['userMaj']>;
  majDate: FormControl<IDemarche['majDate']>;
  numMaj: FormControl<IDemarche['numMaj']>;
  dossier: FormControl<IDemarche['dossier']>;
};

export type DemarcheFormGroup = FormGroup<DemarcheFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DemarcheFormService {
  createDemarcheFormGroup(demarche?: DemarcheFormGroupInput): DemarcheFormGroup {
    const demarcheRawValue = {
      ...this.getFormDefaults(),
      ...(demarche ?? { id: null }),
    };
    return new FormGroup<DemarcheFormGroupContent>({
      id: new FormControl(
        { value: demarcheRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      demarcheDate: new FormControl(demarcheRawValue.demarcheDate),
      numDemarche: new FormControl(demarcheRawValue.numDemarche),
      codeOrigine: new FormControl(demarcheRawValue.codeOrigine),
      codeStatut: new FormControl(demarcheRawValue.codeStatut),
      codeAgent: new FormControl(demarcheRawValue.codeAgent),
      userCreation: new FormControl(demarcheRawValue.userCreation),
      creationDate: new FormControl(demarcheRawValue.creationDate),
      userMaj: new FormControl(demarcheRawValue.userMaj),
      majDate: new FormControl(demarcheRawValue.majDate),
      numMaj: new FormControl(demarcheRawValue.numMaj),
      dossier: new FormControl(demarcheRawValue.dossier),
    });
  }

  getDemarche(form: DemarcheFormGroup): IDemarche | NewDemarche {
    return form.getRawValue() as IDemarche | NewDemarche;
  }

  resetForm(form: DemarcheFormGroup, demarche: DemarcheFormGroupInput): void {
    const demarcheRawValue = { ...this.getFormDefaults(), ...demarche };
    form.reset({
      ...demarcheRawValue,
      id: { value: demarcheRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): DemarcheFormDefaults {
    return {
      id: null,
    };
  }
}
