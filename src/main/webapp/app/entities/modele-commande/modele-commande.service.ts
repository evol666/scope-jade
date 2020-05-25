import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IModeleCommande } from 'app/shared/model/modele-commande.model';

type EntityResponseType = HttpResponse<IModeleCommande>;
type EntityArrayResponseType = HttpResponse<IModeleCommande[]>;

@Injectable({ providedIn: 'root' })
export class ModeleCommandeService {
  public resourceUrl = SERVER_API_URL + 'api/modele-commandes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/modele-commandes';

  constructor(protected http: HttpClient) {}

  create(modeleCommande: IModeleCommande): Observable<EntityResponseType> {
    return this.http.post<IModeleCommande>(this.resourceUrl, modeleCommande, { observe: 'response' });
  }

  update(modeleCommande: IModeleCommande): Observable<EntityResponseType> {
    return this.http.put<IModeleCommande>(this.resourceUrl, modeleCommande, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModeleCommande>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModeleCommande[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModeleCommande[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
