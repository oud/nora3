import { IEnfant, NewEnfant } from './enfant.model';

export const sampleWithRequiredData: IEnfant = {
  id: 8607,
};

export const sampleWithPartialData: IEnfant = {
  id: 5296,
  cleNir: 2871,
  numPersonneGaia: 32565,
  codeAgent: 'where eek',
  creationDate: '01:19:00',
  majDate: '20:32:00',
};

export const sampleWithFullData: IEnfant = {
  id: 20475,
  nir: 'engage',
  cleNir: 8163,
  numPersonneGaia: 19082,
  codeAgent: 'supplier',
  userCreation: 'daughter humiliating',
  creationDate: '08:52:00',
  userMaj: 'huzzah bar',
  majDate: '18:59:00',
  numMaj: 24282,
};

export const sampleWithNewData: NewEnfant = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
