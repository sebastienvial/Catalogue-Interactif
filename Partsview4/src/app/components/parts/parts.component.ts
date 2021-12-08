import { Component } from '@angular/core';
import { Parts, PartsviewService } from 'src/app/services/partsview.service';


@Component({
  selector: 'app-parts',
  templateUrl: './parts.component.html',
  styleUrls: ['./parts.component.scss']
})
export class PartsComponent {

  displayedColumns: string[] = ['repere', 'numBobst',  'description', 'note'];
  dataSource!: Parts[];
  part!: Parts;

  constructor(private partsviewService: PartsviewService) {
    // Injection partsviewService
    //this.dataSource = partsviewService.PARTS_DATA;
    this.partsviewService.activeListParts.subscribe( value => {
      this.dataSource = value;
    });

    this.partsviewService.activePart.subscribe( value => {
      this.part = value;
    });

  }

}
