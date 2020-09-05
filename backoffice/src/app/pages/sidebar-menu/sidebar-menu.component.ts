import { Component, OnInit } from '@angular/core';
import {NbMenuItem} from '@nebular/theme';

@Component({
  selector: 'app-sidebar-menu',
  templateUrl: './sidebar-menu.component.html',
  styleUrls: ['./sidebar-menu.component.css']
})
export class SidebarMenuComponent implements OnInit {

  constructor() { }
  items: NbMenuItem[] = [
    {
      title: 'Client',
      expanded: true,
      icon: 'people-outline',
      children: [
        {
          title: 'Client',
          link: '/client', // goes into angular `routerLink
          icon: 'arrow-right'
        }
      ],
    },
    {
      title: 'Operation',
      icon: 'shopping-cart-outline',
      children: [
        {
          title: 'paiements',
          icon: 'arrow-right',
          link: '/paiement'
        }
        ]
    },
    {
      title: 'Partenaire',
      icon: 'settings-2-outline',
      children: [
        {
          title: 'partenaire',
          icon: 'arrow-right',
          link: '/partner'
        }
      ]
    }
    ,
    {
      title: 'Activite',
      icon: 'activity-outline',
      children: [
        {
          title: 'dashboard',
          icon: 'arrow-right',
          link: '/dashboard'
        }
      ]
    }
  ];

  ngOnInit(): void {
  }

}
