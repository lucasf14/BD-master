import java.io.IOException;
import java.net.ConnectException;
import java.sql.*;
import java.util.*;

/**
 * author: André Leite
 */

import static java.lang.Thread.sleep;


public class Main {

    private static Connection connection;
    private static Statement stmt;
    private static ResultSet res;
    private static User user;
    private static User outside_user;
    private static Scanner scan;

    public static void main(String[] args) throws Exception {

        Init start = new Init();
        sleep(2000);
        scan = new Scanner(System.in);
        connection = start.connection;

        if((stmt = connection.createStatement()) == null) {
            System.out.println("Erro nao foi possível criar uma statement ou retornou null");
            System.exit(-1);
        }

        login();
        insert_music();

    }

    public static int check_existence(String mail, String password) throws IOException {

        int found = 0; /* 0-> nao existe , 1->existe mail e pass corretos, 2-> pass incorreta*/
        PreparedStatement pepstmt;

        try {

            ResultSet res = stmt.executeQuery("SELECT Email,Nome,Password,Permit FROM \"Users\"");
            int i = 1;

            while(res.next() && found  == 0){
                String value_mail = res.getString(i);
                String value_name = res.getString(i+1);
                String value_password = res.getString(i+2);
                int value_permit = res.getInt(i+3);
                if(mail.toUpperCase().equals(value_mail.toUpperCase()) && password.equals(value_password)){
                    found = 1;
                    outside_user = new User(value_mail,value_password,value_name);
                    outside_user.setEditor(value_permit);
                }else if(mail.toUpperCase().equals(value_mail.toUpperCase()) && !password.equals(value_password)){
                    found = 2;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return found;

    }

    public static void login(){

        int found = 0;
        String password = "";
        String mail = "";
        PreparedStatement pepstmt;
        Scanner scan = new Scanner(System.in);
        clearConsole();
        System.out.println("-----LOGIN-----");
        try {


            while((mail.length() <= 10 && password.length() <= 6) && (!mail.equals("admin"))){
                System.out.printf("Email: ");
                mail = scan.nextLine();
                System.out.printf("Password: ");
                password = scan.nextLine();
                password = encrypt(password,4);
            }

            found = check_existence(mail,password);

            if(found == 1){
                /*pass*/
                user = outside_user;
                System.out.println("Hello "+user.getName()+". Welcome back!");
                System.out.println("Redirecting to menu.");
            }else if (found == 2){
                System.out.println("Password is incorrect.");
                sleep(1000);
                login();
            }else if (found == 2){
                System.out.println("Account is inexistent. Redirecting to Regist.");
                sleep(1000);
                register();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void register() throws IOException {

        int found = 0;
        String nome;
        String password;
        String mail;
        PreparedStatement pepstmt;
        Scanner scan = new Scanner(System.in);
        clearConsole();
        System.out.println("-----REGIST-----");
        try {



            System.out.printf("Email: ");
            mail = scan.nextLine();
            System.out.printf("Nome: ");
            nome = scan.nextLine();
            System.out.printf("Password: ");
            password = scan.nextLine();
            password = encrypt(password,4);
            User user = new User(mail,password,nome);

            found = check_existence(mail,password);

            if(found == 0){
                pepstmt = connection.prepareStatement("INSERT INTO  \"Users\"(Email,Password,Nome,Permit) VALUES (?,?,?,?);");
                pepstmt.setString(1,user.getUsername());
                pepstmt.setString(2,user.getPassword());
                pepstmt.setString(3,user.getName());
                pepstmt.setInt(4,user.getEditor());
                pepstmt.execute();
                pepstmt.close();
                System.out.println("Regist successfull.");
                sleep(2000);
            }else{
                System.out.println("User already existent.\nRedirecting to Login.");
                sleep(2000);
                login();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void update_permits(String user) throws SQLException {

        Statement stmt = connection.createStatement();
        stmt.execute("UPDATE  \"Users\"" +
                "SET Permit = 1"+
                "WHERE Email = \'"+user+"\';");
        System.out.println("Permission changed successfuly.");

    }

    public static void give_permits() throws InterruptedException, IOException, SQLException {

        int i = 1;
        int found = 0;
        int position = 0;
        String username = "";
        Scanner scan = new Scanner(System.in);

        ArrayList<String> users =  new ArrayList<>();

        if(user.getEditor() == 1){

            System.out.println("Wich user do you want to provide editor permission?");
            ResultSet res = stmt.executeQuery("SELECT Email,Permit FROM \"Users\"");

            while(res.next()){
                if(!res.getString(1).equals(user.getUsername()) && res.getInt(2) != 1){
                    System.out.println("["+i+"]  "+res.getString(1));
                    users.add(res.getString(1));
                    i++;
                }
            }

            do{
                System.out.printf("User number: ");
                position = scan.nextInt();
            }while(position > users.size() && position > 0);

            username = users.get(position-1);
            update_permits(username);
            sleep(1300);


        }else{
            System.out.println("You don't have the permission to do this operation. Sending you back to menu.");
            sleep(2000);
        }

    }

    public static void insert_music() throws SQLException, InterruptedException {

        Music music;

        int music_id;
        String title;
        int duration;
        String launch_date;
        int upvotes = 0;
        String lyrics;
        String format;
        String artist;
        PreparedStatement pepstmt;
        clearConsole();

        if(user.getEditor() == 1){

            System.out.println("-----ADD MUSIC-----");
            music_id = get_id("Musics");
            System.out.println("Music ID: "+music_id);
            System.out.printf("Title: ");
            title = scan.nextLine();
            System.out.printf("Duration: ");
            duration = scan.nextInt();
            scan.nextLine();
            System.out.printf("Launch Date: ");
            launch_date = scan.nextLine();
            System.out.printf("Lyrics: ");
            lyrics = scan.nextLine();
            System.out.printf("Format: ");
            format = scan.nextLine();
            System.out.printf("Artist / Band: ");
            artist = scan.nextLine();

            pepstmt = connection.prepareStatement("INSERT INTO \"Musics\"("+
                    "music_id, title, duration, launch_date, upvotes, lyrics, format, singer)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            pepstmt.setInt(1,music_id);
            pepstmt.setString(2,title);
            pepstmt.setInt(3,duration);
            pepstmt.setString(4,launch_date);
            pepstmt.setInt(5,upvotes);
            pepstmt.setString(6,lyrics);
            pepstmt.setString(7, format);
            pepstmt.setString(8, artist);
            pepstmt.execute();
            pepstmt.close();
            System.out.println("Music insert successfull.");
            sleep(2000);


        }else{
            System.out.println("You have to be an editor to change database info.");
        }

    }

    public static void insert_artist()

    public static int get_id(String table) throws SQLException {

        int id = 0;
        res = stmt.executeQuery("SELECT COUNT(*) FROM \""+table+"\";");
        if(res.next()){
            id = res.getInt(1);
        }
        return id+1;
    }

    public static String encrypt(String text, int s){

        String result= new String();
        for (int i=0; i<text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                char ch = (char)(((int)text.charAt(i) +
                        s - 65) % 26 + 65);
                result+=ch;
            }
            else{
                char ch = (char)(((int)text.charAt(i) +
                        s - 97) % 26 + 97);
                result+=ch;
            }
        }
        return result;
    }

    private static void clearConsole() {
        for (int y = 0; y < 25; y++){
            System.out.println("\n");
        }
    }

}

