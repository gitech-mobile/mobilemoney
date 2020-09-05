import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {LoaderService} from './loader.service';
import {Paiement} from './data/Paiement';
import {Observable} from 'rxjs';
import {createRequestOption} from './request-util';
import {map} from 'rxjs/operators';
import * as moment from 'moment';
import {EtatPaiement} from './Enum/EtatPaiement';
import {Moment} from 'moment';

type EntityResponseType = HttpResponse<Paiement>;
type EntityResponseTypeObject= HttpResponse<Object>;
type EntityArrayResponseType = HttpResponse<Paiement[]>;

@Injectable({
  providedIn: 'root'
})
export class PaiementService {
  private resourceUrl = environment.baseUrl + '/donne/paiement';

  constructor(protected http: HttpClient, private loaderService: LoaderService) { }

  create(paiement: Paiement): Observable<EntityResponseType> {
    this.loaderService.startLoading();
    return this.http
      .post<Paiement>(this.resourceUrl, paiement, {observe : 'response'});
  }

  update(paiement: Paiement): Observable<EntityResponseType> {
    this.loaderService.startLoading();
    return this.http
      .put<Paiement>(this.resourceUrl, paiement, {observe : 'response'});
  }

  getStatYear(etat: EtatPaiement, year: number): Observable<EntityResponseTypeObject> {
    const options = createRequestOption({
      year: year,
      etatPaiement: etat.toString()
    });
    return this.http
      .get<Object>(`${this.resourceUrl}/statyear`, {params : options, observe : 'response'});
  }

  getStatWeek(etat: EtatPaiement, date: Moment): Observable<EntityResponseTypeObject> {
    const options = createRequestOption({
      date:date.format('YYYY-MM-DD'),
      etatPaiement: etat.toString()
    });
    return this.http
      .get<Object>(`${this.resourceUrl}/statweek`, {params : options, observe : 'response'});
  }
  getStatMonth(etat: EtatPaiement, date: Moment): Observable<EntityResponseTypeObject> {
    const options = createRequestOption({
      date:date.format('YYYY-MM-DD'),
      etatPaiement: etat.toString()
    });
    return this.http
      .get<Object>(`${this.resourceUrl}/statmonth`, {params: options,observe : 'response'});
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    let path = this.resourceUrl;
    if(options.has('reference') ||options.has('dateInf') ||options.has('dateSup') ||options.has('canal') ||options.has('etatPaiement'))
      path+='/search';
    return this.http
      .get<Paiement[]>(`${path}` , {params : options, observe: 'response'})
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    this.loaderService.startLoading();
    const options = createRequestOption({
      id: id
    });
    return this.http
      .delete<any>(`${this.resourceUrl}`, {params : options, observe: 'response' });
  }


  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((paiement: Paiement) => {
        paiement.dateupdate = paiement.dateupdate != null ? moment(paiement.dateupdate) : null;
      });
    }
    return res;
  }
}
