import dayjs from 'dayjs/esm';

import { ISituation, NewSituation } from './situation.model';

export const sampleWithRequiredData: ISituation = {
  id: 28169,
};

export const sampleWithPartialData: ISituation = {
  id: 2575,
  situationProDebutDate: dayjs('2026-01-06'),
  situationProfinDate: dayjs('2026-01-06'),
  codeSituation: 'once',
  codeAgent: 'vice',
  userCreation: 'gosh eek well-groomed',
  majDate: '17:45:00',
  numMaj: 16764,
};

export const sampleWithFullData: ISituation = {
  id: 6181,
  situationProDebutDate: dayjs('2026-01-06'),
  situationProfinDate: dayjs('2026-01-07'),
  codeSituation: 'monasticism beneath a',
  commentaire: 'how pro',
  codeAgent: 'into dreary',
  userCreation: 'clamp',
  creationDate: '18:38:00',
  userMaj: 'boss',
  majDate: '22:39:00',
  numMaj: 8017,
};

export const sampleWithNewData: NewSituation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
