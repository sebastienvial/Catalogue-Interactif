package bobst.catalog.compoCat4.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import bobst.catalog.compoCat4.models.CatBom;

public class DataConvert {

    //Read file bom , for each line - convert and write to db

    public static void readBomFile(String nameBomFile) {

        try {
            File bomFile = new File("nameBomFile");
            Scanner bomFileReader = new Scanner(bomFile);
            while (bomFileReader.hasNextLine()) {
                String line = bomFileReader.nextLine();
                convertLine(line);
            }

            bomFileReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

    }

    public static CatBom convertLine(String line) {
        CatBom catBom = new CatBom();
        String[] lineSplit;


        if (line.contains("|")){
            lineSplit = line.split("\\s*|\\s*"); //use a regular expression to split and trim result
            System.out.println(lineSplit);


        }
        return catBom;
    }

    public static boolean writeDb(CatBom CatBom) {

        return true;

    }

    
}
