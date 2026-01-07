import { IStatut, NewStatut } from './statut.model';

export const sampleWithRequiredData: IStatut = {
  id: 29002,
};

export const sampleWithPartialData: IStatut = {
  id: 15322,
  statutDebutDate: '16:00:00',
  codeStatut: 'cod yahoo',
  motifStatut: 'boldly shrilly',
  codeAgent: 'gut astride',
  creationDate: '07:30:00',
  userMaj: 'the',
  majDate: '07:13:00',
};

export const sampleWithFullData: IStatut = {
  id: 12230,
  statutDebutDate: '00:48:00',
  statutFinDate: '02:56:00',
  codeStatut: 'under pixellate',
  motifStatut: 'if around hmph',
  codeAgent: 'sleepily',
  userCreation: 'once gullible',
  creationDate: '23:42:00',
  userMaj: 'restfully pension',
  majDate: '18:17:00',
  numMaj: 15045,
};

export const sampleWithNewData: NewStatut = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
