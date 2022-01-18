import { Component, OnInit } from '@angular/core';
import { FlatTreeControl } from '@angular/cdk/tree';
import { MatTreeFlatDataSource, MatTreeFlattener } from '@angular/material/tree';
import { PartsviewService, EquipmentNode, EquipmentFlatNode } from 'src/app/services/partsview.service';
import { BomService } from 'src/app/services/bom.service';

 @Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html', 
  styleUrls: ['./navigation.component.scss']
 })

 export class NavigationComponent implements OnInit {
    
    constructor(private partsviewService: PartsviewService, private bomService: BomService) {
      //injection of PartsviewService
      

    }
    
    private _transformer = (node: EquipmentNode, level: number) => {
          return {
          expandable: !!node.children && node.children.length > 0,
          name: node.name,
          parentId: node.parentId,
          id: node.id,
          drawing: !!node.drawing ? node.drawing : 'no drawing',
          level: level,
          };
    }

    treeControl = new FlatTreeControl<EquipmentFlatNode>( node => node.level, node => node.expandable);
    treeFlattener = new MatTreeFlattener( this._transformer, node => node.level, node => node.expandable, node => node.children);

    dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);
 
    activeNode: string = 'S10';


    hasChild = (_: number, node: EquipmentFlatNode) => node.expandable;

    onNaviguer(drawing:string) {
  
      this.partsviewService.showDrawing(drawing);
      this.partsviewService.showParts(drawing);
  
    }

    ngOnInit() {
      
      //this.dataSource.data =this.treeConstruct(this.partsviewService.treeData);

      //this.dataSource.data =this.treeConstruct(this.bomService.getBom());

      this.bomService.getBom().subscribe( (response) => {
        response = response.slice(0,4000);
        console.log ('Retour http: ',response);
        this.dataSource.data = this.treeConstruct(response);
     })

    }

  //constructTree recursively iterates through the tree to create nested tree structure.
  //We only need Id and parentId Columns in the flat data to construct this tree properly.

    treeConstruct(treeData) {
      let constructedTree = [];
      for (let i of treeData) {
        let treeObj = i;
        let assigned = false;
        this.constructTree(constructedTree, treeObj, assigned)
      }
      return constructedTree;
    }

    constructTree(constructedTree, treeObj, assigned) {
    if (treeObj.parentId == 'M') {
          treeObj.children = [];
          constructedTree.push(treeObj);
          return true;
        } else if (treeObj.parentId == constructedTree.id) {
          treeObj.children = [];
          constructedTree.children.push(treeObj);
          return true;
        }
        else {
          if (constructedTree.children != undefined) {
            for (let index = 0; index < constructedTree.children.length; index++) {
              let constructedObj = constructedTree.children[index];
              if (assigned == false) {
                assigned = this.constructTree(constructedObj, treeObj, assigned);
              }
            }
          } else {
            for (let index = 0; index < constructedTree.length; index++) {
              let constructedObj = constructedTree[index];
              if (assigned == false) {
                assigned = this.constructTree(constructedObj, treeObj, assigned);
              }
            }
          }
          return false;
        }
      }


 }

