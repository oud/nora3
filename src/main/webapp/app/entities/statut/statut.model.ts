import { IDossier } from 'app/entities/dossier/dossier.model';

export interface IStatut {
  id: number;
  statutDebutDate?: string | null;
  statutFinDate?: string | null;
  codeStatut?: string | null;
  motifStatut?: string | null;
  codeAgent?: string | null;
  userCreation?: string | null;
  creationDate?: string | null;
  userMaj?: string | null;
  majDate?: string | null;
  numMaj?: number | null;
  dossier?: Pick<IDossier, 'id'> | null;
}

export type NewStatut = Omit<IStatut, 'id'> & { id: null };
