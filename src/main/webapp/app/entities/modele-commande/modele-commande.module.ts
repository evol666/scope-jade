import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JadeSharedModule } from 'app/shared/shared.module';
import { ModeleCommandeComponent } from './modele-commande.component';
import { ModeleCommandeDetailComponent } from './modele-commande-detail.component';
import { ModeleCommandeUpdateComponent } from './modele-commande-update.component';
import { ModeleCommandeDeleteDialogComponent } from './modele-commande-delete-dialog.component';
import { modeleCommandeRoute } from './modele-commande.route';

@NgModule({
  imports: [JadeSharedModule, RouterModule.forChild(modeleCommandeRoute)],
  declarations: [
    ModeleCommandeComponent,
    ModeleCommandeDetailComponent,
    ModeleCommandeUpdateComponent,
    ModeleCommandeDeleteDialogComponent,
  ],
  entryComponents: [ModeleCommandeDeleteDialogComponent],
})
export class JadeModeleCommandeModule {}
