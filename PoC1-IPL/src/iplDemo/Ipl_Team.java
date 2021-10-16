package iplDemo;
import java.io.*;
import java.sql.*;
import java.util.Properties;

public class Ipl_Team {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        // Open a connection
        try {
            // 1. Load the properties file
            Properties props = new Properties();
            props.load(new FileInputStream("d:/POC-Data/demo.properties"));

            // 2. Read the props
            String theUser = props.getProperty("user");
            String thePassword = props.getProperty("password");
            String theDburl = props.getProperty("dburl");

            System.out.println("Connecting to database...");
            System.out.println("Database URL: " + theDburl);
            System.out.println("User: " + theUser);

            // 3. Get a connection to database
            conn = DriverManager.getConnection(theDburl, theUser, thePassword);

            System.out.println("\nConnection successful!\n");
            stmt = conn.createStatement();

            String drop2 = "DROP TABLE IF EXISTS iplTeam";
            stmt.executeUpdate(drop2);

//            create table
            String sql = "CREATE TABLE IF NOT EXISTS iplTeam " + "(teamID int DEFAULT NULL, " + "teamName varchar(255) DEFAULT NULL )";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            //            Insert value
            System.out.println("Inserting records into the table...");
            sql = "INSERT INTO iplTeam VALUES (1, 'CSK')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO iplTeam VALUES (2, 'MI')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO iplTeam VALUES (3, 'KKR')";
            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table...");

            //File Write
            try ( PrintWriter fwrite = new PrintWriter(new OutputStreamWriter
                    (new BufferedOutputStream(new FileOutputStream("D:\\POC-Data\\Input_IplTeam_List.txt")), "UTF-8"));) {
                try(ResultSet res2 = stmt.executeQuery("select * from iplTeam")) {
                    while (res2.next()) {
                        fwrite.append(res2.getString("teamID")).append("\t")
                                .append(res2.getString("teamName")).append("\t")
                                .println();
                        System.out.println("File Has Been Written in txt");

                        String ID = res2.getString("teamID");
                        String Name = res2.getString("teamName");

                        System.out.format( "%10s%10s\n", ID, Name );

                    }
                    System.out.println("File Has Been Written ");
                }
            }

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}