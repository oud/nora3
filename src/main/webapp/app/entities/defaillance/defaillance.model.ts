import dayjs from 'dayjs/esm';

import { IDossier } from 'app/entities/dossier/dossier.model';

export interface IDefaillance {
  id: number;
  moisDefaillance?: dayjs.Dayjs | null;
  montantPADue?: number | null;
  montantPAVersee?: number | null;
  flagDetteInitiale?: boolean | null;
  codeAgent?: string | null;
  userCreation?: string | null;
  creationDate?: string | null;
  userMaj?: string | null;
  majDate?: string | null;
  numMaj?: number | null;
  dossier?: Pick<IDossier, 'id'> | null;
}

export type NewDefaillance = Omit<IDefaillance, 'id'> & { id: null };
