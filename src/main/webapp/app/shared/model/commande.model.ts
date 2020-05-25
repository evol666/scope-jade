import { Moment } from 'moment';

export interface ICommande {
  id?: number;
  libelle?: string;
  dtexecution?: Moment;
  statut?: string;
  volume?: number;
  volumeveh?: number;
}

export class Commande implements ICommande {
  constructor(
    public id?: number,
    public libelle?: string,
    public dtexecution?: Moment,
    public statut?: string,
    public volume?: number,
    public volumeveh?: number
  ) {}
}
