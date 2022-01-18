import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { PartsviewService } from './partsview.service';

@Injectable({
  providedIn: 'root'
})
export class PartsService implements OnInit {

  constructor(private http: HttpClient, private partsviewService: PartsviewService) { }

  ngOnInit() {
    this.http.get ('http://localhost:8080/parts').subscribe(
      (response) => {
        console.log('response', response);
      }
    )
  }


}
