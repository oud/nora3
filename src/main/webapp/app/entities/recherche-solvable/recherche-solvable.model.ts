import { IDebiteur } from 'app/entities/debiteur/debiteur.model';

export interface IRechercheSolvable {
  id: number;
  rechercheSolvabiliteDebutDate?: string | null;
  codeAgent?: string | null;
  userCreation?: string | null;
  creationDate?: string | null;
  userMaj?: string | null;
  majDate?: string | null;
  numMaj?: number | null;
  debiteur?: Pick<IDebiteur, 'id'> | null;
}

export type NewRechercheSolvable = Omit<IRechercheSolvable, 'id'> & { id: null };
