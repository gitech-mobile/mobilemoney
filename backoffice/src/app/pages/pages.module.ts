import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from '@angular/router';
import {
  NbAccordionModule,
  NbActionsModule,
  NbButtonModule,
  NbCardModule, NbDatepickerModule, NbDialogModule, NbFormFieldModule,
  NbIconModule, NbInputModule,
  NbLayoutModule,
  NbMenuModule,
  NbOverlayModule, NbSelectModule,
  NbSidebarModule, NbTableModule,
  NbThemeModule
} from '@nebular/theme';
import { HomeComponent } from './home/home.component';
import { SidebarMenuComponent } from './sidebar-menu/sidebar-menu.component';
import { PaiementComponent } from './paiement/paiement.component';
import { ListPaiementComponent } from './paiement/list-paiement/list-paiement.component';
import { AlertComponent } from './alert/alert.component';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatInputModule} from '@angular/material/input';
import {MatSortModule} from '@angular/material/sort';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatListModule} from '@angular/material/list';
import { ClientComponent } from './client/client.component';
import { ListClientComponent } from './client/list-client/list-client.component';
import { PartnerComponent } from './partner/partner.component';
import { ListPartnerComponent } from './partner/list-partner/list-partner.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TransactionChartComponent } from './dashboard/transaction-chart/transaction-chart.component';
import {ChartModule} from 'angular2-chartjs';
import { ChartHeaderComponent } from './dashboard/chart-header/chart-header.component';
import {ModalComponent} from './modal/modal.component';
import {FormsModule} from '@angular/forms';
import {NbMomentDateModule} from '@nebular/moment'
import {MatSelectModule} from '@angular/material/select';
import { SearchPaiementComponent } from './paiement/search-paiement/search-paiement.component';

@NgModule({
  declarations: [HomeComponent, SidebarMenuComponent, PaiementComponent, ListPaiementComponent, AlertComponent, ClientComponent, ListClientComponent, PartnerComponent, ListPartnerComponent, DashboardComponent, TransactionChartComponent, ChartHeaderComponent, ModalComponent, SearchPaiementComponent],
  exports: [
    HomeComponent
  ],
  imports: [
    CommonModule,
    RouterModule, // RouterModule.forRoot(routes, { useHash: true }), if this is your app.module
    NbLayoutModule,
    NbSidebarModule.forRoot(), // NbSidebarModule.forRoot(), //if this is your app.module
    NbButtonModule,
    NbThemeModule.forRoot({name: 'cosmic'}),
    NbMenuModule.forRoot(),
    NbIconModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    NbCardModule,
    NbOverlayModule,
    MatListModule,
    NbActionsModule,
    ChartModule,
    NbSelectModule,
    FormsModule,
    NbDatepickerModule,
    NbMomentDateModule,
    NbInputModule,
    NbTableModule,
    NbDialogModule.forRoot(),
    MatSelectModule,
    NbAccordionModule,
    NbFormFieldModule
  ],
  entryComponents: [ModalComponent]
})
export class PagesModule { }
