import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import {NB_DIALOG_CONFIG, NbDialogRef, NbDialogService} from '@nebular/theme';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {
  title:string;
  actionButtonText: string;
  closeButtonText: string;
  description: string;
  @Output() output: EventEmitter<any> = new EventEmitter();
  constructor(
    public dialogRef: NbDialogRef<any>
  ) { }

  ngOnInit() {
  }

  actionFunction() {
    // alert('I am a work in progress');
    this.output.emit(true);
    this.closeModal();
  }

  closeModal() {
    this.dialogRef.close();
  }
}
