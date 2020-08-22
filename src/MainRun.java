import java.sql.*;

/**
 * This is the main class where the GUI is instantiated
 */
public class MainRun {
	public static Connection myConn;
	public static PreparedStatement myStmt;
	public static ResultSet myRs;

	public static void main (String[] args) {
		try { 
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/university","root","root");
			myStmt = null;
			myRs = null;
			System.out.println("Connection established");
	        new WelcomePage();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
