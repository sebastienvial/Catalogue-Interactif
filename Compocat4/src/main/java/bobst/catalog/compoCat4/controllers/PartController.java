package bobst.catalog.compoCat4.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Node;

import bobst.catalog.compoCat4.models.NodeBom;
import bobst.catalog.compoCat4.models.Part;

@RestController
public class PartController {
	
	/*
	 * {repere: 1, numBobst: 'BSA03500000HU', description: 'Vis sans fin', note:
	 * 'Supper blue'}, {repere: 2, numBobst: 'Helium', description: 'Vis sans fin',
	 * note: 'He'}, {repere: 3, numBobst: 'Lithium', description: 'Vis sans fin',
	 * note: 'Li'}, {repere: 4, numBobst: 'Beryllium', description: 'Vis sans fin',
	 * note: 'Be'}, {repere: 5, numBobst: 'Boron', description: 'Vis sans fin',
	 * note: 'B'}, {repere: 6, numBobst: 'Carbon', description: 'Vis sans fin',
	 * note: 'C'}
	 */
	
	@GetMapping("/parts")
	public ArrayList<Part> getPart() {
		
		ArrayList<Part> partList = new ArrayList<Part>();
		
		partList.add(new Part("1", "BSA03500000HU", "Vis sans fin", ""));
		partList.add(new Part("2", "BSA9876", "Autre description", "une note1"));
		
		return partList;
	}
	
	@GetMapping ("/bom")
	public ArrayList<NodeBom> getBom() {
		
		ArrayList<NodeBom> bom = new ArrayList<NodeBom>();
		
		bom.add(new NodeBom("GENERAL", "PCR0380E00", "M", ""));
		bom.add(new NodeBom("GENERAL + PLAQUETTES", "PCR0380S0001", "PCR0380E00", "ZSP_postMessage001.svg"));
		
		return bom;
	}

}
