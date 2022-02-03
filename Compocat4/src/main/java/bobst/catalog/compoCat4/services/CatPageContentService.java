package bobst.catalog.compoCat4.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bobst.catalog.compoCat4.models.CatItem;
import bobst.catalog.compoCat4.models.CatPageContent;
import bobst.catalog.compoCat4.repositories.CatItemRepository;
import bobst.catalog.compoCat4.repositories.CatPageContentRepository;

@Service
public class CatPageContentService {

    @Autowired
    CatPageContentRepository catPageContentRepository;

    @Autowired
    CatItemRepository catItemRepository;

    public void uploadXml(MultipartFile file) throws ParserConfigurationException, SAXException, IllegalStateException, IOException {

        String idE43 = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
        File xmlFile = new File("src/main/resources/" + file.getOriginalFilename());

        try (OutputStream os = new FileOutputStream(xmlFile)) {
            os.write(file.getBytes());
        }
        
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            // get <items>
            NodeList list = doc.getElementsByTagName("items");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // get items's attributes
                    String pageNumber = element.getAttribute("PageNumber");
                    String idPage = idE43 + "_" + pageNumber;

                    //Upload table catPage with info itemPage = fileName + pageNumber
                    //Upload table catItem with Reference and DescriptionLocal and description English

                    //get <link>
                    NodeList listLink = element.getElementsByTagName("link");
                    for (int j = 0; j < listLink.getLength(); j++){
                        Node link = listLink.item(j);
                        if(link.getNodeType() == Node.ELEMENT_NODE) {
                            Element linkElement = (Element) link;
                            String repere = linkElement.getElementsByTagName("BomId").item(0).getTextContent();
                            String idItem = linkElement.getElementsByTagName("Id").item(0).getTextContent();
                            String contentType = "L";
                            CatPageContent newPageContentItem = new CatPageContent(idPage, contentType, idItem, repere);
                            catPageContentRepository.save(newPageContentItem);
                        }
                    } 

                    // get <item>
                    NodeList listItem = element.getElementsByTagName("item");
                    for(int i = 0; i < listItem.getLength(); i++) {
                        Node item = listItem.item(i);                    

                        if (item.getNodeType() == Node.ELEMENT_NODE){
                            Element itemElement = (Element) item;
                            String repere = itemElement.getElementsByTagName("BomId").item(0).getTextContent();
                            String criticality = itemElement.getElementsByTagName("Criticality").item(0).getTextContent();
                            //String itemMaterialGroup = itemElement.getElementsByTagName("ItemMaterialGroup").item(0).getTextContent();
                            String idItem = itemElement.getElementsByTagName("ItemNumber").item(0).getTextContent();
                            String itemRevision = itemElement.getElementsByTagName("ItemRevision").item(0).getTextContent();
                            String itemState = itemElement.getElementsByTagName("ItemState").item(0).getTextContent();
                            //String itemUnitOfMeasure = itemElement.getElementsByTagName("ItemUnitOfMeasure").item(0).getTextContent();
                            //String material = itemElement.getElementsByTagName("Material").item(0).getTextContent();
                            //String partType = itemElement.getElementsByTagName("PartType").item(0).getTextContent();
                            //String preconisationCode = itemElement.getElementsByTagName("PreconisationCode").item(0).getTextContent();
                            String descriptionDe = itemElement.getElementsByTagName("ShortDescriptionDe").item(0).getTextContent();
                            String descriptionEn = itemElement.getElementsByTagName("ShortDescriptionEn").item(0).getTextContent();
                            String descriptionEs = itemElement.getElementsByTagName("ShortDescriptionEs").item(0).getTextContent();
                            String descriptionFr = itemElement.getElementsByTagName("ShortDescriptionFr").item(0).getTextContent();
                            String descriptionIt = itemElement.getElementsByTagName("ShortDescriptionIt").item(0).getTextContent();
                            String descriptionPt = itemElement.getElementsByTagName("ShortDescriptionPt").item(0).getTextContent();
                            //String smarteamClassName = itemElement.getElementsByTagName("SmarteamClassName").item(0).getTextContent();

                            //Print screen of the data
                            //System.out.println(idItem + " - " + descriptionFr);
                            
                            //upload table catContentPage with itemId, 
                            //catPageContentRepository.save(new CatPageContent())

                            CatItem newItem = new CatItem(idItem, criticality, itemRevision, itemState, descriptionFr, descriptionEn, descriptionDe, descriptionIt, descriptionPt);
                            catItemRepository.save(newItem);

                            //signature : CatPageContent(String idPage, String contentType, String idItem, String repere)
                            String contentType = "I";
                            
                            if (catPageContentRepository.exists(idPage, contentType, idItem, repere) == 0 ) {
                                CatPageContent newPageContentItem = new CatPageContent(idPage, contentType, idItem, repere);
                                catPageContentRepository.save(newPageContentItem);
                            }
                            
                        }
                        
                    } 

                }
            }

        } catch ( IOException e) {
            e.printStackTrace();
        }





    }




    


    
}
