import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Partner} from './Partner';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {PartnerService} from '../partner.service';
import { without } from 'lodash';

export class PartnerDataSource implements DataSource<Partner> {

  private partnerSubject = new BehaviorSubject<Partner[]>([]);
  private loadingSubject  = new BehaviorSubject<boolean>(false);
  private totalItemSubject = new BehaviorSubject<any>(0);
  private search: any;

  constructor(private partnerService: PartnerService) {}

  public loading$ = this.loadingSubject.asObservable();
  public totalItem$ = this.totalItemSubject.asObservable();

  connect(collectionViewer: CollectionViewer): Observable<Partner[]> {
  return this.partnerSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
  this.partnerSubject.complete();
  this.loadingSubject.complete();
  this.totalItemSubject.complete();
  }

  loadPartner(sortDirection = null, pageIndex = 0, pageSize = 3) {
    this.loadingSubject.next(true);
    this.partnerService.query({
      page: pageIndex,
      size: pageSize,
      sort: sortDirection,
      nom: this.search?this.search.nom:null,
      etatPartner:this.search?this.search.etatPartner:null
    })
      .subscribe(
        (res: HttpResponse<Partner[]> ) => this.paginatePartner(res.body, res.headers)
      );
  }

  protected paginatePartner(partners: Partner[], headers: HttpHeaders) {
    this.totalItemSubject.next(parseInt(headers.get('X-Total-Count'), 10));
    this.partnerSubject.next(partners);
  }

  addRowClient(partner: Partner){
    this.partnerSubject.getValue().push(partner);
    this.partnerSubject.next(this.partnerSubject.getValue());
    this.totalItemSubject.next(this.totalItemSubject.getValue() + 1);
  }

  deleteRow(partner: Partner){
    this.partnerSubject.next(without(this.partnerSubject.getValue(),partner));
    this.totalItemSubject.next(this.totalItemSubject.getValue() - 1);
  }

  searchChange(search: any){
    this.search = search;
  }
}
