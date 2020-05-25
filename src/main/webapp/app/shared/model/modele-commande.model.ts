export interface IModeleCommande {
  id?: number;
  libelle?: string;
}

export class ModeleCommande implements IModeleCommande {
  constructor(public id?: number, public libelle?: string) {}
}
