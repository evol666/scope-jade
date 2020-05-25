import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModeleCommande } from 'app/shared/model/modele-commande.model';
import { ModeleCommandeService } from './modele-commande.service';

@Component({
  templateUrl: './modele-commande-delete-dialog.component.html',
})
export class ModeleCommandeDeleteDialogComponent {
  modeleCommande?: IModeleCommande;

  constructor(
    protected modeleCommandeService: ModeleCommandeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modeleCommandeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('modeleCommandeListModification');
      this.activeModal.close();
    });
  }
}
