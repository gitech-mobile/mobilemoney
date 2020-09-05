import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Client} from './Client';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ClientService} from '../client.service';
import { without } from 'lodash';
import * as moment from 'moment';

export class ClientDataSource implements DataSource<Client> {

  private clientSubject = new BehaviorSubject<Client[]>([]);
  private loadingSubject  = new BehaviorSubject<boolean>(false);
  private totalItemSubject = new BehaviorSubject<any>(0);
  private search: Client;

  constructor(private clientService: ClientService) {}

  public loading$ = this.loadingSubject.asObservable();
  public totalItem$ = this.totalItemSubject.asObservable();

  connect(collectionViewer: CollectionViewer): Observable<Client[]> {
  return this.clientSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
  this.clientSubject.complete();
  this.loadingSubject.complete();
  this.totalItemSubject.complete();
  }

  loadClient(sortDirection = null, pageIndex = 0, pageSize = 3) {
    this.loadingSubject.next(true);
    let req = {
      page: pageIndex,
      size: pageSize,
      sort: sortDirection,
      nom: this.search?this.search.nom:null,
      prenom: this.search?this.search.prenom:null,
      cin: this.search?this.search.cin:null,
      numeroCompte: this.search?this.search.numeroCompte:null
    };
    this.clientService.query(req)
      .subscribe(
        (res: HttpResponse<Client[]> ) => this.paginateClient(res.body, res.headers)
      )
    ;
  }

  protected paginateClient(clients: Client[], headers: HttpHeaders) {
    this.totalItemSubject.next(parseInt(headers.get('X-Total-Count'), 10));
    this.clientSubject.next(clients);
  }

   addRowClient(client: Client){
    client.finCin = moment();
    this.clientSubject.getValue().push(client);
    this.clientSubject.next(this.clientSubject.getValue());
    this.totalItemSubject.next(this.totalItemSubject.getValue() + 1);
  }

  deleteRow(client: Client){
    this.clientSubject.next(without(this.clientSubject.getValue(),client));
    this.totalItemSubject.next(this.totalItemSubject.getValue() - 1);
  }

  searchChange(client: Client){
    this.search = client;
  }

}
