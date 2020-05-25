import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommande, Commande } from 'app/shared/model/commande.model';
import { CommandeService } from './commande.service';

@Component({
  selector: 'jhi-commande-update',
  templateUrl: './commande-update.component.html',
})
export class CommandeUpdateComponent implements OnInit {
  isSaving = false;
  dtexecutionDp: any;

  editForm = this.fb.group({
    id: [],
    libelle: [],
    dtexecution: [],
    statut: [],
    volume: [],
    volumeveh: [],
  });

  constructor(protected commandeService: CommandeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commande }) => {
      this.updateForm(commande);
    });
  }

  updateForm(commande: ICommande): void {
    this.editForm.patchValue({
      id: commande.id,
      libelle: commande.libelle,
      dtexecution: commande.dtexecution,
      statut: commande.statut,
      volume: commande.volume,
      volumeveh: commande.volumeveh,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commande = this.createFromForm();
    if (commande.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeService.update(commande));
    } else {
      this.subscribeToSaveResponse(this.commandeService.create(commande));
    }
  }

  private createFromForm(): ICommande {
    return {
      ...new Commande(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      dtexecution: this.editForm.get(['dtexecution'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      volume: this.editForm.get(['volume'])!.value,
      volumeveh: this.editForm.get(['volumeveh'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommande>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
