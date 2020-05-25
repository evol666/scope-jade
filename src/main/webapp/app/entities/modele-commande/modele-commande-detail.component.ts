import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModeleCommande } from 'app/shared/model/modele-commande.model';

@Component({
  selector: 'jhi-modele-commande-detail',
  templateUrl: './modele-commande-detail.component.html',
})
export class ModeleCommandeDetailComponent implements OnInit {
  modeleCommande: IModeleCommande | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modeleCommande }) => (this.modeleCommande = modeleCommande));
  }

  previousState(): void {
    window.history.back();
  }
}
