import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'commande',
        loadChildren: () => import('./commande/commande.module').then(m => m.JadeCommandeModule),
      },
      {
        path: 'modele-commande',
        loadChildren: () => import('./modele-commande/modele-commande.module').then(m => m.JadeModeleCommandeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JadeEntityModule {}
