import { getLocaleNumberFormat } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { EquipmentNode, PartsviewService } from './partsview.service';

@Injectable({
  providedIn: 'root'
})
export class BomService implements OnInit {
  

  public idDoc: string | undefined;
  public children: string[] |undefined;

  constructor(private http: HttpClient, private partsviewService: PartsviewService) { 
  }

  ngOnInit(): void {
    //Init BomService
   }
    

  getBom(): Observable<EquipmentNode[]> {
    return this.http.get<EquipmentNode[]> ('http://localhost:8080/bom/BSA03802000066');
  }

  getBomDynamic(idDoc: string, idParent: string): Observable<string[]> {
    this.idDoc = idDoc; 
    return this.http.get<string[]> ('http://localhost:8080/bom/' + idDoc + '/' + idParent);
  }

  getChildrenDynamic(node: string): void {
    this.http.get<string[]> ('http://localhost:8080/bom/' + this.idDoc + '/' + node).subscribe((response) => {
      this.children = response;
    });
  }

  
}




