import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {DashboardComponent} from './pages/dashboard/dashboard.component';
import {PaiementComponent} from './pages/paiement/paiement.component';
import {PartnerComponent} from './pages/partner/partner.component';
import {ClientComponent} from './pages/client/client.component';
import {AuthentificationGuard} from './services/guards/authentification.guard';


const routes: Routes = [
  {path: '',
    canActivate: [AuthentificationGuard],
    pathMatch: 'full',
    redirectTo : 'dashboard',
  },
  {path: 'home', component: HomeComponent},
  {path: 'paiement', component: PaiementComponent},
  {path: 'partner', component: PartnerComponent},
  {path: 'client', component: ClientComponent},
  {path: 'dashboard', component: DashboardComponent}
  ];
@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
