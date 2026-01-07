import dayjs from 'dayjs/esm';

import { IDebiteur } from 'app/entities/debiteur/debiteur.model';

export interface ISituation {
  id: number;
  situationProDebutDate?: dayjs.Dayjs | null;
  situationProfinDate?: dayjs.Dayjs | null;
  codeSituation?: string | null;
  commentaire?: string | null;
  codeAgent?: string | null;
  userCreation?: string | null;
  creationDate?: string | null;
  userMaj?: string | null;
  majDate?: string | null;
  numMaj?: number | null;
  debiteur?: Pick<IDebiteur, 'id'> | null;
}

export type NewSituation = Omit<ISituation, 'id'> & { id: null };
