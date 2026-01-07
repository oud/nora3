import dayjs from 'dayjs/esm';

import { IDefaillance, NewDefaillance } from './defaillance.model';

export const sampleWithRequiredData: IDefaillance = {
  id: 4591,
};

export const sampleWithPartialData: IDefaillance = {
  id: 24041,
  montantPADue: 25689.21,
  montantPAVersee: 30112.51,
  flagDetteInitiale: false,
  creationDate: '10:29:00',
};

export const sampleWithFullData: IDefaillance = {
  id: 11145,
  moisDefaillance: dayjs('2026-01-06'),
  montantPADue: 29986.29,
  montantPAVersee: 30121.1,
  flagDetteInitiale: true,
  codeAgent: 'blindly climb',
  userCreation: 'draft',
  creationDate: '04:35:00',
  userMaj: 'powerfully',
  majDate: '20:13:00',
  numMaj: 22696,
};

export const sampleWithNewData: NewDefaillance = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
