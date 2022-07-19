package application;

import java.sql.*;
public final class Database {
	
	private final String USERNAME = "bd27c961f191e7";
	private final String PASSWORD = "49a9e753";
	private final String DATABASE = "heroku_f19aec1502fecec";
	private final String HOST = "us-cdbr-east-06.cleardb.net";
	private final String CONECTION = String.format("jdbc:mysql://%s/%s?useSSL=false&autoReconnect=true", HOST, DATABASE);
	
	private Connection con;
	private Statement st;
	private static Database connect;
	
    private Database() {
    	try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONECTION, USERNAME, PASSWORD);  
            st = con.createStatement(); 
        } catch(Exception e) {
        	e.printStackTrace();
        
        	System.exit(0);
        }  
    }
    
    public static synchronized Database getConnection() {
		return connect = (connect == null) ? new Database() : connect;
    }

    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
    	try {
            rs = st.executeQuery(query);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return rs;
    }

    public void executeUpdate(String query) {
    	try {
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public PreparedStatement prepareStatement(String query) {
    	PreparedStatement ps = null;
    	try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ps;
    }
}
