import dayjs from 'dayjs/esm';

import { IDemarche, NewDemarche } from './demarche.model';

export const sampleWithRequiredData: IDemarche = {
  id: 10840,
};

export const sampleWithPartialData: IDemarche = {
  id: 21889,
  userCreation: 'dramatic',
  creationDate: '07:17:00',
  userMaj: 'horst ectoderm yuck',
  majDate: '19:38:00',
  numMaj: 22863,
};

export const sampleWithFullData: IDemarche = {
  id: 31885,
  demarcheDate: dayjs('2026-01-07'),
  numDemarche: 'scowl pupil',
  codeOrigine: 'beneath cosset geez',
  codeStatut: 'plastic tentacle',
  codeAgent: 'abandoned mosh',
  userCreation: 'masculinize briskly',
  creationDate: '00:07:00',
  userMaj: 'charm yuck tag',
  majDate: '19:30:00',
  numMaj: 11502,
};

export const sampleWithNewData: NewDemarche = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
