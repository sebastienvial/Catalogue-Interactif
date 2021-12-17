import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PartsService implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get ('http://localhost:8080/parts').subscribe(
      (response) => {
        console.log('response', response);
      }
    )
  }


}
