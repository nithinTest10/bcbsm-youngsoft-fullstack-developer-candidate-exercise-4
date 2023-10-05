import { Component, ElementRef, ViewChild } from '@angular/core';
import axios from 'axios';
import { environment } from '../environment';

@Component({
  selector: 'app-fileupload',
  templateUrl: './fileupload.component.html',
  styleUrls: ['./fileupload.component.css']
})
export class FileuploadComponent {
  @ViewChild('filebutton') uploadButtonRef: ElementRef;

  lines: any = [];
  linesR: any = [];

  file : File = null;
  ngOnInit() {}

  changeListener(files) {
    let fileList = (<HTMLInputElement>files.target).files;
    if (fileList && fileList.length > 0) {
       this.file = fileList[0];
    }
  }
  submit() {
    let formParams = new FormData();
    formParams.append('file', this.file)

    let headers =  {
      "Content-Type": 'multipart/form-data',
      "Access-Control-Allow-Origin": '*',
      "Authorization" : sessionStorage.getItem(environment.SESSION_ATTRIBUTE_NAME),
      "userId": sessionStorage.getItem(environment.LOGGEDUSER)
  };
    
    axios.post(environment.backendurl + "/filerepo/save", formParams, {"headers" : headers}).then(response => {
      alert(response.data);
    })
    .catch(error => {
      console.error(error);
      return false;
  });
  }
}