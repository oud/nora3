import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICreancier, NewCreancier } from '../creancier.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICreancier for edit and NewCreancierFormGroupInput for create.
 */
type CreancierFormGroupInput = ICreancier | PartialWithRequiredKeyOf<NewCreancier>;

type CreancierFormDefaults = Pick<NewCreancier, 'id'>;

type CreancierFormGroupContent = {
  id: FormControl<ICreancier['id'] | NewCreancier['id']>;
  nir: FormControl<ICreancier['nir']>;
  cleNir: FormControl<ICreancier['cleNir']>;
  numAllocCristal: FormControl<ICreancier['numAllocCristal']>;
  numPersonneCristal: FormControl<ICreancier['numPersonneCristal']>;
  codeOrganismeCristal: FormControl<ICreancier['codeOrganismeCristal']>;
  situationFamilialeDebutDate: FormControl<ICreancier['situationFamilialeDebutDate']>;
  codeSituationFamiliale: FormControl<ICreancier['codeSituationFamiliale']>;
  codeNationalite: FormControl<ICreancier['codeNationalite']>;
  codeAgent: FormControl<ICreancier['codeAgent']>;
  userCreation: FormControl<ICreancier['userCreation']>;
  creationDate: FormControl<ICreancier['creationDate']>;
  userMaj: FormControl<ICreancier['userMaj']>;
  majDate: FormControl<ICreancier['majDate']>;
  numMaj: FormControl<ICreancier['numMaj']>;
};

export type CreancierFormGroup = FormGroup<CreancierFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CreancierFormService {
  createCreancierFormGroup(creancier?: CreancierFormGroupInput): CreancierFormGroup {
    const creancierRawValue = {
      ...this.getFormDefaults(),
      ...(creancier ?? { id: null }),
    };
    return new FormGroup<CreancierFormGroupContent>({
      id: new FormControl(
        { value: creancierRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nir: new FormControl(creancierRawValue.nir),
      cleNir: new FormControl(creancierRawValue.cleNir),
      numAllocCristal: new FormControl(creancierRawValue.numAllocCristal),
      numPersonneCristal: new FormControl(creancierRawValue.numPersonneCristal),
      codeOrganismeCristal: new FormControl(creancierRawValue.codeOrganismeCristal),
      situationFamilialeDebutDate: new FormControl(creancierRawValue.situationFamilialeDebutDate),
      codeSituationFamiliale: new FormControl(creancierRawValue.codeSituationFamiliale),
      codeNationalite: new FormControl(creancierRawValue.codeNationalite),
      codeAgent: new FormControl(creancierRawValue.codeAgent),
      userCreation: new FormControl(creancierRawValue.userCreation),
      creationDate: new FormControl(creancierRawValue.creationDate),
      userMaj: new FormControl(creancierRawValue.userMaj),
      majDate: new FormControl(creancierRawValue.majDate),
      numMaj: new FormControl(creancierRawValue.numMaj),
    });
  }

  getCreancier(form: CreancierFormGroup): ICreancier | NewCreancier {
    return form.getRawValue() as ICreancier | NewCreancier;
  }

  resetForm(form: CreancierFormGroup, creancier: CreancierFormGroupInput): void {
    const creancierRawValue = { ...this.getFormDefaults(), ...creancier };
    form.reset({
      ...creancierRawValue,
      id: { value: creancierRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): CreancierFormDefaults {
    return {
      id: null,
    };
  }
}
