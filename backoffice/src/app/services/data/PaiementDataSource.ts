import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Paiement} from './Paiement';
import {BehaviorSubject, Observable} from 'rxjs';
import {PaiementService} from '../paiement.service';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import * as moment from 'moment';
import { without } from 'lodash';


export class PaiementDataSource implements DataSource<Paiement> {

  private paiementSubject = new BehaviorSubject<Paiement[]>([]);
  private loadingSubject  = new BehaviorSubject<boolean>(false);
  private totalItemSubject = new BehaviorSubject<any>(0);
  private search: any;

  constructor(private paiementService: PaiementService) {}

  public loading$ = this.loadingSubject.asObservable();
  public totalItem$ = this.totalItemSubject.asObservable();

  connect(collectionViewer: CollectionViewer): Observable<Paiement[]> {
  return this.paiementSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
  this.paiementSubject.complete();
  this.loadingSubject.complete();
  this.totalItemSubject.complete();
  }

  loadPaiement(sortDirection = null, pageIndex = 0, pageSize = 3) {
    this.loadingSubject.next(true);
    this.paiementService.query({
      page: pageIndex,
      size: pageSize,
      sort: sortDirection,
      reference: this.search?this.search.reference:null,
      dateInf: this.search?this.search.dateInf:null,
      dateSup:this.search?this.search.dateSup:null,
      canal:this.search?this.search.canal:null,
      etatPaiement:this.search?this.search.etat:null
    })
      .subscribe(
        (res: HttpResponse<Paiement[]> ) => this.paginatePaiement(res.body, res.headers)
      )
    ;
  }

  protected paginatePaiement(paiements: Paiement[], headers: HttpHeaders) {
    this.totalItemSubject.next(parseInt(headers.get('X-Total-Count'), 10));
    this.paiementSubject.next(paiements);
  }

  addRowClient(paiement: Paiement){
    paiement.dateupdate = moment();
    this.paiementSubject.getValue().push(paiement);
    this.paiementSubject.next(this.paiementSubject.getValue());
    this.totalItemSubject.next(this.totalItemSubject.getValue() + 1);
  }

  deleteRow(paiement: Paiement){
    this.paiementSubject.next(without(this.paiementSubject.getValue(),paiement));
    this.totalItemSubject.next(this.totalItemSubject.getValue() - 1);
  }

  searchChange(search: any){
    this.search = search;
  }
}
