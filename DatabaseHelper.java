/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fetapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author Harvey
 */
public class DatabaseHelper {

    public Connection connection;
    public ResultSet resultSet;
     public ResultSetMetaData metaData;
    public int numberOfRows;
    public Statement statement;
     public boolean connectedToDatabase;
     public ResultSet result;
     public ResultSetMetaData metadata;

    public DatabaseHelper() throws SQLException {

        this.numberOfRows = 0;
        this.statement = null;
        this.connectedToDatabase = false;
        this.resultSet = null;
        this.metaData = null;
        this.connection = null;

        createDB();//create the database if it does not exist
        // update database connection status
        connectedToDatabase = true;
    }

 
    public final void setQuery(String query)
            throws SQLException, IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // specify query and execute it WATCH OUT: statement.executequery doesnot 
//        run on queries that return number of rows such as inserts, so I use statement.execute()
        statement.execute(query);
        resultSet = statement.getResultSet();
        if (resultSet != null) {//a resultset is null if it is an update, count, and I think insert
            // determine number of rows in ResultSet
            resultSet.last(); // move to last row
            numberOfRows = resultSet.getRow(); // get row number
            metaData = resultSet.getMetaData();
        }else{
            numberOfRows = 0;
        }

    } // end method setQuery

    public int getColumnCount() throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // determine number of columns
        try {
            return metaData.getColumnCount();
        } // end try
        catch (SQLException sqlException) {
        } // end catch

        return 0; // if problems occur above, return 0 for number of columns
    } // end method getColumnCount

    // get name of a particular column in ResultSet
    public String getColumnName(int column) throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // determine column name
        try {
            return metaData.getColumnName(column + 1);
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch

        return ""; // if problems, return empty string for column name
    } // end method getColumnName
    // return number of rows in ResultSet

    public int getRowCount() throws IllegalStateException {
        // ensure database connection is available

        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        return numberOfRows;
    } // end method getRowCount

    public Object getValueAt(int row, int column)
            throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // obtain a value at specified ResultSet row and column
        try {
            resultSet.absolute(row + 1);
            return resultSet.getObject(column + 1);
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch

        return ""; // if problems, return empty string object
    } // end method getValueAt

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void disconnectFromDatabase() {
        if (connectedToDatabase) {
// close Statement and Connection
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (SQLException sqlException) {
            } // end catch
            finally // update database connection status
            {
                connectedToDatabase = false;
            } // end finally
        } // end if
    } // end method disconnectFromDatabase
    
    
    public int Query(String sql)
    {
        try{
            statement = connection.createStatement();
           // System.out.println(sql);
            return  statement.executeUpdate(sql);
        
        }
        catch(SQLException pp)
        {
            System.out.println(sql);
            System.out.println(pp.toString());
        }
        return 0;
    
    
    }
    public ArrayList ExecuteQuery(String sql) throws SQLException
    {
        ArrayList resultSet = new  ArrayList();
       try
       { 
           if(connection!=null)
           {
      statement = connection.createStatement();
        result = statement.executeQuery(sql);
       metadata = result.getMetaData();
     //  String[] res=null;
       int numcolls = metadata.getColumnCount();
       while(result.next())
       {
       
       for ( int i = 1; i <= numcolls; i++ )
       {
            
             System.out.println(i+":"+result.getObject(i));  
            
             resultSet.add(result.getObject(i));
       
            
       }
       }
           }
       }
       
       
       
       catch(SQLException pp)
       {
           System.out.println(pp.toString());
       }
       
       if(resultSet.isEmpty())
           resultSet.add(0);
        return resultSet;
       }

    /**
     *
     * @param sql
     * @return
     * @throws SQLException
     */
  

    private void createDB()  {
        try {
            String userHomeDir = System.getProperty("user.home", ".");
            String systemDir = userHomeDir + "/.fetdatabase";
            

            // Set the db system directory.
            System.setProperty("derby.system.home", systemDir);
            
            String url = "jdbc:derby:fet;create=true";
            Properties props = new Properties();
            props.put("fet", "fet");
            
            try {
                //Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                connection = DriverManager.getConnection(url, props);
                statement = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.toString());
            }
            
            String query;
            String qu;
      
          System.out.println("creating the table hms.PLAN");
          
            qu = "CREATE TABLE fet.PLAN ("
                    + "	PLAN_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "	TITLE LONG VARCHAR NOT NULL,"
                    + " CONTENT LONG VARCHAR NOT NULL,"
                    + " PLAN_DAY VARCHAR(25) NOT NULL,"
                    + " PRIMARY KEY(PLAN_ID)"            
                    + ")";
          
             System.out.println(qu);

             statement.execute(qu);  
           
       System.out.println("  PLAN TABLE CREATED  ");
       
            System.out.println("  creating the DIARY TABLE ");
            
                 query = "CREATE TABLE fet.DIARY ("
                    + " DIARY_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "	DIARY_DATE DATE NOT NULL,"
                    + "	CONTENT LONG VARCHAR NOT NULL,"
                    + " PRIMARY KEY (DIARY_ID)"
                    + ")";

                 System.out.println(query);
                 
           statement.execute(query);
           
              System.out.println("  DIARY TABLE CREATED  ");
              
            System.out.println("  CREATING THE ROOM TIME_TABLE TABLE  ==========================>");
            
         qu = "CREATE TABLE fet.TIME_TABLE ("
                    + "	TIME_TABLE_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "	COURSE_NAME VARCHAR(10) NOT NULL,"
                    + " DAY VARCHAR(9)NOT NULL,"
                    + " TIME_TABLE_TIME VARCHAR(9) NOT NULL ,"
                    + " PRIMARY KEY (TIME_TABLE_ID)"        
                    + ")";
          
             System.out.println(qu);
             
             System.out.println(" EXECUTING THE QUERY ---------->>>>>>> ");        
             
             statement.execute(qu); 
      
                  System.out.println("TIME_TABLE -> TABLE CREATED  ");
               
              System.out.println("  CREATING THE NOTIFICATION TABLE  ==========================>");
            
         qu = "CREATE TABLE fet.NOTIFICATION ("
                     + "  NOTIFICATION_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                     + " NOT_DATE DATE NOT NULL,"
                     + " NOT_TIME TIME NOT NULL,"  
                     + " CONTENT VARCHAR(90) NOT NULL,"
                     + " PRIMARY KEY (NOTIFICATION_ID)"        
                     + ")";
          
             System.out.println(qu);
             
             System.out.println(" EXECUTING THE QUERY ---------->>>>>>> ");        
             
             statement.execute(qu); 
      
                  System.out.println("NOTIFICATION -> TABLE CREATED  ");
             
                     qu = "CREATE TABLE fet.ACCOUNT ("
                     + "ACCOUNT_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                     + " NAME VARCHAR(15) NOT NULL,"
                     + " PASSWORD VARCHAR(15) NOT NULL,"  
                     + " PRIMARY KEY (ACCOUNT_ID)"        
                     + ")";
          
             System.out.println(qu);
             
             System.out.println(" EXECUTING THE QUERY ---------->>>>>>> ");        
             
             statement.execute(qu); 
      
                  System.out.println("ACCOUNT -> TABLE CREATED  ");
                  
             
                  
                  
                   
  System.out.println("............................................................................................................................ ");                  
                   
 System.out.println(" <-=========================================== ALL TABLES CREATED SUCCESSFULLY  =======================-> ");           
        }
        
            catch (SQLException ex) {
            System.out.println(""+ex.getCause()); 
        
        }
        
    }
    
    
}


