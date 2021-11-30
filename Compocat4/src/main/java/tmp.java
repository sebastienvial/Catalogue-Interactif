
public class tmp {
	
	package importData;

	import com.opencsv.CSVReader;
	import com.opencsv.bean.CsvToBeanBuilder;

	import models.JobPosition;

	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.UnsupportedEncodingException;
	import java.nio.charset.StandardCharsets;
	import java.sql.Connection;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.text.SimpleDateFormat;
	import java.time.YearMonth;
	import java.time.format.DateTimeFormatter;
	import java.util.ArrayList;
	import java.util.Date;
	import java.util.List;

	public class ImportData {
		
		public static String PERIOD = "2021-10";
		public static String DIRPATH = "D:\\HomeOffice\\KPI\\Test New KPI\\2021\\"; 

		public static void main(String[] args) throws IllegalStateException, IOException, SQLException {
			
			//importDataUserLMS();
			
			//importDataUserSJ();
			
			importDataActvity();
			
			//importDataUserWFP();
			
			//importDataPositionWFP();
			
			
			
	        
		} 
		
		
		public static void importDataPositionWFP() throws IllegalStateException, IOException {
			
			String fileNamePositionWFP = DIRPATH + "Consolidation_WFP_Position_" + PERIOD + ".csv";
	        List<JobPosition> jobPositions = new CsvToBeanBuilder(new FileReader(fileNamePositionWFP,StandardCharsets.UTF_8))
	                .withType(JobPosition.class)
	                .withSeparator(';')
	                .build()
	                .parse();
	        
	        Integer cpt=0;
	        boolean ajout;
	        
	        System.out.println(jobPositions.size());
	        List<JobPosition> jobPositionsClean = new ArrayList<JobPosition>();
	        
	        for (JobPosition job: jobPositions) {
	        	
	        	cpt++;
	        	if (cpt==100) {
	        		return;
	        	}
	        	
	        	System.out.println(job.toString());
	        	
	        	ajout = true;
	        	for (JobPosition jobClean: jobPositionsClean) {
	        		if ( jobClean.getEntryDatePosition().equals(job.getEntryDatePosition()) &&
	        			 jobClean.getExitDatePosition().equals(job.getExitDatePosition()) &&
	        			 jobClean.getRoleName().equals(job.getRoleName()) && 
	        			 jobClean.getRoleRate().equals(job.getRoleRate()) &&
	        			 jobClean.getCl().equals(job.getCl()) &&
	        			 jobClean.getStatus().equals(job.getStatus()) &&
	        			 jobClean.getContract().equals(job.getContract()) &&
	        			 jobClean.getPl().equals(job.getPl()))  {
	        			
	        			ajout = false;
	        			break;
	        		}        		
	        	}
	        	if (ajout) {
	        		jobPositionsClean.add(job);
	        		System.out.println("----------New Entry" + job.toString());
	        	}
	        	
	        	
	        }
			
		}
		
		
		
		
		
