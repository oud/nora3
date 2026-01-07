import { IDebiteur, NewDebiteur } from './debiteur.model';

export const sampleWithRequiredData: IDebiteur = {
  id: 20654,
};

export const sampleWithPartialData: IDebiteur = {
  id: 20682,
  cleNir: 14213,
  numAllocCristal: 26263,
  codeAgent: 'raw wide-eyed',
  userMaj: 'pry phew',
  majDate: '02:01:00',
};

export const sampleWithFullData: IDebiteur = {
  id: 22503,
  nir: 'yuck',
  cleNir: 29121,
  numAllocCristal: 27566,
  codeOrganismeCristal: 'jovially',
  codeAgent: 'phew rejoin darling',
  userCreation: 'replacement',
  creationDate: '21:08:00',
  userMaj: 'hopelessly',
  majDate: '16:11:00',
  numMaj: 27987,
};

export const sampleWithNewData: NewDebiteur = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
