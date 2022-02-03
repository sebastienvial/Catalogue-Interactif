package bobst.catalog.compoCat4.controllers;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xml.sax.SAXException;

import bobst.catalog.compoCat4.models.CatBom;
import bobst.catalog.compoCat4.services.CatBomService;
import bobst.catalog.compoCat4.services.CatE43Service;
import bobst.catalog.compoCat4.services.CatPageContentService;
import bobst.catalog.compoCat4.services.CatPageService;

@Controller
public class CaptureController {

    @Autowired
    private CatBomService catBomService;

    @Autowired
    private CatPageService catPageService;

    @Autowired
    private CatPageContentService catPageContentService;

    @Autowired CatE43Service catE43Service;

    @CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/test")
	public boolean test() {
        CatBom bom = new CatBom("BSA1111","BSA1234","M");
        catBomService.saveCatBom(bom);

        return true;        
    }

    @GetMapping("/capturer")
    public String uploadFile(Model model) {
        return "uploadForm";
    }

    @PostMapping("/captureBom")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        catBomService.uploadBom(file);
        return "redirect:/capture";
    }
    

    @PostMapping("/captureZip")
    public String processZip(@RequestParam("file") MultipartFile file) {
        catPageService.uploadZip(file);
        return "redirect:/capture";
    }

    @PostMapping("/captureXml")
    public String processXml(@RequestParam("file") MultipartFile file) throws ParserConfigurationException, SAXException, IllegalStateException, IOException {
        catPageContentService.uploadXml(file);
        return "redirect:/capture";
    }
    

    @PostMapping("/captureE43")
    public String processZip(@RequestParam("file") MultipartFile file) {
        catE43Service.uploadE43(file);
        return "redirect:/capture";
    }
    

}
