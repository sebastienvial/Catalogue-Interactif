import { BehaviorSubject } from "rxjs";

/**
 * Equipment/Machine data with nested structure.
 * Each node has a name and an optional list of children.
 */
export interface EquipmentNode {
    name: string;
    Id: string;
    parentId: string;
    drawing?: string;
    children?: EquipmentNode[];
   }


 /** EquipmentFlatNode with expandable and level information 
*  and caracteristik needed for the naviagtion.
*  The private -transformer need to return all the attributs of the interface below
*/
export interface EquipmentFlatNode {
 expandable: boolean;
 name: string;
 parentId: string;
 Id: string;
 drawing: string;
 level: number;
}

export interface Parts {
    numBobst: string;
    repere: number;
    description: string;
    note: string;
  }

 
export class PartsviewService {

    treeData: EquipmentNode[] = [

        { name: 'GENERAL', Id: 'PCR0380E00', parentId: 'M' },
        { name: 'GENERAL + PLAQUETTES', Id: 'PCR0380S0001', parentId: 'PCR0380E00' },
        { name: 'MASTERFOLD BASE TOUTES VERSIONS ', Id: 'PCR0380T0001001', parentId: 'PCR0380S0001' },
        { name: 'ME00 CAISSE D\'OUTILLAGE', Id: 'BSA00350000CJ', parentId: 'PCR0380T0001001' },
        { name: 'VISSERIE DE RESERVE', Id: 'BSA03220000LF', parentId: 'BSA00350000CJ' },
        { name: 'ECROU MOL BAS M8 X 1', Id: 'BSA1004036100', parentId: 'BSA03220000LF' },
        { name: 'E60', Id: 'E60', parentId: 'M' },
        { name: 'S60', Id: 'S60', parentId: 'E10' },
        { name: 'S10', Id: 'S10', parentId: 'E20' },
        { name: 'S10', Id: 'S10', parentId: 'E30' },
        { name: 'S10', Id: 'S10', parentId: 'E40' },
        { name: 'S10', Id: 'S10', parentId: 'E50' },
        { name: 'S10', Id: 'S10', parentId: 'E00' },
        { name: 'S10', Id: 'S10', parentId: 'E10' },
        { name: 'S10', Id: 'S10', parentId: 'E60' },
        { name: 'TIGE SUPPORT L 350', Id: 'BSA00270000BS', parentId: 'S10', drawing:'ZSP_postMessage001.svg' },
        { name: 'BSA00270000BM', Id: 'BSA00270000BM', parentId: 'S10', drawing:'ZSP_BSA00270000BM_E43_017_-_P01.svg' },
        { name: 'P01', Id: 'BSA00270000BS_P01', parentId: 'BSA00270000BS', drawing:'ZSP_BSA00270000BM_E43_017_-_P01.svg' },
      
      ] 

    
    svgServer: string = "https://svgview.blob.core.windows.net/svg/";

    public activeSvg: BehaviorSubject<string> = new BehaviorSubject<string>("")


      
    PARTS_DATA: Parts[] = [
        {repere: 1, numBobst: 'BSA03500000HU', description: 'Vis sans fin', note: 'Supper blue'},
        {repere: 2, numBobst: 'Helium', description: 'Vis sans fin', note: 'He'},
        {repere: 3, numBobst: 'Lithium', description: 'Vis sans fin', note: 'Li'},
        {repere: 4, numBobst: 'Beryllium', description: 'Vis sans fin', note: 'Be'},
        {repere: 5, numBobst: 'Boron', description: 'Vis sans fin', note: 'B'},
        {repere: 6, numBobst: 'Carbon', description: 'Vis sans fin', note: 'C'},
      ];

      
    public activeListParts: BehaviorSubject<Parts[]> = new BehaviorSubject<Parts[]>(this.PARTS_DATA);
    public activePart: BehaviorSubject<Parts> = new BehaviorSubject<Parts>(this.PARTS_DATA[0]);


    showDrawing(drawing:string) {

        // Active observable
        this.activeSvg.next(this.svgServer + drawing); 
    }


      
    showParts(drawing:string) {

        // Requête Parts appartenant au drawing
        console.log ('Begin showParts');

        let new_PARTS_DATA: Parts[] =  [
            {repere: 1, numBobst: 'BSA03500000HU', description: 'Vis sans fin', note: 'Supper blue'},
            {repere: 2, numBobst: 'BSA03500000AS', description: 'Vis  fin', note: 'He'},
            {repere: 3, numBobst: 'BSA03500000DE', description: 'Vis avec fin', note: 'Li'},
          ];

        // Modification of variable PARTS_DATA
        this.PARTS_DATA = new_PARTS_DATA;  
        // Active Observable   
        this.activeListParts.next(new_PARTS_DATA);  

    }


    showPart(repere: number) {
        for (let part of this.PARTS_DATA) {
            if (part.repere === repere) {
                this.activePart.next(part);
            }

        }
    }

      
}