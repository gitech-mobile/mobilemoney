import { Component, OnInit } from '@angular/core';
import {ModalComponent} from '../modal/modal.component';
import {NbDialogService} from '@nebular/theme';
import {KeycloakService} from 'keycloak-angular';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

  constructor(private dialogService: NbDialogService, private keycloakService: KeycloakService) { }

  ngOnInit(): void {
  }
  logOutModal(): void {
    const modalDialog = this.dialogService.open(ModalComponent, {
      context: {
        description: 'Etes vous sure de vouloir se deconnecter ',
        title: 'Deconnexion',
        actionButtonText: 'Deconnexion',
        closeButtonText: 'Annuler'
      }
    });
    modalDialog.componentRef.instance.output.subscribe(
      (save: boolean) => this.keycloakService.logout()
    );
  }
}
