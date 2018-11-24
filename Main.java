import jdk.swing.interop.SwingInterOpUtils;

import java.io.IOException;
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
        searchArtist("J");

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

    public static void searchMusic(String titleSearch) throws SQLException {
        int select;
        Music music;
        ArrayList<Music> musicList = new ArrayList<>();
        String query = "select * from \"Musics\" WHERE title LIKE '%"+titleSearch+"%';";
        ResultSet res = stmt.executeQuery(query);
        while(res.next()) {
            music = new Music(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4), res.getInt(5), res.getString(6), res.getString(7), res.getString(8));
            musicList.add(music);
        }
        if (musicList.isEmpty()){
            System.out.println("No results were found.");
        }
        else{
            System.out.println("Results found: ");
            for(int i = 0; i<musicList.size(); i++) {
                System.out.println("" + (i+1) + ": " + musicList.get(i).getTitle() + " by "+musicList.get(i).getArtist());
            }
        }
        System.out.print("Pick a song: ");
        select = scan.nextInt();
        select -= 1;
        System.out.println("SONG INFORMATION: ");
        System.out.println("TITLE: "+musicList.get(select).getTitle());
        System.out.println("DURATION: "+musicList.get(select).getTime()+"seconds");
        System.out.println("LAUNCH DATE: "+musicList.get(select).getLaunch_date());
        System.out.println("UPVOTES: "+musicList.get(select).getUpvotes());
        System.out.println("LYRICS: "+musicList.get(select).getLyrics());
        System.out.println("FORMAT: "+musicList.get(select).getFormat());
        System.out.println("ARTIST: "+musicList.get(select).getArtist());
    }
/*
    public static void searchAlbum(String albumSearch) throws SQLException {
        int select;
        Album album;
        ArrayList<Album> albumList = new ArrayList<>();
        String query = "select * from \"Albums\" WHERE title LIKE '%"+albumSearch+"%';";
        ResultSet res = stmt.executeQuery(query);
        while(res.next()) {
            album = new Album(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), res.getString(5), res.getInt(6));
            albumList.add(album);
        }
        if (albumList.isEmpty()){
            System.out.println("No results were found.");
        }
        else{
            System.out.println("Results found: ");
            for(int i = 0; i<albumList.size(); i++) {
                System.out.println("" + (i+1) + ": " + albumList.get(i).getTitle() + " by "+albumList.get(i).getArtist());
            }
        }
        System.out.print("Pick an album: ");
        select = scan.nextInt();
        select -= 1;
        System.out.println("ALBUM INFORMATION: ");
        System.out.println("TITLE: "+albumList.get(select).getTitle());
        System.out.println("DURATION: "+albumList.get(select).getDuration()+"seconds");
        System.out.println("LAUNCH DATE: "+albumList.get(select).getLaunch());
        System.out.println("NUMBER OF TRACKS: "+albumList.get(select).getTrackNumber());
        System.out.println("ARTIST: "+albumList.get(select).getArtist());
    }

*/
    public static void searchArtist(String artistSearch) throws SQLException {
        int select;
        Artist artist;
        ArrayList<Artist> artistList = new ArrayList<>();
        String query = "select * from \"Artists\" WHERE artistic_name LIKE '%"+artistSearch+"%';";
        ResultSet res = stmt.executeQuery(query);
        while(res.next()) {
            artist = new Artist(res.getString(1), res.getString(2), res.getString(3), res.getInt(4), res.getString(5), res.getString(6));
            artistList.add(artist);
        }
        if (artistList.isEmpty()){
            System.out.println("No results were found.");
        }
        else{
            System.out.println("Results found: ");
            for(int i = 0; i<artistList.size(); i++) {
                System.out.println("" + (i+1) + ": " + artistList.get(i).getArtistic_name());
            }
        }
        System.out.print("Pick an artist: ");
        select = scan.nextInt();
        select -= 1;
        System.out.println("ARTIST INFORMATION: ");
        System.out.println("ARTISTIC NAME: "+artistList.get(select).getArtistic_name());
        System.out.println("REAL NAME: "+artistList.get(select).getName());
        System.out.println("ORIGIN: "+artistList.get(select).getOrigin());
        System.out.println("YEARS ACTIVE: "+artistList.get(select).getActive_years());
        System.out.println("SHORT BIOGRAPHY: "+artistList.get(select).getBiography());
        System.out.println("UPCOMING CONCERTS: "+artistList.get(select).getConcertos());
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
                System.out.println("Registration successful.");
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
        System.out.println("Permission changed successfully.");

    }

    public static void give_permits() throws InterruptedException, IOException, SQLException {

        int i = 1;
        int found = 0;
        int position = 0;
        String username = "";
        Scanner scan = new Scanner(System.in);

        ArrayList<String> users =  new ArrayList<>();

        if(user.getEditor() == 1){

            System.out.println("Which user do you want to provide editor permission?");
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

        if (user.getEditor() == 1) {

            System.out.println("-----ADD MUSIC-----");
            music_id = get_id("Musics");
            System.out.println("Music ID: " + music_id);
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

            pepstmt = connection.prepareStatement("INSERT INTO \"Musics\"(" +
                    "music_id, title, duration, launch_date, upvotes, lyrics, format, singer)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            pepstmt.setInt(1, music_id);
            pepstmt.setString(2, title);
            pepstmt.setInt(3, duration);
            pepstmt.setString(4, launch_date);
            pepstmt.setInt(5, upvotes);
            pepstmt.setString(6, lyrics);
            pepstmt.setString(7, format);
            pepstmt.setString(8, artist);
            pepstmt.execute();
            pepstmt.close();
            System.out.println("Music insertion successful.");
            sleep(2000);


        } else {
            System.out.println("You have to be an editor to change database info.");
        }

    }

    public static void insert_artist() throws SQLException, InterruptedException {
        String artistic_name, nome, origin, biography, concertos;
        int active_years;
        Artist artist;
        PreparedStatement pepstmt;
        clearConsole();
        if (user.getEditor() == 1) {
            System.out.println("-----ADD ARTIST-----");
            System.out.println("Artist name: ");
            artistic_name = scan.nextLine();
            System.out.println("Real name: ");
            nome = scan.nextLine();
            System.out.println("Origin: ");
            origin = scan.nextLine();
            System.out.println("Years active: ");
            active_years = scan.nextInt();
            scan.nextLine();
            System.out.println("Short biography: ");
            biography = scan.nextLine();
            System.out.println("Next concerts: ");
            concertos = scan.nextLine();

            pepstmt = connection.prepareStatement("INSERT INTO \"Artists\"(artistic_name, nome, origin, active_years, biography, concertos" +
                    ")" +
                    "VALUES (?, ?, ?, ?, ?, ?);");
            pepstmt.setString(1, artistic_name);
            pepstmt.setString(2, nome);
            pepstmt.setString(3, origin);
            pepstmt.setInt(4, active_years);
            pepstmt.setString(5, biography);
            pepstmt.setString(6, concertos);
            pepstmt.execute();
            pepstmt.close();
            System.out.println("Artist insertion successful.");
            sleep(2000);
        } else {
            System.out.println("You have to be an editor to change database info.");
        }
    }

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

