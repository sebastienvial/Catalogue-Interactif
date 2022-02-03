package bobst.catalog.compoCat4.services;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bobst.catalog.compoCat4.models.CatE43;
import bobst.catalog.compoCat4.repositories.CatE43Repository;

@Service
public class CatE43Service {

    @Autowired
    CatBomService catBomService;

    @Autowired
    CatE43Repository catE43Repository;

    public void uploadE43(MultipartFile file) throws IOException {
        //traitement du fichier liste des E43 exitants
        //Lire le fichier et renseigner la table cat_E43 
        List<String> lines = catBomService.readFile(catBomService.convert(file));
        
        String idE43 = "";
        String idItem = "";
        Date dateD = Date.valueOf("2022-01-01");
        Date dateF = Date.valueOf("2022-01-01");
        String otp = "";

        for (String line : lines) {
                
            CatE43 catE43 = new CatE43(idE43, idItem, dateD, dateF, otp);

        }


    }
    
}
