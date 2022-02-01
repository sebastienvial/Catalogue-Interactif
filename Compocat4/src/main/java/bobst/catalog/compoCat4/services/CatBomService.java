package bobst.catalog.compoCat4.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import static java.nio.file.StandardCopyOption.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bobst.catalog.compoCat4.models.CatBom;
import bobst.catalog.compoCat4.models.CatDoc;
import bobst.catalog.compoCat4.models.CatItem;
import bobst.catalog.compoCat4.repositories.CatBomRepository;
import bobst.catalog.compoCat4.repositories.CatDocRepository;
import bobst.catalog.compoCat4.repositories.CatItemRepository;

@Service
public class CatBomService {

    @Autowired
    private CatBomRepository catBomRepository;

    @Autowired
    private CatItemRepository catItemRepository;

    @Autowired
    private CatDocRepository catDocRepository;
    
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

    public List<String> readFile(File file) {
        BufferedReader br = null;
        String line;
        List<String> lines = new ArrayList<>();

        //ZNC_BSA03802000066_017_-_2021-09-20
        this.docName = file.getName().substring(0, file.getName().indexOf('.'));
         
        String idDoc = this.docName.substring(0,this.docName.lastIndexOf('_'));
        Date dateEclatement = Date.valueOf(this.docName.substring(this.docName.lastIndexOf('_')+1));
        String otp = "";

        this.docName = idDoc;


        //"UTF8"
        try {
            br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8)); 
            line = br.readLine();
            while(line!=null){
                lines.add(line);
                if ( line.contains("mentOTP")) {
                    otp = line.substring(line.indexOf("-")-3, line.lastIndexOf("-")+4).trim();
                    System.out.println("otp : " + otp);

                }
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

        
        CatDoc catDoc = new CatDoc(idDoc, dateEclatement, otp);
        catDocRepository.save(catDoc);

        return lines;

    }

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
          try(InputStream is = file.getInputStream()) {
            Files.copy(is, convFile.toPath(), REPLACE_EXISTING); 
          }
        return convFile;
      }

    public void uploadBom(File file)   throws IOException {
        List<String> lines = readFile(file);
            for (String line : lines) {
                if (line.contains("|")){
                    convertLine(line);
                }                               
            }
    }

    public void uploadBom(MultipartFile mfile) throws IOException {
        File file = convert(mfile);
        uploadBom(file);
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
