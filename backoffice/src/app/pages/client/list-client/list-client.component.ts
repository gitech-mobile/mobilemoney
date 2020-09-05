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

@Component({
  selector: 'app-list-client',
  templateUrl: './list-client.component.html',
  styleUrls: ['./list-client.component.css']
})
export class ListClientComponent implements OnInit, AfterViewInit {
  dataSource: ClientDataSource;
  displayedColumns: string[] = ['action', 'nom', 'prenom', 'numeroCompte', 'cin', 'finCin', 'adresse', 'departement', 'region'];
  selection = new SelectionModel<Client>(false, null);

  constructor(protected clientService: ClientService, private dialogService: NbDialogService) {
    console.log('contructeur');
    this.dataSource = new ClientDataSource(this.clientService);
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  ngOnInit(): void {
    console.log('init');
    this.dataSource.loadClient();
  }

  ngAfterViewInit() {
    this.paginator.page
      .pipe(
        tap(() => this.loadClientPage())
      )
      .subscribe();
  }

  loadClientPage() {
    this.dataSource.loadClient(
      this.sorted(),
      this.paginator.pageIndex,
      this.paginator.pageSize
    );
  }

  sorted() {
    const result = [this.sort.active + ',' + (this.sort.direction)];
    if (this.sort.active !== 'id') {
      result.push('id');
    }
    return result;
  }

  transition() {
    this.loadClientPage();
  }


  updateData(element){
    console.log(element);
  }

  save(client: Client) {
    console.log(client);
    if(client.id)
      this.clientService.update(client).subscribe(
    client => console.log(client),
    error => console.log(error)
      );
    else
      this.clientService.create(client).subscribe(
        clientCreated => {
          this.dataSource.deleteRow(client),
            this.dataSource.addRowClient(clientCreated.body);
        },
        error => console.log(error)
      );
  }

  delete(client: Client) {
    if(client.id == undefined)
      this.dataSource.deleteRow(client);
    else
      this.clientService.delete(client.id).subscribe(
    res =>     this.dataSource.deleteRow(client),
    error => console.log(error)
  );
  }

  deleteModal(client: Client) {
    const modalDialog = this.dialogService.open(ModalComponent,{
      context: {
        description: 'Etes vous sure de vouloir supprimer le client ' +client.nom + '?',
        title: 'Suppression client',
        actionButtonText: 'Supprimer',
        closeButtonText: 'Annuler'
      }
    });
    modalDialog.componentRef.instance.output.subscribe(
      (save: boolean) => this.delete(client)
    );
  }

  saveModal(client: Client) {

    const modalDialog = this.dialogService.open(ModalComponent, {context: {
      description: 'Etes vous sure de vouloir modifier le client ' +client.nom + '?',
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

  ajouter() {
    console.log('ajouter');
    this.dataSource.addRowClient(new Client());
  }


  setSearch($event: any) {
    this.dataSource.searchChange($event);
    this.transition();
  }
}
