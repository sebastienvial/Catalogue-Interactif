package bobst.catalog.compoCat4.services;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;


public class WatchCaptureService {

    public static final String DIR_CAPTURE = "D:\\capture\\";
    public static final String SERVER_CAPTURE = "http://localhost:8080/";
 
    public static void run () throws InterruptedException, IllegalStateException, ParserConfigurationException, SAXException {
        try {
                WatchService watchService = FileSystems.getDefault().newWatchService();

                Path pathCapture = Paths.get(DIR_CAPTURE);

                pathCapture.register(watchService, ENTRY_CREATE);
                WatchKey key;

                
                while ((key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Thread.sleep(10000);
                        System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context());
                        
                        String fileName = event.context().toString();
                        URI url = null;
                        if (fileName.contains(".txt")) url = URI.create(SERVER_CAPTURE + "captureBom");
                        if (fileName.contains(".zip")) url = URI.create(SERVER_CAPTURE + "captureZip");
                        if (fileName.contains(".xml")) url = URI.create(SERVER_CAPTURE + "captureXml");

                        File file = new File(DIR_CAPTURE + fileName);   
                        
                        HttpEntity entity = MultipartEntityBuilder.create()
                                            .addPart("file", new FileBody(file))
                                            .build();
                                        
                        
                        HttpPost request = new HttpPost(url);
                        request.setEntity(entity);
                    
                        HttpClient client = HttpClientBuilder.create().build();
                        HttpResponse response = client.execute(request);
                        //System.out.println(response.toString());

                    }
                    key.reset();
                }
            } catch (IOException e) {
                System.err.println(e);
            }

    }

}
