import { ICreancier } from 'app/entities/creancier/creancier.model';
import { IDebiteur } from 'app/entities/debiteur/debiteur.model';

export interface IDossier {
  id: number;
  numDossierNor?: string | null;
  numDossierGaia?: string | null;
  receptionDateNor?: string | null;
  validationDateNor?: string | null;
  codeOrganisme?: string | null;
  codeAgent?: string | null;
  userCreation?: string | null;
  creationDate?: string | null;
  userMaj?: string | null;
  majDate?: string | null;
  numMaj?: number | null;
  debiteur?: Pick<IDebiteur, 'id'> | null;
  creancier?: Pick<ICreancier, 'id'> | null;
}

export type NewDossier = Omit<IDossier, 'id'> & { id: null };
