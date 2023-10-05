import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { environment } from '../environment';
import axios from 'axios';

@Component({
  selector: 'app-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent implements OnInit, OnDestroy {

  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject<any>();
  fileItems: Array<any>;


  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  ngOnInit(): void {
    this.getDocuments();
  }

  getDocuments() {
    axios.get(environment.backendurl + "/filerepo/getall").then(response => {
      // Handle successful response
      console.log(response.data);
      this.fileItems = response.data;
      this.dtTrigger.next(response.data);
      return true;
    })
      .catch(error => {
        console.error(error);
        return false;
      });
  }

  download(id, fileName) {

    let headers =  {
      "Content-Type": 'application/json',
      "Access-Control-Allow-Origin": '*',
      "Authorization" : sessionStorage.getItem(environment.SESSION_ATTRIBUTE_NAME),
      "userId": sessionStorage.getItem(environment.LOGGEDUSER)
  };

    axios.get(environment.backendurl + "/filerepo/download?id="+ id, {"headers": headers}).then(response => {
      const temp = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement('a');
    link.href = temp;
    link.setAttribute('download', fileName); //or any other extension
    document.body.appendChild(link);
    link.click();
    })
      .catch(error => {
        console.error(error);
        return false;
      });
  }

  delete(id) {

    this.dtTrigger.unsubscribe();
    
    let headers =  {
      "Content-Type": 'application/json',
      "Access-Control-Allow-Origin": '*',
      "Authorization" : sessionStorage.getItem(environment.SESSION_ATTRIBUTE_NAME),
      "userId": sessionStorage.getItem(environment.LOGGEDUSER)
  };

    axios.post(environment.backendurl + "/filerepo/delete?id=" + id, '', {"headers": headers}).then(response => {
      alert(response.data);
      this.getDocuments();
    })
      .catch(error => {
        console.error(error);
        return false;
      });
  }
}
