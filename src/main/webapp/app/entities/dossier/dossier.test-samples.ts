import { IDossier, NewDossier } from './dossier.model';

export const sampleWithRequiredData: IDossier = {
  id: 6332,
};

export const sampleWithPartialData: IDossier = {
  id: 17883,
  userMaj: 'exonerate geez',
};

export const sampleWithFullData: IDossier = {
  id: 4880,
  numDossierNor: 'that whether wry',
  numDossierGaia: 'but spiffy',
  receptionDateNor: '21:14:00',
  validationDateNor: '02:30:00',
  codeOrganisme: 'against curry',
  codeAgent: 'warped given',
  userCreation: 'aha',
  creationDate: '06:03:00',
  userMaj: 'low too yellowish',
  majDate: '00:09:00',
  numMaj: 2757,
};

export const sampleWithNewData: NewDossier = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
