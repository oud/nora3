import { IDossier } from 'app/entities/dossier/dossier.model';

export interface IEnfant {
  id: number;
  nir?: string | null;
  cleNir?: number | null;
  numPersonneGaia?: number | null;
  codeAgent?: string | null;
  userCreation?: string | null;
  creationDate?: string | null;
  userMaj?: string | null;
  majDate?: string | null;
  numMaj?: number | null;
  dossier?: Pick<IDossier, 'id'> | null;
}

export type NewEnfant = Omit<IEnfant, 'id'> & { id: null };
