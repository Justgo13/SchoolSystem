import java.sql.*;
import java.util.ArrayList;

public class SQLQuery {
    public static String CONNECTION_URL = "jdbc:mysql://localhost:3306/university";
    public static String USERNAME = "root";
    public static String PASSWORD = "root";
    public Connection myConn;
    public PreparedStatement myStmt;
    public ResultSet myRs;
    public SQLQuery() {
        try {
            myConn = DriverManager.getConnection(CONNECTION_URL,USERNAME,PASSWORD);
            myStmt = null;
            myRs = null;
            System.out.println("Connection established");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet runQuery(String query, ArrayList<String> params) {
        ResultSet resultQuery = null;
        try {
            myStmt = myConn.prepareStatement(query);
            for (int i = 1; i <= params.size(); i++) {
                myStmt.setString(i,params.get(i-1));
            }
            resultQuery = myStmt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultQuery;
    }

    public void runUpdate(String query, ArrayList<String> params) {
        try {
            myStmt = myConn.prepareStatement(query);
            for (int i = 1; i <= params.size(); i++) {
                myStmt.setString(i,params.get(i-1));
            }
             myStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeSQLConnection() {
        try {
            myConn.close();
            System.out.println("Connection terminated");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void openSQLConnection() {
        try {
            myConn = DriverManager.getConnection(CONNECTION_URL,USERNAME,PASSWORD);
            System.out.println("Connection established");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
