import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ClientDataSource} from '../../../services/data/ClientDataSource';
import {Client} from '../../../services/data/Client';
import {ClientService} from '../../../services/client.service';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {tap} from 'rxjs/operators';
import {SelectionModel} from '@angular/cdk/collections';
import {NbDialogService} from '@nebular/theme';
import {ModalComponent} from '../../modal/modal.component';
import {AlertService} from '../../../services/alert.service';

@Component({
  selector: 'app-list-client',
  templateUrl: './list-client.component.html'
})
export class ListClientComponent implements OnInit, AfterViewInit {
  dataSource: ClientDataSource;
  displayedColumns: string[] = ['action', 'nom', 'prenom', 'numeroCompte', 'cin', 'finCin', 'adresse', 'departement', 'region'];
  selection = new SelectionModel<Client>(false, null);

  constructor(protected clientService: ClientService, private dialogService: NbDialogService, private alertService: AlertService) {
    this.dataSource = new ClientDataSource(this.clientService);
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  ngOnInit(): void {
    this.dataSource.loadClient();
  }

  ngAfterViewInit(): void {
    this.paginator.page
      .pipe(
        tap(() => this.loadClientPage())
      )
      .subscribe();
  }

  loadClientPage(): void {
    this.dataSource.loadClient(
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
    this.loadClientPage();
  }


  updateData(element): void {
    console.log(element);
  }

  save(client: Client): void {
    if (client.id) {
      this.clientService.update(client).subscribe(
        value => this.alertService.success('modification client : ' + value.body.nom, false)
      );
    }
    else {
      this.clientService.create(client).subscribe(
        clientCreated => {
          this.dataSource.deleteRow(client);
          this.dataSource.addRowClient(clientCreated.body);
          this.alertService.success('creation client : ' + clientCreated.body.nom, false);
        }
      );
    }
  }

  delete(client: Client): void {
    if (client.id === undefined) {
      this.dataSource.deleteRow(client);
    }
    else {
      this.clientService.delete({id: client.id}).subscribe(
    res => {
      this.dataSource.deleteRow(client);
      this.alertService.success('client supprime avec succes ' + client.nom);
    }
  );
    }
  }

  deleteModal(client: Client): void {
    const modalDialog = this.dialogService.open(ModalComponent, {
      context: {
        description: 'Etes vous sure de vouloir supprimer le client ' + client.nom + '?',
        title: 'Suppression client',
        actionButtonText: 'Supprimer',
        closeButtonText: 'Annuler'
      }
    });
    modalDialog.componentRef.instance.output.subscribe(
      (save: boolean) => this.delete(client)
    );
  }

  saveModal(client: Client): void {
    const modalDialog = this.dialogService.open(ModalComponent, {context: {
      description: 'Etes vous sure de vouloir modifier le client ' + client.nom + '?',
        title: 'Modification client',
        actionButtonText: 'Modifier',
        closeButtonText: 'Annuler'
    }
    });
    modalDialog.componentRef.instance.output.subscribe(
      (save: boolean) => this.save(client)
    );
    this.selection.deselect(client);
  }

  ajouter(): void{
    this.dataSource.addRowClient(new Client());
  }


  setSearch($event: any): void {
    this.dataSource.searchChange($event);
    this.transition();
  }
}
