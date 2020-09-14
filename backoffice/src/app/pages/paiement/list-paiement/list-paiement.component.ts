import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {PaiementService} from '../../../services/paiement.service';

import {Paiement} from '../../../services/data/Paiement';
import {MatPaginator} from '@angular/material/paginator';
import {PaiementDataSource} from '../../../services/data/PaiementDataSource';
import {tap} from 'rxjs/operators';
import {MatSort} from '@angular/material/sort';
import {ModalComponent} from '../../modal/modal.component';
import {NbDialogService} from '@nebular/theme';
import {SelectionModel} from '@angular/cdk/collections';
import {EtatPaiement} from '../../../services/Enum/EtatPaiement';
import {Canal} from '../../../services/Enum/Canal';
import {AlertService} from "../../../services/alert.service";

@Component({
  selector: 'app-list-paiement',
  templateUrl: './list-paiement.component.html'
})
export class ListPaiementComponent implements AfterViewInit, OnInit  {
  dataSource: PaiementDataSource;
  selection = new SelectionModel<Paiement>(false, null);

  EtatPaiementList = Object.keys(EtatPaiement);
  CanalList        = Object.keys(Canal);
  paiements: Paiement[];
  displayedColumns: string[] = ['action', 'montant', 'reference', 'client', 'partner' , 'dateupdate', 'canal', 'etat'];
  constructor(protected paiementService: PaiementService, private dialogService: NbDialogService, private alertService: AlertService) {
  }


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  classToggled = true;

  ngOnInit(): void {
    this.dataSource = new PaiementDataSource(this.paiementService);
    this.dataSource.loadPaiement();

  }

  ngAfterViewInit(): void {
    this.paginator.page
      .pipe(
        tap(() => this.loadPaiementPage())
      )
      .subscribe();
  }

  loadPaiementPage(): void {
    this.dataSource.loadPaiement(
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
    this.loadPaiementPage();
  }

  save(paiement: Paiement): void {
    if (paiement.id) {
      this.paiementService.update(paiement).subscribe(
        value => this.alertService.success('paiement mise a jour ' + paiement.reference )
      );
    }
    else {
      this.paiementService.create(paiement).subscribe(
        clientCreated => {
          this.dataSource.deleteRow(paiement);
          this.dataSource.addRowClient(clientCreated.body);
          this.alertService.success('paiement mise a jour ' + paiement.reference );
        });
    }
  }

  delete(paiement: Paiement): void{
    if (paiement.id === undefined) {
      this.dataSource.deleteRow(paiement);
    }
    else {
      this.paiementService.delete(paiement.id).subscribe(
        res => {
          this.dataSource.deleteRow(paiement);
          this.alertService.success('paiement supprime ' + paiement.reference );
        }
      );
    }
  }

  deleteModal(paiement: Paiement): void {
    const modalDialog = this.dialogService.open(ModalComponent, {
      context: {
        description: 'Etes vous sure de vouloir supprimer le paiement ' + paiement.montant + 'du partenaire  ' + paiement.partner.nom,
        title: 'Suppression client',
        actionButtonText: 'Supprimer',
        closeButtonText: 'Annuler'
      }
    });
    modalDialog.componentRef.instance.output.subscribe(
      (save: boolean) => this.delete(paiement)
    );
  }

  saveModal(paiement: Paiement): void {

    const modalDialog = this.dialogService.open(ModalComponent, {context: {
        description: 'Etes vous sure de vouloir modifier le paiement ' + paiement.id + '?',
        title: 'Modification paiement',
        actionButtonText: 'Modifier',
        closeButtonText: 'Annuler'
      }
    });


    modalDialog.componentRef.instance.output.subscribe(
      (save: boolean) => this.save(paiement)
    );
    this.selection.deselect(paiement);
  }

  toggleField(): void{
    this.classToggled = !this.classToggled;
  }

  setSearch($event: any): void {
    this.dataSource.searchChange($event);
    this.transition();
  }
}
