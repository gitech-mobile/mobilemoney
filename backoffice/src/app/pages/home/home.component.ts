import {Component, OnDestroy, OnInit} from '@angular/core';
import {ModalComponent} from '../modal/modal.component';
import {NbDialogService, NbMenuService, NbSidebarService} from '@nebular/theme';
import {KeycloakService} from 'keycloak-angular';
import {MediaMatcher} from '@angular/cdk/layout';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit, OnDestroy {

  constructor(private dialogService: NbDialogService, private menuService: NbMenuService,
              private  media: MediaMatcher, private keycloakService: KeycloakService, private sidebarService: NbSidebarService) {
    this.menuService.onItemSelect().subscribe((event: {tag: string, item: any}) => {
      if ( media.matchMedia('(max-width: 900px)').matches) {
      this.sidebarService.collapse('menu-sidebar');
      }
    });
  }

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
  toggle(): void {
    this.sidebarService.toggle(false, 'menu-sidebar');
  }

  ngOnDestroy(): void {
  }
}
