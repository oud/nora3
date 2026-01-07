import { IRechercheSolvable, NewRechercheSolvable } from './recherche-solvable.model';

export const sampleWithRequiredData: IRechercheSolvable = {
  id: 27215,
};

export const sampleWithPartialData: IRechercheSolvable = {
  id: 18599,
  rechercheSolvabiliteDebutDate: '19:34:00',
  userCreation: 'pro analogy',
  creationDate: '07:18:00',
  numMaj: 20611,
};

export const sampleWithFullData: IRechercheSolvable = {
  id: 4768,
  rechercheSolvabiliteDebutDate: '05:08:00',
  codeAgent: 'gently bitterly',
  userCreation: 'ha',
  creationDate: '00:47:00',
  userMaj: 'helpfully trolley',
  majDate: '23:40:00',
  numMaj: 5484,
};

export const sampleWithNewData: NewRechercheSolvable = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
