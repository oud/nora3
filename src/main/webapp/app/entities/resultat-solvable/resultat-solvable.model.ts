import dayjs from 'dayjs/esm';

import { IRechercheSolvable } from 'app/entities/recherche-solvable/recherche-solvable.model';

export interface IResultatSolvable {
  id: number;
  moisSolvabiliteDate?: dayjs.Dayjs | null;
  codeEtatSolvabilite?: string | null;
  codeAgent?: string | null;
  userCreation?: string | null;
  creationDate?: string | null;
  userMaj?: string | null;
  majDate?: string | null;
  numMaj?: number | null;
  rechercheSolvable?: Pick<IRechercheSolvable, 'id'> | null;
}

export type NewResultatSolvable = Omit<IResultatSolvable, 'id'> & { id: null };
