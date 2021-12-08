import { Component, Input, OnInit } from '@angular/core';
import { Parts, PartsviewService } from 'src/app/services/partsview.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';

@Component({
  selector: 'app-drawing',
  templateUrl: './drawing.component.html',
  styleUrls: ['./drawing.component.scss']
})
export class DrawingComponent implements OnInit {

  @Input() svgDrawing!: SafeResourceUrl;

  posX:number = 0;
  posY:number = 0;

  constructor(private partsviewService: PartsviewService, private sanitizer: DomSanitizer, private dialog: MatDialog) { 
    // injection PartsService
    
    // initialisation base drawing
    this.svgDrawing = this.sanitizer.bypassSecurityTrustResourceUrl(this.partsviewService.svgServer + 'ZSP_postMessage.svg');

    // Subscribe here, this will automatically update "svgDrawing" whenever a change to the subject is made.
    this.partsviewService.activeSvg.subscribe( value => {
      this.svgDrawing = this.sanitizer.bypassSecurityTrustResourceUrl(value);
    });
    
   }

  ngOnInit(): void {
    // initialisation onInit

    const displayMessage =  (evt) => {

      //console.log ('reception du message:', evt.data[2]);
      
      this.posX = evt.data[0];
      this.posY = evt.data[1];
      var id:number = evt.data[2];
      var strTooltip:string = evt.data[3];

      var message;
      if (evt.origin !== "https://svgview.blob.core.windows.net") {
        message = "You are not worthy";
      } else {
        message = "I got " + id + " from " + evt.origin;
        console.log(strTooltip);
        this.partsviewService.showPart(id);
        //this.openDialog();
        
      }
      //console.log(message);
    }

      
    if (window.addEventListener) {
      // For standards-compliant web browsers
      window.addEventListener("message", displayMessage, false);
    }
    
  }

  onResize(event) {
    console.log(event.target.innerWidth);
    var obj = document.getElementById('drawing');
    obj?.setAttribute('width',event.target.innerWidth);
    obj?.setAttribute('height',event.target.innerHeight);
    console.log(obj);
  }

  openDialog() {
     console.log('openDialog');

     const dialogConfig = new MatDialogConfig();
     dialogConfig.position = {
       'top' : this.posX.toString()+'px', 
       'left': this.posY.toString()+'px'
     };

     dialogConfig.hasBackdrop = true;

     this.dialog.open(DialogPartDialog, dialogConfig);

  }

}




@Component ({
  selector: 'dialog-part-dialog',
  templateUrl: 'dialog-part-dialog.html',
})
export class DialogPartDialog {

    part!: Parts;

    constructor (private pvService: PartsviewService) {

      pvService.activePart.subscribe( value => {
         this.part = value;
      })

    } 

}



