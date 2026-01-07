import dayjs from 'dayjs/esm';

import { IDossier } from 'app/entities/dossier/dossier.model';

export interface IDemarche {
  id: number;
  demarcheDate?: dayjs.Dayjs | null;
  numDemarche?: string | null;
  codeOrigine?: string | null;
  codeStatut?: string | null;
  codeAgent?: string | null;
  userCreation?: string | null;
  creationDate?: string | null;
  userMaj?: string | null;
  majDate?: string | null;
  numMaj?: number | null;
  dossier?: Pick<IDossier, 'id'> | null;
}

export type NewDemarche = Omit<IDemarche, 'id'> & { id: null };
