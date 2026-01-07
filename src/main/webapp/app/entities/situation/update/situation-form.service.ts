import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISituation, NewSituation } from '../situation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISituation for edit and NewSituationFormGroupInput for create.
 */
type SituationFormGroupInput = ISituation | PartialWithRequiredKeyOf<NewSituation>;

type SituationFormDefaults = Pick<NewSituation, 'id'>;

type SituationFormGroupContent = {
  id: FormControl<ISituation['id'] | NewSituation['id']>;
  situationProDebutDate: FormControl<ISituation['situationProDebutDate']>;
  situationProfinDate: FormControl<ISituation['situationProfinDate']>;
  codeSituation: FormControl<ISituation['codeSituation']>;
  commentaire: FormControl<ISituation['commentaire']>;
  codeAgent: FormControl<ISituation['codeAgent']>;
  userCreation: FormControl<ISituation['userCreation']>;
  creationDate: FormControl<ISituation['creationDate']>;
  userMaj: FormControl<ISituation['userMaj']>;
  majDate: FormControl<ISituation['majDate']>;
  numMaj: FormControl<ISituation['numMaj']>;
  debiteur: FormControl<ISituation['debiteur']>;
};

export type SituationFormGroup = FormGroup<SituationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SituationFormService {
  createSituationFormGroup(situation?: SituationFormGroupInput): SituationFormGroup {
    const situationRawValue = {
      ...this.getFormDefaults(),
      ...(situation ?? { id: null }),
    };
    return new FormGroup<SituationFormGroupContent>({
      id: new FormControl(
        { value: situationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      situationProDebutDate: new FormControl(situationRawValue.situationProDebutDate),
      situationProfinDate: new FormControl(situationRawValue.situationProfinDate),
      codeSituation: new FormControl(situationRawValue.codeSituation),
      commentaire: new FormControl(situationRawValue.commentaire),
      codeAgent: new FormControl(situationRawValue.codeAgent),
      userCreation: new FormControl(situationRawValue.userCreation),
      creationDate: new FormControl(situationRawValue.creationDate),
      userMaj: new FormControl(situationRawValue.userMaj),
      majDate: new FormControl(situationRawValue.majDate),
      numMaj: new FormControl(situationRawValue.numMaj),
      debiteur: new FormControl(situationRawValue.debiteur),
    });
  }

  getSituation(form: SituationFormGroup): ISituation | NewSituation {
    return form.getRawValue() as ISituation | NewSituation;
  }

  resetForm(form: SituationFormGroup, situation: SituationFormGroupInput): void {
    const situationRawValue = { ...this.getFormDefaults(), ...situation };
    form.reset({
      ...situationRawValue,
      id: { value: situationRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): SituationFormDefaults {
    return {
      id: null,
    };
  }
}
