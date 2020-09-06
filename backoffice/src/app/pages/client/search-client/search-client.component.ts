import {Component, EventEmitter, Output} from '@angular/core';
import {Client} from '../../../services/data/Client';

@Component({
  selector: 'app-search-client',
  templateUrl: './search-client.component.html'
})
export class SearchClientComponent  {
  classToggled: boolean  = true;
  @Output() clientChange = new EventEmitter();
  client: Client = new Client();

  toggleField() {
    this.classToggled = !this.classToggled;
    this.client = new Client();
    this.clientChange.emit(null);
  }
  search(client: Client) {
    console.log('search');
    console.log(client);
    this.clientChange.emit(client);
  }
}
