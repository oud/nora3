import dayjs from 'dayjs/esm';

import { IResultatSolvable, NewResultatSolvable } from './resultat-solvable.model';

export const sampleWithRequiredData: IResultatSolvable = {
  id: 22953,
};

export const sampleWithPartialData: IResultatSolvable = {
  id: 18692,
  moisSolvabiliteDate: dayjs('2026-01-06'),
  codeEtatSolvabilite: 'disrespect after fooey',
};

export const sampleWithFullData: IResultatSolvable = {
  id: 11248,
  moisSolvabiliteDate: dayjs('2026-01-06'),
  codeEtatSolvabilite: 'daily sophisticated whether',
  codeAgent: 'swim from',
  userCreation: 'airbrush questionably yum',
  creationDate: '21:34:00',
  userMaj: 'daily fashion helplessly',
  majDate: '18:55:00',
  numMaj: 17800,
};

export const sampleWithNewData: NewResultatSolvable = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
