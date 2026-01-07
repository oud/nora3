import dayjs from 'dayjs/esm';

export interface ICreancier {
  id: number;
  nir?: string | null;
  cleNir?: number | null;
  numAllocCristal?: number | null;
  numPersonneCristal?: number | null;
  codeOrganismeCristal?: string | null;
  situationFamilialeDebutDate?: dayjs.Dayjs | null;
  codeSituationFamiliale?: string | null;
  codeNationalite?: string | null;
  codeAgent?: string | null;
  userCreation?: string | null;
  creationDate?: string | null;
  userMaj?: string | null;
  majDate?: string | null;
  numMaj?: number | null;
}

export type NewCreancier = Omit<ICreancier, 'id'> & { id: null };
