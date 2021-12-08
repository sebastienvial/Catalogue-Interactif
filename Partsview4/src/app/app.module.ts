import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { DrawingComponent } from './components/drawing/drawing.component';
import { PartsComponent } from './components/parts/parts.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatTreeModule } from '@angular/material/tree';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatTableModule } from '@angular/material/table';
import { MatDialogModule } from '@angular/material/dialog'

import { AngularSplitModule } from 'angular-split';
import { PartsviewService } from './services/partsview.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    DrawingComponent,
    PartsComponent
  ],
  imports: [
    MatDialogModule,
    AngularSplitModule,
    MatTableModule,
    MatIconModule,
    MatTreeModule,
    MatButtonModule,
    MatButtonToggleModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [
    PartsviewService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