		public static String getPeriodNow() {
			 SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
			 SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
			
			 Date act = new Date();
			 return sdfYear.format(act) + "-" + sdfMonth.format(act);		 
		 }
		
		
		public static void importDataUserWFP() throws IllegalStateException, IOException {
			String fileNameUserWFP = DIRPATH + "Consolidation_WFP_" + PERIOD + ".csv";
	        List<UserWFP> usersWFP = new CsvToBeanBuilder(new FileReader(fileNameUserWFP,StandardCharsets.UTF_8))
	                .withType(UserWFP.class)
	                .withSkipLines(1) 
	                .build()
	                .parse();
	        
	        Integer cpt=0;
	        
	        for (UserWFP userWFP: usersWFP) {
	        	userWFP.prepareToBD();
	        	cpt++;
	        	userWFP.setSite(userWFP.getSite().trim());
	        	userWFP.setFirstName(userWFP.getFirstName().trim());
	        	userWFP.setLastName(userWFP.getLastName().trim());
	        	
	        	if ( !((userWFP.getFirstName().isEmpty()) | (userWFP.getLastName().isEmpty())) ) {
	        		
			        	//System.out.println(userWFP.toString());
			        	User userBD = getBDUser(userWFP.getSite(),userWFP.getFirstName(),userWFP.getLastName());
			        	if (userBD == null) {
			        		//check bd user binding
			        		String presenceBDUserBinding = getEmailBDUserBinding(userWFP);
			        		if (presenceBDUserBinding.equals("notin")) { //userWFP.getSite(),userWFP.getFirstName(),userWFP.getLastName())) {
			        			//user non identifié, création dans la BD USER BINDING avec email vide, message sur system out
			        			System.out.println(cpt + " user non identifié :" + userWFP.toString());
			        			addToBDUserBinding(userWFP.getSite(),userWFP.getFirstName(),userWFP.getLastName());
			        			
			        		} else {
			        			if (presenceBDUserBinding.contains("@")) {
			        				userBD = getBDUserByEmail(presenceBDUserBinding);
			        				userBD.setPrimaryPl(userWFP.getPrimarPL());
			        				userBD.setInWfp(userWFP.getInWfp());
			        				userBD.setTitleWfp(userWFP.getTitleWFP());
			        				userBD.setControlling(userWFP.getControlling());
			        				userBD.setExpertise(userWFP.getExpertise());
			                		updateBDUserFromWFP(userBD);
			                		System.out.println(cpt + " user updated :" + userWFP.toString());
				        			
			        			} else {

			                		System.out.println(cpt + " user compléter l'email dans BD binding :" + userWFP.toString());
			        			}
			        			
			        		}
			        	} else {
			        		//user identifier, utiliser les données de WFP
			        		userBD.setPrimaryPl(userWFP.getPrimarPL());
	        				userBD.setInWfp(userWFP.getInWfp());
	        				userBD.setTitleWfp(userWFP.getTitleWFP());
	        				userBD.setControlling(userWFP.getControlling());
	        				userBD.setExpertise(userWFP.getExpertise());
			        		updateBDUserFromWFP(userBD);
	                		System.out.println(cpt + " user updated :" + userWFP.toString());
			        		
			        	}
	        	}
	        }

			
		}
		
