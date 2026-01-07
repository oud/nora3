import dayjs from 'dayjs/esm';

import { ICreancier, NewCreancier } from './creancier.model';

export const sampleWithRequiredData: ICreancier = {
  id: 19879,
};

export const sampleWithPartialData: ICreancier = {
  id: 29798,
  cleNir: 21426,
  codeOrganismeCristal: 'phew ugly',
  codeNationalite: 'kiddingly plus carelessly',
  codeAgent: 'emboss',
  creationDate: '20:38:00',
  majDate: '17:00:00',
  numMaj: 25194,
};

export const sampleWithFullData: ICreancier = {
  id: 12862,
  nir: 'stiffen',
  cleNir: 2992,
  numAllocCristal: 23602,
  numPersonneCristal: 26415,
  codeOrganismeCristal: 'bah',
  situationFamilialeDebutDate: dayjs('2026-01-07'),
  codeSituationFamiliale: 'till railway',
  codeNationalite: 'yuck times',
  codeAgent: 'reasoning some whose',
  userCreation: 'gosh uh-huh',
  creationDate: '03:03:00',
  userMaj: 'deflate',
  majDate: '23:16:00',
  numMaj: 18143,
};

export const sampleWithNewData: NewCreancier = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
