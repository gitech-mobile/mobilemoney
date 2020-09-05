import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {LoaderService} from './loader.service';
import {Observable} from 'rxjs';
import {Partner} from './data/Partner';
import {createRequestOption} from './request-util';

type EntityResponseType = HttpResponse<Partner>;
type EntityArrayResponseType = HttpResponse<Partner[]>;

@Injectable({
  providedIn: 'root'
})
export class PartnerService {
  private resourceUrl = environment.baseUrl + '/donne/partner/';

  constructor(protected http: HttpClient, private loaderService: LoaderService) { }

  create(partner: Partner): Observable<EntityResponseType> {
    this.loaderService.startLoading();
    return this.http
      .post<Partner>(this.resourceUrl, partner, {observe : 'response'});
  }
  update(partner: Partner): Observable<EntityResponseType> {
    this.loaderService.startLoading();
    console.log(partner);
    return this.http
      .put<Partner>(this.resourceUrl, partner, {observe : 'response'});
  }
  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<Partner>(`${this.resourceUrl}${id}`, {observe : 'response'});
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    let path = this.resourceUrl;
    if(options.has('nom') || options.has('etatPartner'))
      path+='/search';

    return this.http
      .get<Partner[]>(`${path}` , {params : options, observe: 'response'})
  }

  delete(id: number): Observable<HttpResponse<any>> {
    this.loaderService.startLoading();
    return this.http
      .delete<any>(`${this.resourceUrl}${id}`, { observe: 'response' });
  }

}
