package iplDemo;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Ipl_Players {
    private static PreparedStatement Stmt;
    private static Object String;

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

            String drop1 = "DROP TABLE IF EXISTS iplPlayers";
            stmt.executeUpdate(drop1);

            //            create table
            String sql = "CREATE TABLE IF NOT EXISTS iplPlayers " +
                    "( teamId int DEFAULT NULL ," + "teamName varchar(255) DEFAULT NULL, " +
                    "playerName varchar(255) DEFAULT NULL, " + "playerScore int DEFAULT NULL)";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            //Inserting values

            FileInputStream fstream = new FileInputStream("D:\\POC-Data\\Cricket_Players.txt");
            DataInputStream dstream = new DataInputStream(fstream);
            BufferedReader bf = new BufferedReader(new InputStreamReader(dstream));
            String data = null;
            String comma = ",";
            while ((data = bf.readLine()) != null) {
                StringTokenizer stoken = new StringTokenizer(data, comma);
                String teamID = "";
                String teamName = stoken.nextToken();
                String playerName = stoken.nextToken();
                String playerScore = "";

                ResultSet rsSet = null;
                Statement Stmnt = conn.createStatement();

                rsSet = Stmnt.executeQuery("select * from iplTeam");
                while (rsSet.next()) {
                    if (teamName.equals(rsSet.getString("teamName"))) {
                        teamID = new String(rsSet.getString("teamID"));
                    }
                }
                System.out.println("Enter the score of" + playerName);
                Scanner in = new Scanner(System.in);
                playerScore = in.nextLine();

                stmt.executeUpdate("insert into iplPlayers(teamID, teamName, playerName, playerScore) values ('" + teamID + "','" + teamName + "','" + playerName + "','" + playerScore + "')");
            }

                bf.close();
                stmt.close();
                System.out.println("Data inserted......");
        }
        catch(SQLException throwables){
                throwables.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
