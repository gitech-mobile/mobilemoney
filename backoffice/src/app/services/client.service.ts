import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {LoaderService} from './loader.service';
import {Client} from './data/Client';
import {Observable} from 'rxjs';
import {createRequestOption} from './request-util';
import {map} from 'rxjs/operators';
import * as moment from 'moment';


type EntityResponseType = HttpResponse<Client>;
type EntityArrayResponseType = HttpResponse<Client[]>;

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private resourceUrl = environment.baseUrl + '/donne/client';

  constructor(protected http: HttpClient, private loaderService: LoaderService) { }

  create(client: Client): Observable<EntityResponseType> {
    this.loaderService.startLoading();
    return this.http
      .post<Client>(this.resourceUrl, client, {observe : 'response'});
  }
  update(client: Client): Observable<EntityResponseType> {
    this.loaderService.startLoading();
    return this.http
      .put<Client>(this.resourceUrl, client, {observe : 'response'});
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    let path = this.resourceUrl;
    if(options.has('nom') || options.has('cin') || options.has('numeroCompte') || options.has('prenom'))
      path+='/search';
    return this.http
      .get<Client[]>(`${path}` , {params : options, observe: 'response'})
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(req?: any): Observable<HttpResponse<any>> {
    this.loaderService.startLoading();
    const options = createRequestOption(req);
    return this.http
      .delete<Boolean>(`${this.resourceUrl}`, { params: options,observe: 'response' });
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((client: Client) => {
        client.finCin = client.finCin != null ? moment(client.finCin) : null;
      });
    }
    return res;
  }
}
