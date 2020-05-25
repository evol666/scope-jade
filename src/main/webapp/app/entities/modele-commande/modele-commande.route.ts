import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IModeleCommande, ModeleCommande } from 'app/shared/model/modele-commande.model';
import { ModeleCommandeService } from './modele-commande.service';
import { ModeleCommandeComponent } from './modele-commande.component';
import { ModeleCommandeDetailComponent } from './modele-commande-detail.component';
import { ModeleCommandeUpdateComponent } from './modele-commande-update.component';

@Injectable({ providedIn: 'root' })
export class ModeleCommandeResolve implements Resolve<IModeleCommande> {
  constructor(private service: ModeleCommandeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModeleCommande> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((modeleCommande: HttpResponse<ModeleCommande>) => {
          if (modeleCommande.body) {
            return of(modeleCommande.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ModeleCommande());
  }
}

export const modeleCommandeRoute: Routes = [
  {
    path: '',
    component: ModeleCommandeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ModeleCommandes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModeleCommandeDetailComponent,
    resolve: {
      modeleCommande: ModeleCommandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ModeleCommandes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModeleCommandeUpdateComponent,
    resolve: {
      modeleCommande: ModeleCommandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ModeleCommandes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModeleCommandeUpdateComponent,
    resolve: {
      modeleCommande: ModeleCommandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ModeleCommandes',
    },
    canActivate: [UserRouteAccessService],
  },
];
