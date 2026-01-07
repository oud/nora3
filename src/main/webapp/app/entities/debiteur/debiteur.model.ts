export interface IDebiteur {
  id: number;
  nir?: string | null;
  cleNir?: number | null;
  numAllocCristal?: number | null;
  codeOrganismeCristal?: string | null;
  codeAgent?: string | null;
  userCreation?: string | null;
  creationDate?: string | null;
  userMaj?: string | null;
  majDate?: string | null;
  numMaj?: number | null;
}

export type NewDebiteur = Omit<IDebiteur, 'id'> & { id: null };
