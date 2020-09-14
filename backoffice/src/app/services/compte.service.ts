import { Injectable } from '@angular/core';

import {Compte} from './data/Compte';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<Compte>;
type EntityArrayResponseType = HttpResponse<Compte[]>;

@Injectable({
  providedIn: 'root'
})
export class CompteService {
  private resourceUrl = environment.baseUrl + '/donne/compte/';

  constructor(protected http: HttpClient) { }

  create(compte: Compte): Observable<EntityResponseType> {
    return this.http
      .post<Compte>(this.resourceUrl, compte, {observe : 'response'});
  }
  update(compte: Compte): Observable<EntityResponseType> {
    return this.http
      .put<Compte>(this.resourceUrl, compte, {observe : 'response'});
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<Compte>(`${this.resourceUrl}/${id}`, {observe : 'response'});
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((compte: Compte) => {
        compte.date = compte.date != null ? moment(compte.date) : null;
      });
    }
    return res;
  }
}
