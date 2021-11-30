import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import models.Cat_Structure;
import com.opencsv.bean.CsvToBeanBuilder;




public class CompoCat4 {
	
	private static final Logger log;

	static {
	    System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
	    log =Logger.getLogger(CompoCat4.class.getName());
	}

	public static void main(String[] args) throws IllegalStateException, IOException, SQLException {
		// CompoCat4 Main 
        importDataStructure();
        System.out.println(structures.size());
        System.out.println(structures.get(10).getIdItem());
        
        log.info("Loading application properties");
        Properties properties = new Properties();
        properties.load(CompoCat4.class.getClassLoader().getResourceAsStream("application.properties"));
        
        log.info("Connecting to the database");
        Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        log.info("Database connection test: " + connection.getCatalog());
        
        for (int i = 0; i < 100; i++) {
          insertData(structures.get(i), connection);
        }
        
        log.info("Closing database connection");
        connection.close();
        
	}
	
	private static void insertData(Cat_Structure str, Connection connection) throws SQLException {
	    log.info("Insert data");
	    PreparedStatement insertStatement = connection
	            .prepareStatement("INSERT INTO CAT_STRUCTURE (ID_DOC, ID_PAGE, ID_TOC, PARENT) VALUES (?, ?, ?, ?);");

	    insertStatement.setString(1, str.getIdDoc());
	    insertStatement.setString(2, str.getIdPage());
	    insertStatement.setString(3, str.getIdItem());
	    insertStatement.setString(4, str.getIdParent());
	    insertStatement.executeUpdate();
	}
	
	public static ArrayList<Cat_Structure> structures = new ArrayList<Cat_Structure>();
	
	public static void traiterLine(String line) {
		
		String level;
		String idItem;
		String idParent;	
		
		
		if (line.contains("|")) {
			
			//System.out.println(line);
			String[] rows = line.split("\\|");
			
			level = rows[1].trim();
			if (StringUtils.isNumeric(level)) {
				idItem = rows[3].trim();
				niv[Integer.parseInt(level)] = idItem;
				idParent = niv[Integer.parseInt(level)-1];
				//System.out.println(idItem + " " + idParent);
				structures.add(new Cat_Structure("", idItem, "", idParent));				
			}
	
		}
			
	}
	
	public static String[] niv = new String[10] ;
	
	public static void importDataStructure() throws FileNotFoundException {
		
		FileInputStream fis = new FileInputStream("D:\\Travai_Master\\Catalogue-Interactif\\Data\\BSA03802000066.txt");
		Scanner sc = new Scanner(fis);		
		
		niv[0] = "M";
				
		while (sc.hasNextLine()) {
			
			traiterLine(sc.nextLine());
			
		}
		
		sc.close();
	}
	
	
	
	
	public static void importDataStructure1() throws IllegalStateException, IOException {
		
		String fileNameStructureEquipment =  "D:\\Travai_Master\\Catalogue-Interactif\\Data\\BSA03802000066.txt";
        List<Cat_Structure> structures = new CsvToBeanBuilder(new FileReader(fileNameStructureEquipment,StandardCharsets.UTF_8))
                .withType(Cat_Structure.class)
                .withSeparator('|')
                .build()
                .parse();
        
        Integer cpt=0;
        
        System.out.println(structures.size());
        List<Cat_Structure> structuresClean = new ArrayList<Cat_Structure>();
        
        for (Cat_Structure struct: structures) {
        	
        	System.out.println();
        	
        }
	}
        	
	
	
	

}
