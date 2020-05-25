import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IModeleCommande } from 'app/shared/model/modele-commande.model';
import { ModeleCommandeService } from './modele-commande.service';
import { ModeleCommandeDeleteDialogComponent } from './modele-commande-delete-dialog.component';

@Component({
  selector: 'jhi-modele-commande',
  templateUrl: './modele-commande.component.html',
})
export class ModeleCommandeComponent implements OnInit, OnDestroy {
  modeleCommandes?: IModeleCommande[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected modeleCommandeService: ModeleCommandeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.modeleCommandeService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IModeleCommande[]>) => (this.modeleCommandes = res.body || []));
      return;
    }

    this.modeleCommandeService.query().subscribe((res: HttpResponse<IModeleCommande[]>) => (this.modeleCommandes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInModeleCommandes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IModeleCommande): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInModeleCommandes(): void {
    this.eventSubscriber = this.eventManager.subscribe('modeleCommandeListModification', () => this.loadAll());
  }

  delete(modeleCommande: IModeleCommande): void {
    const modalRef = this.modalService.open(ModeleCommandeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.modeleCommande = modeleCommande;
  }
}
