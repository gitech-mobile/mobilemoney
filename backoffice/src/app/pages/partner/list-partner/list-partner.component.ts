import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {PartnerDataSource} from '../../../services/data/PartnerDataSource';
import {Partner} from '../../../services/data/Partner';
import {PartnerService} from '../../../services/partner.service';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {tap} from 'rxjs/operators';
import {ModalComponent} from '../../modal/modal.component';
import {SelectionModel} from '@angular/cdk/collections';
import {NbDialogService} from '@nebular/theme';
import {EtatPartner} from '../../../services/Enum/EtatPartner';
import {Photo} from '../../../services/data/Photo';
import {Compte} from '../../../services/data/Compte';
import {AlertService} from "../../../services/alert.service";

@Component({
  selector: 'app-list-partner',
  templateUrl: './list-partner.component.html'
})
export class ListPartnerComponent implements OnInit, AfterViewInit {

  dataSource: PartnerDataSource;
  selection = new SelectionModel<Partner>(false, null);
  EtatPartnerList = Object.keys(EtatPartner);
  displayedColumns: string[] = ['action', 'logo', 'nom', 'identifiant', 'email', 'adresse', 'etat', 'solde', 'contrat'];
  constructor(protected partnerService: PartnerService, private dialogService: NbDialogService, private alertService: AlertService) {
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  ngOnInit(): void {
    this.dataSource = new PartnerDataSource(this.partnerService);
    this.dataSource.loadPartner();
  }


  changeFile(file): any {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = error => reject(error);
    });
  }

  uploadFile(event, partner: Partner): void {
    if (partner.logo == null) {
      partner.logo = new Photo();
    }

    if (event.target.value) {
      const file = event.target.files[0];
      const type = file.type;
      this.changeFile(file).then((base64: string): any => {
        const arr = base64.split(',', 2);
        partner.logo.photo = arr[1];
        partner.logo.contentType = arr[0] ;
      });
    }
  }

  uploadContrat(event, partner: Partner): void {
    console.log('change');
    if (partner.contrat == null){
      partner.contrat = new Photo();
    }

    if (event.target.value) {
      const file = event.target.files[0];
      const type = file.type;
      this.changeFile(file).then((base64: string): any => {
        const arr = base64.split(',', 2);
        partner.contrat.photo = arr[1];
        partner.contrat.contentType = arr[0] ;
      });
    }
  }

  ngAfterViewInit(): void {
    this.paginator.page
      .pipe(
        tap(() => this.loadPartnerPage())
      )
      .subscribe();
  }

  loadPartnerPage(): void {
    this.dataSource.loadPartner(
      this.sorted(),
      this.paginator.pageIndex,
      this.paginator.pageSize
    );
  }

  sorted(): any {
    const result = [this.sort.active + ',' + (this.sort.direction)];
    if (this.sort.active !== 'id') {
      result.push('id');
    }
    return result;
  }

  transition(): void {
    this.loadPartnerPage();
  }

  save(partner: Partner): void {
    if (partner.id) {
      this.partnerService.update(partner).subscribe(
        value => this.alertService.success('partenaire modifie : ' + value.body.nom)
      );
    }
    else {
      this.partnerService.create(partner).subscribe(
        clientCreated => {
          this.dataSource.deleteRow(partner);
          this.dataSource.addRowClient(clientCreated.body);
          this.alertService.success('partenaire cree ' + clientCreated.body.nom);
        }
      );
    }
  }

  delete(partner: Partner): void {
    if (partner.id === undefined) {
      this.dataSource.deleteRow(partner);
    }
    else {
      this.partnerService.delete({ id: partner.id }).subscribe(
        res => {
          this.dataSource.deleteRow(partner);
          this.alertService.success('partenaire supprimer : ' + partner.nom);
        }
      );
    }
  }

  deleteModal(partner: Partner): void {
    const modalDialog = this.dialogService.open(ModalComponent, {
      context: {
        description: 'Etes vous sure de vouloir supprimer le partner ' + partner.nom ,
        title: 'Suppression Partenaire',
        actionButtonText: 'Supprimer',
        closeButtonText: 'Annuler'
      }
    });
    modalDialog.componentRef.instance.output.subscribe(
      (save: boolean) => this.delete(partner)
    );
  }

  saveModal(partner: Partner): void {

    const modalDialog = this.dialogService.open(ModalComponent, {context: {
        description: 'Etes vous sure de vouloir modifier le partner ' + partner.id + '?',
        title: 'Modification partenaire',
        actionButtonText: 'Modifier',
        closeButtonText: 'Annuler'
      }
    });


    modalDialog.componentRef.instance.output.subscribe(
      (save: boolean) => this.save(partner)
    );
    this.selection.deselect(partner);
  }

  ajouter(): void {
    console.log('ajouter');
    const partner = new Partner();
    partner.logo = new Photo();
    partner.contrat = new Photo();
    partner.compte = new Compte();
    this.dataSource.addRowClient(partner);
  }

  setSearch($event: any): void {
    this.dataSource.searchChange($event);
    this.transition();
  }
}
