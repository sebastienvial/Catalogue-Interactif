package bobst.catalog.compoCat4.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bobst.catalog.compoCat4.models.CatBom;
import bobst.catalog.compoCat4.models.CatItem;
import bobst.catalog.compoCat4.repositories.CatBomRepository;
import bobst.catalog.compoCat4.repositories.CatItemRepository;

@Service
public class CatBomService {

    @Autowired
    private CatBomRepository catBomRepository;

    @Autowired
    private CatItemRepository catItemRepository;
    
    private String[] parents = new String[20];
    private String docName;

    public boolean saveCatBom(CatBom bom) {
        catBomRepository.save(bom);
        return true;
    }

    public boolean saveCatItem(CatItem catItem) {
        catItemRepository.save(catItem);
        return true;
    }

    public List<String> readF(MultipartFile file) {
        BufferedReader br = null;
        String line;
        List<String> lines = new ArrayList<>();

        this.docName = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
        
        try {
            br = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF8"));
            line = br.readLine();
            while(line!=null){
                lines.add(line);
                line = br.readLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        try{
            br.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return lines;

    }

   
    
    public void uploadBom(MultipartFile file) {

            List<String> lines = readF(file);
            for (String line : lines) {
                if (line.contains("|")){
                    convertLine(line);
                }

                               
            }

    }

    public void convertLine(String line) {
        CatBom catBom = new CatBom();
        CatItem catItem = new CatItem();
        
        Integer level = 0;
        this.parents[0] = "M";

        String[] lineSplit = line.split("\\s*\\|\\s*"); // use a regular expression to split and trim result
        try {
            
            level = Integer.parseInt(lineSplit[1]); //exception if not an integer
            this.parents[level] = lineSplit[3];
            
            catBom.setIdDoc(this.docName);
            catBom.setItemParent(this.parents[level-1]);
            catBom.setItemToc(lineSplit[3]);
            this.saveCatBom(catBom);

            catItem.setIdItem(catBom.getItemToc());
            catItem.setDescriptionFr(lineSplit[6]);
            this.saveCatItem(catItem);

        }
        catch (NumberFormatException e) {
            catBom = null;
        }

    
    }

    
}
