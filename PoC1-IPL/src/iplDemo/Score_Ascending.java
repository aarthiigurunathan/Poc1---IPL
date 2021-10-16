package iplDemo;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Score_Ascending {
    static final String QUERY = "SELECT teamID, teamName, playerName, playerScore FROM iplPlayers";

    public static void main(String[] args) throws Exception {
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

            // Arranging in ascending order & File Write
                try (PrintWriter fwrite = new PrintWriter(new OutputStreamWriter
                        (new BufferedOutputStream(new FileOutputStream("D:\\POC-Data\\Output_TeamWise_Scores.txt")), "UTF-8"));) {
                    try (ResultSet res2 = stmt.executeQuery(QUERY + " ORDER BY teamID  ASC, playerScore  ASC");) {
                        while (res2.next()) {

                            //Display values
                            System.out.print("teamID: " + res2.getInt("teamID"));
                            System.out.print(", teamname: " + res2.getString("teamName"));
                            System.out.print(", playerName: " + res2.getString("playerName"));
                            System.out.println(", playerScore: " + res2.getInt("playerScore"));
                            System.out.println("Arranged records in ascending order...");

                            fwrite.append(new Integer(res2.getInt("teamID")).toString()).append("\t") //1
                                    .append(res2.getString("teamName")).append("\t")  //2
                                    .append(res2.getString("playerName")).append("\t")      //3
                                    .append(new Integer(res2.getInt("playerScore")).toString()).append("\t") //4
                                    .println();
                            System.out.println("File Has Been Written in txt");

                            String ID = res2.getString("teamID");
                            String team = res2.getString("teamName");
                            String name = res2.getString("playerName");
                            String score = res2.getString("playerScore");

                            System.out.format("%10s%10s%10s%15s\n", ID,team,name,score);
                        }
                        System.out.println("File Has Been Written ");
                    }
                }
        }catch(SQLException throwables) {
            throwables.printStackTrace();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}