		public static void importDataUserLMS() throws IllegalStateException, IOException, SQLException {
			
			String fileNameUserLMS = DIRPATH + "Knowledge_KPI_members_ALL_" + PERIOD + ".csv";
	        List<UserLMS> usersLMS = new CsvToBeanBuilder(new FileReader(fileNameUserLMS,StandardCharsets.UTF_8))
	                .withType(UserLMS.class)
	                .withSkipLines(1) 
	                .build()
	                .parse();

	        Integer cpt=0;
	        ArrayList<User> users = getBDUsers();
	        
	        for (UserLMS userLMS: usersLMS) {
	        	//System.out.println(userLMS.toString());
	        	userLMS.prepareToBD();
	        	cpt++;
	        	User userBD;
	        	if ( (userLMS.getEmail().contains("@")) & 
	           		 !(userLMS.getEmail().contains("NO.MAIL")) & 
	        		 !(userLMS.getEmail().contains("NOREPLY")) & 
	        		 !(userLMS.getEmail().contains("XX.XX")) ) {
	        		
	        		//userBD = getBDUserByEmail(userLMS.getEmail());
	        		userBD = getBDUserByEmailAndLocationId(userLMS.getEmail(), userLMS.getLocationId(), users);
	        	
		        	if (userBD == null) {
		        		addToBDUser(userLMS);
		        		System.out.println (cpt + " new user :" + userLMS.toString());
		        	} else {
		        		// Test si besoin d'updater le user
		        		
		        		if ( (userBD.getStatus_global().equals(userLMS.getStatus_global())) &
		        			 (userBD.getFirstName().equals(userLMS.getFirstName())) &
		        			 (userBD.getLastName().equals(userLMS.getLastName())) &
		        			 (userBD.getEmail().equals(userLMS.getEmail().toUpperCase())) &
		        			 (userBD.getUserName().equals(userLMS.getUserName())) &
		        			 (userBD.getPosition().equals(userLMS.getPosition())) &
		        			 (userBD.getPositionId().equals(userLMS.getPositionId())) &
		        			 (userBD.getLocationId().equals(userLMS.getLocationId())) ) {
		        			//System.out.println (cpt + " user ok :" + userLMS.toString());
		        		} else {
		        			//updateBDUser;
			        		System.out.println (cpt + " update user :" + userLMS.toString());
			        		updateBDUserFromLMS(userLMS);
		        			
		        		}
		        			
		        	}
	        	}
	        }
			
		}
		
		
		public static void importDataUserSJ() throws IllegalStateException, IOException {
			
			String fileNameUserSJ = DIRPATH + "Company User Detail Report_" + PERIOD + ".csv";
			
	        List<UserSJ> usersSJ = new CsvToBeanBuilder(new FileReader(fileNameUserSJ,StandardCharsets.UTF_8))
	                .withType(UserSJ.class)
	                .withSkipLines(5)
	                .build()
	                .parse();

	        Integer cpt=0;
	           
	        for (UserSJ userSJ: usersSJ) {
	        	userSJ.prepareToBD();
	        	cpt++;
	        	
	        	if (userSJ.getEmail().contains("@")) {
	        	
		        	//check si user exist en BD
		        	User userBD = getBDUserByEmail(userSJ.getEmail());
		        	if (userBD != null) {
		        		updateBDUserFromSJ(userSJ);
		        		System.out.println(cpt + " Update user :" + userSJ.toString());
		        	} else {
		        		UserLMS user = new UserLMS();
		        		user.setEmail(userSJ.getEmail().toUpperCase());
		        		user.setFirstName(userSJ.getFirstName());
		        		user.setLastName(userSJ.getLastName());
		        		user.setOccupationRate("100");
		        		user.setTitleKnowledge(userSJ.getTitleKnowledge());
		        		user.setLastLoginKnowledge(userSJ.getLastLoginKnowledge());
		        		user.setStatusKnowledge(userSJ.getStatusKnowledge());
		        		user.setStatus_global("ActiveSJ");
		        		user.setUserName(userSJ.getUserName());
		        		addToBDUser(user);
		        		System.out.println(cpt + " Add user :" + userSJ.toString());
		        	}
	        	}
	        	
	        	
	        }
			
			
		}
		
		
		public static void importDataActvity() throws IllegalStateException, FileNotFoundException {
			
			String fileNameView = DIRPATH + "User Page Views Report (monthly)_" + PERIOD + ".csv";
	        List<View> views = new CsvToBeanBuilder(new FileReader(fileNameView))
	                .withType(View.class)
	                .withSkipLines(5) 
	                .build()
	                .parse();

		
			
			String fileNameContribution = DIRPATH + "User Contribution Activity Report_" + PERIOD + ".csv";

	        List<Activity> activities = new CsvToBeanBuilder(new FileReader(fileNameContribution))
	                .withType(Activity.class)
	                .withSkipLines(5) 
	                .build()
	                .parse();
	        
	        Integer cpt=0;

	        for (Activity activity : activities) {
	        	cpt++;
	        	//initialise le nombre de view à zéro
	        	activity.setViews(0);
	        	
	        	//ajout du nombre de view a chaque user
	        	for (View view : views) {
	        		if (view.getEmail().equals(activity.getEmail())) {
	        			activity.setViews(view.getViews());
	        		}
	        	}
	        	
	        	//ajout de la period à chaque activity
	        	activity.setPeriod(PERIOD);
	        	
	        	if(activity.getEmail().contains("@")) {
	        		addToBD(activity);
	        		System.out.println(cpt + " " + activity.getEmail() + " :" + activity.getContributions() + "-" + activity.getViews());
	        	}
	        			
	        	
				/*
				 * if ( (activity.getEmail().equals("")) | ((activity.getViews()==0 &
				 * activity.getContributions()==0)) ) {
				 * 
				 * } else { addToBD(activity); System.out.println(activity.getEmail() + " :" +
				 * activity.getContributions() + "-" + activity.getViews());
				 * 
				 * }
				 */
	        	        	
	        }
			
			
			
		}
		
		
		public static String getEmailBDUserBinding(UserWFP u) {
			
			String res = "notin";
			
			try {
				Connection connO = DBOperations.getOracleConnexion();
				Statement statement = connO.createStatement();
				String sql = "SELECT * FROM KPI_USER_BINDING WHERE LOWER(SITE)='" + u.getSite().toLowerCase() + "' and LOWER(FIRST_NAME_WFP)='" + u.getFirstName().toLowerCase() + "' and LOWER(LAST_NAME_WFP)='" + u.getLastName().toLowerCase() + "'";
				ResultSet resultSet = statement.executeQuery(sql);
				resultSet.next();
				
				if (resultSet.getRow()>0) {
					res=resultSet.getString("EMAIL");
					
				}

				resultSet.close();
				statement.close();
				connO.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return res;		

			
		}
		
		
		public static User getBDUser(String site, String firstName, String lastName) {

			User u = null;
			Entity company = Entity.getEntityBySite(site);
			
			try {
				Connection connO = DBOperations.getOracleConnexion();
				Statement statement = connO.createStatement();
				String sql = "SELECT * FROM KPI_USER WHERE LOWER(LOCATION_ID)='" + company.getLocationID().toLowerCase() + "' and LOWER(FIRST_NAME)='" + firstName.toLowerCase() + "' and LOWER(LAST_NAME)='" + lastName.toLowerCase() + "'";
				ResultSet resultSet = statement.executeQuery(sql);
				resultSet.next();
				
				if (resultSet.getRow()>0) {
					u = new User(resultSet.getString("EMAIL"),
				             resultSet.getString("LOCATION_ID"),
				             resultSet.getString("PRIMARY_PL"),
					         resultSet.getString("FIRST_NAME"),
					         resultSet.getString("LAST_NAME"),
				             resultSet.getString("OCCUPATION_RATE"),
				             resultSet.getString("TITLE_KNOWLEDGE"),
				             resultSet.getString("LAST_LOGIN_KNOWLEDGE"),
				             resultSet.getString("STATUS_KNOWLEDGE"),
				             resultSet.getString("STATUS_GLOBAL"),
				             resultSet.getString("USER_NAME"),
				             resultSet.getString("POSITION"),
				             resultSet.getString("POSITION_ID"),
				             resultSet.getString("IN_WFP"),
				             resultSet.getString("TITLE_WFP"),
				             resultSet.getString("CONTROLLING"),
					         resultSet.getString("EXPERTISE"));
				}

				resultSet.close();
				statement.close();
				connO.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return u;		
			
		}
		
		public static ArrayList<User> getBDUsers() throws SQLException {
			
				ArrayList<User> users = new ArrayList<User>();
				
			    Connection connO = DBOperations.getOracleConnexion();
				Statement statement = connO.createStatement();
				String sql = "SELECT * FROM KPI_USER";
				ResultSet rs = statement.executeQuery(sql);
				
				while (rs.next()) {
					
					User user = new User(rs.getString("EMAIL"),
				             rs.getString("LOCATION_ID"),
				             rs.getString("PRIMARY_PL"),
					         rs.getString("FIRST_NAME"),
					         rs.getString("LAST_NAME"),
				             rs.getString("OCCUPATION_RATE"),
				             rs.getString("TITLE_KNOWLEDGE"),
				             rs.getString("LAST_LOGIN_KNOWLEDGE"),
				             rs.getString("STATUS_KNOWLEDGE"),
				             rs.getString("STATUS_GLOBAL"),
				             rs.getString("USER_NAME"),
				             rs.getString("POSITION"),
				             rs.getString("POSITION_ID"),
				             rs.getString("IN_WFP"),
				             rs.getString("TITLE_WFP"),
				             rs.getString("CONTROLLING"),
					         rs.getString("EXPERTISE"));
				
					users.add(user);
					
				}
				
				return users;
			
		}
		
		public static User getBDUserByEmailAndLocationId (String email, String locationId, ArrayList<User> users) {
			User u = null;
			
			for (int i=0;i<users.size();i++) {
				
				if ( (users.get(i).getEmail().equals(email)) & 
					 (users.get(i).getLocationId().equals(locationId))	) {
					u = users.get(i);
					break;
				}
				
			}
			
			return u;
		}
		
		public static User getBDUserByEmail (String email) { 

			User u = null;
			
			try {
				Connection connO = DBOperations.getOracleConnexion();
				Statement statement = connO.createStatement();
				String sql = "SELECT * FROM KPI_USER WHERE LOWER(EMAIL)='" + email.toLowerCase() + "'";
				ResultSet resultSet = statement.executeQuery(sql);
				resultSet.next();
				
				if (resultSet.getRow()>0) {
					u = new User(resultSet.getString("EMAIL"),
					             resultSet.getString("LOCATION_ID"),
					             resultSet.getString("PRIMARY_PL"),
						         resultSet.getString("FIRST_NAME"),
						         resultSet.getString("LAST_NAME"),
					             resultSet.getString("OCCUPATION_RATE"),
					             resultSet.getString("TITLE_KNOWLEDGE"),
					             resultSet.getString("LAST_LOGIN_KNOWLEDGE"),
					             resultSet.getString("STATUS_KNOWLEDGE"),
					             resultSet.getString("STATUS_GLOBAL"),
					             resultSet.getString("USER_NAME"),
					             resultSet.getString("POSITION"),
					             resultSet.getString("POSITION_ID"),
					             resultSet.getString("IN_WFP"),
					             resultSet.getString("TITLE_WFP"),
					             resultSet.getString("CONTROLLING"),
						         resultSet.getString("EXPERTISE"));
				}

				resultSet.close();
				statement.close();
				connO.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return u;
		}
		
		
		public static boolean updateBDUserFromLMS(UserLMS u) {
			Boolean res=false;

			try {

				Connection connO = DBOperations.getOracleConnexion();
				Statement updateRecordsqlStatement = connO.createStatement();
				String updateRecordupdateRecordSQL = "UPDATE KPI_USER SET STATUS_GLOBAL='" + u.getStatus_global() +
						                                              "', FIRST_NAME='" + u.getFirstName() + 
						                                              "', LAST_NAME='" + u.getLastName() +  
						                                              "', EMAIL='" + u.getEmail().toUpperCase() +  
						                                              "', USER_NAME='" + u.getUserName() +  
						                                              "', POSITION='" + u.getPosition() +  
						                                              "', POSITION_ID='" + u.getPositionId() +  
						                                              "', LOCATION_ID='" + u.getLocationId() + 
						                                              "' WHERE UPPER(EMAIL)='" + u.getEmail().toUpperCase() + "' AND LOCATION_ID='" + u.getLocationId() + "'";
				updateRecordsqlStatement.executeUpdate(updateRecordupdateRecordSQL);
				updateRecordsqlStatement.close();
				connO.close();

				res=true;

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return res;

			
		}
		
		public static boolean updateBDUserFromSJ(UserSJ u) {
			
			Boolean res=false;

			try {

				Connection connO = DBOperations.getOracleConnexion();
				Statement updateRecordsqlStatement = connO.createStatement();
				String updateRecordupdateRecordSQL = "UPDATE KPI_USER SET TITLE_KNOWLEDGE='" + u.getTitleKnowledge() + "', LAST_LOGIN_KNOWLEDGE='" + u.getLastLoginKnowledge() + "',STATUS_KNOWLEDGE='" + u.getStatusKnowledge() + "' WHERE LOWER(EMAIL)='" + u.getEmail().toLowerCase() + "'";
				updateRecordsqlStatement.executeUpdate(updateRecordupdateRecordSQL);
				updateRecordsqlStatement.close();
				connO.close();

				res=true;

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return res;
		}
		
		
		public static boolean updateBDUserFromWFP(User u) {
			
			Boolean res=false;

			try {

				Connection connO = DBOperations.getOracleConnexion();
				Statement updateRecordsqlStatement = connO.createStatement();
				String updateRecordupdateRecordSQL = "UPDATE KPI_USER SET PRIMARY_PL='" + u.getPrimaryPl() + "',IN_WFP='" + u.getInWfp() + "',TITLE_WFP='" + u.getTitleWfp() + "',CONTROLLING='" + u.getControlling() + "',EXPERTISE='" + u.getExpertise() + "' WHERE EMAIL='" + u.getEmail() + "'";
				updateRecordsqlStatement.executeUpdate(updateRecordupdateRecordSQL);
				updateRecordsqlStatement.close();
				connO.close();

				res=true;

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return res;
		}
		
	    public static boolean updateBDUser(UserLMS u) {
			
			Boolean res=false;

			try {

				Connection connO = DBOperations.getOracleConnexion();
				Statement updateRecordsqlStatement = connO.createStatement();
				String updateRecordupdateRecordSQL = "UPDATE KPI_USER SET SITE='" +  Entity.getEntityByLocationId(u.getLocationId()).getSite() + "' WHERE lower(EMAIL)='" + u.getEmail().toLowerCase() + "'";
				updateRecordsqlStatement.executeUpdate(updateRecordupdateRecordSQL);
				updateRecordsqlStatement.close();
				connO.close();

				res=true;

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return res;
		}
		
		
	    public static void addToBDUserBinding(String site, String firstName, String lastName) {
	    	try {
				Connection connO = DBOperations.getOracleConnexion();
				Statement insertsqlStatement = connO.createStatement();
				String insertRecordSQL = "INSERT INTO KPI_USER_BINDING VALUES ('" 
				                          + "empty" + "', '"
				                          + site.trim()   + "', '"
				                          + firstName.trim()   + "', '"
								          + lastName.trim()   + "')";
				insertsqlStatement.executeUpdate(insertRecordSQL);
				insertsqlStatement.close();
				connO.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}

	    	
	    }
	    
	    
		public static boolean addToBDUser(UserLMS u) {
			
			Boolean res=false;
		
			try {
				Connection connO = DBOperations.getOracleConnexion();
				Statement insertsqlStatement = connO.createStatement();
				String insertRecordSQL = "INSERT INTO KPI_USER VALUES ('" 
	                    				+ u.getEmail()   + "', '" 
	                    				+ u.getLocationId()   + "', '" 
				                          + u.getPrimaryPl()   + "', '"
						                  + u.getFirstName()   + "', '"
										  + u.getLastName()   + "', '"
								          + u.getOccupationRate()   + "', '"
								          + u.getTitleKnowledge()   + "', '"
								          + u.getLastLoginKnowledge()   + "', '"
								          + u.getStatusKnowledge()   + "', '"
								          + u.getStatus_global()   + "', '"
								          + u.getUserName()   + "', '"
								          + u.getPosition()   + "', '"
								          + u.getPositionId()   + "', '"
								          + u.getInWfp()   + "', '"
										  + u.getTitleWfp()   + "', '"
										  + u.getControlling()   + "', '"
								          + ""   + "')";
				insertsqlStatement.executeUpdate(insertRecordSQL);
				insertsqlStatement.close();
				connO.close();

				res = true;
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			
			return res;
		}



		public static boolean addToBD(Activity a) {
			
			Boolean res=false;
		
			try {
				Connection connO = DBOperations.getOracleConnexion();
				Statement insertsqlStatement = connO.createStatement();
				String insertRecordSQL = "INSERT INTO KPI_ACTIVITY VALUES ('" 
				                          + a.getEmail() + "', '"
				                          + a.getViews()   + "', '"
				                          + a.getPeriod()   + "', '"
				                          + a.getContributions()   + "', '"
						                  + a.getGroups()   + "', '"
										  + a.getBlogs()   + "', '"
								          + a.getComments()   + "', '"
								          + a.getDiscussions()   + "', '"
								          + a.getDocuments()   + "', '"
								          + a.getExpertiseA()   + "', '"
								          + a.getExpertiseE()   + "', '"
								          + a.getIdeas()   + "', '"
								          + a.getQuestions()   + "', '"
								          + a.getKba()   + "', '"
								          + a.getKudos()   + "', '"
								          + a.getLinks()   + "', '"
								          + a.getImages()  + "', '"
								          + a.getPolls()   + "', '"
								          + a.getPollV()  + "', '"
								          + a.getStatusUpdates()   + "', '"
								          + a.getTasks()   + "', '"
								          + a.getEvents()   + "', '"
								          + a.getTags()  + "', '"
								          + a.getVideos()   + "', '"
								          + a.getWikis()  + "', '"
				                          + a.getOverview()   + "')";
				insertsqlStatement.executeUpdate(insertRecordSQL);
				insertsqlStatement.close();
				connO.close();

				res = true;
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			
			return res;
		}
		
	}

	
	
	
	
	
	

}
