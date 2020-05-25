import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IModeleCommande, ModeleCommande } from 'app/shared/model/modele-commande.model';
import { ModeleCommandeService } from './modele-commande.service';

@Component({
  selector: 'jhi-modele-commande-update',
  templateUrl: './modele-commande-update.component.html',
})
export class ModeleCommandeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [],
  });

  constructor(protected modeleCommandeService: ModeleCommandeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modeleCommande }) => {
      this.updateForm(modeleCommande);
    });
  }

  updateForm(modeleCommande: IModeleCommande): void {
    this.editForm.patchValue({
      id: modeleCommande.id,
      libelle: modeleCommande.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modeleCommande = this.createFromForm();
    if (modeleCommande.id !== undefined) {
      this.subscribeToSaveResponse(this.modeleCommandeService.update(modeleCommande));
    } else {
      this.subscribeToSaveResponse(this.modeleCommandeService.create(modeleCommande));
    }
  }

  private createFromForm(): IModeleCommande {
    return {
      ...new ModeleCommande(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModeleCommande>>): void {
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
