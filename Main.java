import com.mysql.cj.jdbc.util.ResultSetUtil;
import org.postgresql.util.PSQLException;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * author: André Leite
 */

import static java.lang.Thread.sleep;


@SuppressWarnings("ALL")
public class Main {

    private static Connection connection;
    private static Statement stmt;
    private static ResultSet res;
    private static User user;
    private static User outside_user;
    private static Scanner scan;

    public static void welcome() throws IOException, InterruptedException {

        String option = "0";

        clearConsole();

        System.out.println("----WELCOME TO DROPMUSIC----");
        System.out.println("");
        System.out.println("1: Register");
        System.out.println("2: Login");


        System.out.printf("\nOption: ");
        option = scan.nextLine();


        switch (option){
            case "1":
                register();
                break;
            case "2":
                login();
                break;
            default:
                welcome();
        }

    }

    public static void main_menu() throws IOException, InterruptedException, SQLException {

        String option = "";
        String text = "";
        clearConsole();
        System.out.println("----MAIN MENU----\n");
        System.out.println("1: Search Musics");
        System.out.println("2: Search Albums");
        System.out.println("3: Search Artists");
        System.out.println("4: My Playlists");
        System.out.println("5: Create Playlist");
        System.out.println("6: Add Music to Playlist");
        System.out.println("7: Search Public playlists");
        System.out.println("8: See your critics");
        System.out.println("9: Delete Playlist");
        System.out.println("10: Delete Account\n");
        System.out.println("----EDITOR OPTIONS-----\n");
        System.out.println("11: Insert Musics");
        System.out.println("12: Insert Albums");
        System.out.println("13: Insert Artists");
        System.out.println("14: Edit Info");
        System.out.println("15: Give Permissions\n");
        System.out.println("0: EXIT\n");

        System.out.printf("Option: ");
        option = scan.nextLine();

        switch (option){

            case "0":
                System.exit(1);
                break;
            case "1":
                clearConsole();
                System.out.println("----SEARCH MUSIC----");
                System.out.printf("\nMusica: ");
                text = scan.nextLine();
                searchMusic(text);
                break;
            case "2":
                clearConsole();
                System.out.println("----SEARCH ALBUM----");
                System.out.printf("\nAlbum: ");
                text = scan.nextLine();
                searchAlbum(text);
                break;
            case "3":
                clearConsole();
                System.out.println("----SEARCH ARTIST----");
                System.out.printf("\nArtist: ");
                text = scan.nextLine();
                searchArtist(text);
                break;
            case "4":
                my_playlists();
                break;
            case "5":
                create_playlist();
                break;
            case "6":
                add_music_to_playlist();
                break;
            case "7":
                all_playlists();
                break;
            case "8":
                my_critics();
                break;
            case "9":
                delete_playlist();
                break;
            case "10":
                delete_account();
                break;
            case "11":
                insert_music();
                break;
            case "12":
                insert_album();
                break;
            case "13":
                insert_artist();
                break;
            case "14":
                edit();
                break;
            case "15":
                give_permits();
                break;
            default:
                main_menu();
        }

    }

    public static void edit() throws SQLException, InterruptedException, IOException {

        String option;
        ArrayList<Music> musics = get_musics();
        ArrayList<Album> albums = get_albums();
        int idpi;
        clearConsole();
        System.out.println("----EDIT----");
        System.out.println("\n** Select 0 to go back to main menu **\n");
        System.out.println("1: Associate Album to Composer");
        System.out.println("2: Associate Album to Genre");
        System.out.println("3: Associate Album to Label");
        System.out.println("4: Associate Music to Album");
        System.out.println("5: Associate Music to Composer");
        System.out.println("6: Associate Music to Genre");
        System.out.println("7: Associate Music to Label");

        System.out.printf("\nOption: ");
        option = scan.nextLine();
        System.out.println();

        switch (option){

            case "1":
                idpi = get_album_id(albums);
                associate_composer(idpi,"Composers_Album","album_id");
                break;
            case "2":
                idpi = get_album_id(albums);
                associate_genre(idpi,"Genres_Album","album_id");
                break;
            case "3":
                idpi = get_album_id(albums);
                associate_label(idpi,"Labels_Album","album_id");
                break;
            case "4":
                associate_music_album();
                break;
            case "5":
                idpi = get_music_id(musics);
                associate_composer(idpi,"Composers","music_id");
                break;
            case "6":
                idpi = get_music_id(musics);
                associate_genre(idpi,"Genres_Music","music_id");
                break;
            case "7":
                idpi = get_music_id(musics);
                associate_label(idpi,"Labels_Music","music_id");
                break;
            default:
                main_menu();
        }

        main_menu();

    }

    public static int get_album_id(ArrayList<Album> albums){
        String option;
        int idpi;
        int id;
        for(int i = 0; i < albums.size(); i++){
            System.out.println("Album ["+(i+1)+"] "+albums.get(i).getTitle());
        }
        System.out.printf("\nOption: ");
        option = scan.nextLine();
        idpi = Integer.parseInt(option);
        id = albums.get(idpi-1).getAlbum_id();
        return id;
    }

    public static int get_music_id(ArrayList<Music> musics){
        String option;
        int idpi;
        int id;
        for(int i = 0; i < musics.size(); i++){
            System.out.println("Music ["+(i+1)+"] "+musics.get(i).getTitle()+" by "+musics.get(i).getArtist());
        }
        System.out.printf("\nOption: ");
        option = scan.nextLine();
        idpi = Integer.parseInt(option);
        id = musics.get(idpi-1).getMusic_id();
        return id;
    }

    public static void back_to_menu() throws InterruptedException, SQLException, IOException {

        String op;
        int i = 0;
        System.out.println();
        System.out.println("Press Enter key to go back to main menu. ");
        op = scan.nextLine();
        main_menu();

    }

    public static void create_playlist() throws SQLException, InterruptedException, IOException {

        int playlist_id = get_id("Playlists","playlist_id");
        String playlist_name;
        int privacy;
        PreparedStatement pepstmt;

        clearConsole();
        System.out.println("----CREATE PLAYLIST----");
        System.out.println("Playlist ID: "+playlist_id);
        System.out.printf("Title: ");
        playlist_name = scan.nextLine();
        System.out.printf("Privacy ( 0 - Public | 1 - Private ) : ");
        privacy = scan.nextInt();
        scan.nextLine();

        pepstmt = connection.prepareStatement("INSERT INTO public.\"Playlists\"(" +
                "playlist_id, email, titulo, privacidade, musics_m)" +
                "VALUES (?, ?, ?, ?, ?);");
        pepstmt.setInt(1,playlist_id);
        pepstmt.setString(2,user.getUsername());
        pepstmt.setString(3,playlist_name);
        pepstmt.setInt(4,privacy);
        pepstmt.setInt(5,0);
        pepstmt.execute();
        pepstmt.close();

        System.out.println("New playlist "+playlist_name+" createad succesfully.");
        sleep(2000);
        main_menu();

    }

    public static void delete_playlist() throws SQLException, InterruptedException, IOException {

        int play_id;
        int i;
        int j = 1;
        int choice = 0;
        ArrayList<Playlist> lista = get_playlists(2);
        ResultSet set;
        String playlist;
        PreparedStatement pepstmt;

        clearConsole();

        System.out.println("-----DELETE PLAYLIST-----");
        System.out.println("\nSelect 0 to go back to main menu. \n");
        for(i = 0; i < lista.size(); i++){
            try{
                System.out.println("Playlist ["+(i+1)+"]: "+lista.get(i).getTitulo());
                set = stmt.executeQuery("SELECT \"Playlists\".titulo, \"Musics\".title" +
                        " FROM \"Playlists\",\"Musics\",\"Playlists_Musics\" " +
                        "WHERE \"Playlists\".playlist_id = \"Playlists_Musics\".playlist_id " +
                        "AND \"Musics\".music_id = \"Playlists_Musics\".music_id " +
                        "AND \"Playlists\".titulo = \'"+lista.get(i).getTitulo()+"\';");
                while(set.next()){
                    System.out.println("   Music ["+j+"] : "+set.getString(2));
                    j++;
                }
                j = 1;
            }catch (PSQLException e){
                System.out.println("No musics");
            }
        }

        System.out.printf("\nWhich playlist would you like to delete?\n");
        do {
            System.out.printf("-> ");
            playlist = scan.nextLine();
            choice = Integer.parseInt(playlist);
        }while(choice < 0 || choice > lista.size());

        play_id = lista.get(choice-1).getPlaylist_id();

        if(choice == 0){
            sleep(1000);
            main_menu();
        }else{

            System.out.println("\nDeleting playlist "+lista.get(choice-1).getTitulo());
            sleep(2000);
            pepstmt = connection.prepareStatement("DELETE FROM \"Playlists_Musics\" WHERE playlist_id = "+play_id+";");
            pepstmt.execute();
            pepstmt = connection.prepareStatement("DELETE FROM \"Playlists\" WHERE playlist_id = "+play_id+";");
            pepstmt.execute();
            pepstmt.close();
            System.out.println("Playlist "+lista.get(choice-1).getTitulo()+" deleted successfully.");
            sleep(2000);
            main_menu();
        }

    }

    public static ArrayList<Playlist> get_playlists(int flag) throws SQLException {

        ArrayList<Playlist> playlists = new ArrayList<>();
        Playlist play;
        ResultSet set = null;

        if(flag == 0){ /* todas as playlists publicas */

            set = stmt.executeQuery("SELECT * FROM \"Playlists\" WHERE privacidade = 0;");

        }else if(flag == 2){ /* playlists referentes a um utlizador */

            set = stmt.executeQuery("SELECT * FROM \"Playlists\" WHERE email = \'"+user.getUsername()+"\';");

        }

        while(set.next()){
            play = new Playlist(set.getInt(1),set.getString(3),set.getString(2),set.getInt(5),set.getInt(4));
            playlists.add(play);
        }

        return playlists;

    }

    public static ArrayList<Music> get_musics() throws SQLException {

        ArrayList<Music> musics = new ArrayList<>();
        Music muse;
        ResultSet set = null;

        set = stmt.executeQuery("SELECT * FROM \"Musics\"");

        while(set.next()){
            muse = new Music(set.getInt(1),set.getString(2),set.getInt(3),set.getString(4),set.getInt(5),set.getString(6),set.getString(7),set.getString(8));
            musics.add(muse);
        }

        return musics;

    }

    public static ArrayList<Album> get_albums() throws SQLException {

        ArrayList<Album> albums = new ArrayList<>();
        Album muse;
        ResultSet set = null;

        set = stmt.executeQuery("SELECT * FROM \"Albums\"");

        while(set.next()){
            muse = new Album(set.getInt(1),set.getString(2),set.getInt(3),set.getString(4),set.getInt(5));
            albums.add(muse);
        }

        return albums;

    }

    public static ArrayList<Artist> get_artists() throws SQLException {

        ArrayList<Artist> artists = new ArrayList<>();
        Artist muse;
        ResultSet set = null;

        set = stmt.executeQuery("SELECT * FROM \"Artists\"");

        while(set.next()){
            muse = new Artist(set.getString(1),set.getString(2),set.getString(3),set.getInt(4),set.getString(5),set.getString(6));
            artists.add(muse);
        }

        return artists;
    }

    public static ArrayList<Label> get_labels() throws SQLException {

        ArrayList<Label> label = new ArrayList<>();
        Label muse;
        ResultSet set = null;

        set = stmt.executeQuery("SELECT * FROM \"Labels\"");

        while(set.next()){
            muse = new Label(set.getString(1));
            label.add(muse);
        }

        return label;
    }

    public static ArrayList<Genre> get_genres() throws SQLException {

        ArrayList<Genre> genres = new ArrayList<>();
        Genre muse;
        ResultSet set = null;

        set = stmt.executeQuery("SELECT * FROM \"Genres\"");

        while(set.next()){
            muse = new Genre(set.getString(1));
            genres.add(muse);
        }

        return genres;
    }

    public static void searchMusic(String titleSearch) throws SQLException, IOException, InterruptedException {

        int select;
        Music music;
        String op;
        ArrayList<Music> musicList = new ArrayList<>();
        String query = "select * from \"Musics\" WHERE title LIKE '%"+titleSearch+"%';";
        ResultSet res = stmt.executeQuery(query);
        while(res.next()) {
            music = new Music(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4), res.getInt(5), res.getString(6), res.getString(7), res.getString(8));
            musicList.add(music);
        }
        if (musicList.isEmpty()){
            System.out.println("No results were found.\n");
            back_to_menu();

        }
        else{
            System.out.println("\nResults found: \n");
            for(int i = 0; i<musicList.size(); i++) {
                System.out.println("Music [" + (i+1) + "] : " + musicList.get(i).getTitle() + " by "+musicList.get(i).getArtist());
            }
            do {
                System.out.print("\nPick a song: ");
                select = scan.nextInt();
            }while(select <= 0 && select > musicList.size());
            scan.nextLine();
            select -= 1;
            clearConsole();

            System.out.println("\n----MUSIC INFORMATION----\n");
            System.out.println("TITLE: "+musicList.get(select).getTitle()+"\n");
            System.out.printf("GENRE/S: ");
            res = stmt.executeQuery("SELECT \"Musics\".title, \"Genres_Music\".genre " +
                    "FROM \"Musics\",\"Genres_Music\" " +
                    "WHERE \"Musics\".music_id = \"Genres_Music\".music_id " +
                    "AND \"Musics\".title = '"+musicList.get(select).getTitle()+"';");
            while(res.next()){
                System.out.printf(" "+res.getString(2));
            }
            System.out.println("\n");
            System.out.printf("COMPOSERS/S: ");
            res = stmt.executeQuery("SELECT \"Musics\".title, \"Composers\".artistic_name " +
                    "FROM \"Musics\",\"Composers\"\n " +
                    "WHERE \"Musics\".music_id = \"Composers\".music_id " +
                    "AND \"Musics\".title = '"+musicList.get(select).getTitle()+"';");
            while(res.next()){
                System.out.printf(" "+res.getString(2));
            }
            System.out.println("\n");
            System.out.printf("LABEL/S: ");
            res = stmt.executeQuery("SELECT \"Musics\".title, \"Labels_Music\".label " +
                    "FROM \"Musics\",\"Labels_Music\"\n " +
                    "WHERE \"Musics\".music_id = \"Labels_Music\".music_id " +
                    "AND \"Musics\".title = '"+musicList.get(select).getTitle()+"';");
            while(res.next()){
                System.out.printf(" "+res.getString(2)+";");
            }
            System.out.println("\n");
            System.out.println("DURATION: "+musicList.get(select).getDuration()+" seconds"+"\n");
            System.out.println("LAUNCH DATE: "+musicList.get(select).getLaunch_date()+"\n");
            System.out.println("UPVOTES: "+musicList.get(select).getUpvotes()+"\n");
            System.out.println("LYRICS: "+musicList.get(select).getLyrics()+"\n");
            System.out.println("FORMAT: "+musicList.get(select).getFormat()+"\n");
            System.out.println("ARTIST: "+musicList.get(select).getArtist()+"\n");
            System.out.printf("CRITIC/S: ");
            res = stmt.executeQuery("SELECT \"Musics\".title, \"Music_Critics\".critic\n" +
                    "FROM \"Musics\",\"Music_Critics\"\n" +
                    "WHERE \"Musics\".music_id = \"Music_Critics\".music_id " +
                    "AND \"Musics\".title = '"+musicList.get(select).getTitle()+"';");
            while(res.next()){
                System.out.printf(" "+res.getString(2)+" ;");
            }
            System.out.println("\n");
            System.out.printf("ALBUM/S: ");
            res = stmt.executeQuery("SELECT \"Albums\".title\n" +
                    "FROM \"Albums\",\"Albums_Music\"\n" +
                    "WHERE \"Albums\".album_id = \"Albums_Music\".album_id\n " +
                    "AND \"Albums_Music\".music_id = "+musicList.get(select).getMusic_id()+";");
            while(res.next()){
                System.out.printf(" "+res.getString(1)+" ;");
            }
            System.out.println("\n");

            do {
                System.out.println("Would you like to leave a critic? (y/n)");
                System.out.printf("-> ");
                op = scan.nextLine();
            }while(!op.toUpperCase().equals("Y") && !op.toUpperCase().equals("N"));

            if(op.toUpperCase().equals("Y")){
                add_critic(musicList.get(select).getMusic_id(),"Music_Critics","music_id");
            }else{
                back_to_menu();

            }

        }
    }

    public static void searchAlbum(String albumSearch) throws SQLException, IOException, InterruptedException {

        int select;
        String op;
        Album album;
        String art;
        ArrayList<Album> albumList = new ArrayList<>();
        String query = "select * from \"Albums\" WHERE title LIKE '%"+albumSearch+"%';";
        ResultSet res = stmt.executeQuery(query);
        ResultSet set ;

        while(res.next()) {
            album = new Album(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4), res.getInt(5));
            albumList.add(album);
        }


        for(int i = 0; i < albumList.size(); i++){
            if((set = stmt.executeQuery("SELECT \"Bands\".band , \"Albums\".title FROM \"Albums\",\"Artists\",\"Albums_Artists\",\"Bands\"" +
                    "WHERE \"Artists\".artistic_name = \"Albums_Artists\".artistic_name AND \"Albums\".album_id = \"Albums_Artists\".album_id AND \"Artists\".artistic_name = \"Bands\".artistic_name AND \"Albums\".title LIKE '%"+albumList.get(i).getTitle()+"%'"+
                    "GROUP BY \"Bands\".band , \"Albums\".title;")).next()){

                if(albumList.get(i).getTitle().equals(set.getString(2))){
                    art = set.getString(1);
                    albumList.get(i).artist = art;
                }

            }
        }



        if (albumList.isEmpty()){
            System.out.println("No results were found.\n");
            back_to_menu();

        }
        else{
            System.out.println("Results found: \n");
            for(int i = 0; i<albumList.size(); i++) {
                System.out.println("Album [" + (i+1) + "] : " + albumList.get(i).getTitle() + " by "+albumList.get(i).artist);
            }
            do {
                System.out.printf("\nPick an album: ");
                select = scan.nextInt();
            }while(select <= 0 && select > albumList.size());
            select -= 1;
            scan.nextLine();
            System.out.println("\n----ALBUM INFORMATION---- "+"\n");
            System.out.println("TITLE: "+albumList.get(select).getTitle()+"\n");
            System.out.printf("GENRE/S: ");
            res = stmt.executeQuery("SELECT \"Albums\".title, \"Genres_Album\".genre " +
                    "FROM \"Albums\",\"Genres_Album\" " +
                    "WHERE \"Albums\".album_id = \"Genres_Album\".album_id " +
                    "AND \"Albums\".title = '"+albumList.get(select).getTitle()+"';");
            while(res.next()){
                System.out.printf(" "+res.getString(2)+";");
            }
            System.out.println("\n");
            System.out.printf("COMPOSERS/S: ");
            res = stmt.executeQuery("SELECT \"Albums\".title, \"Composers_Album\".artistic_name " +
                    "FROM \"Albums\",\"Composers_Album\"\n " +
                    "WHERE \"Albums\".album_id = \"Composers_Album\".album_id " +
                    "AND \"Albums\".title = '"+albumList.get(select).getTitle()+"';");
            while(res.next()){
                System.out.printf(" "+res.getString(2)+";");
            }
            System.out.println("\n");
            System.out.printf("LABEL/S: ");
            res = stmt.executeQuery("SELECT \"Albums\".title, \"Labels_Album\".label " +
                    "FROM \"Albums\",\"Labels_Album\"\n " +
                    "WHERE \"Albums\".album_id = \"Labels_Album\".album_id " +
                    "AND \"Albums\".title = '"+albumList.get(select).getTitle()+"';");
            while(res.next()){
                System.out.printf(" "+res.getString(2)+";");
            }
            System.out.println("\n");
            System.out.println("DURATION: "+albumList.get(select).getDuration()+" seconds"+"\n");
            System.out.println("LAUNCH DATE: "+albumList.get(select).getLaunch()+"\n");
            System.out.println("NUMBER OF TRACKS: "+albumList.get(select).getTrackNumber()+"\n");
            if(albumList.get(select).artist == ""){
                System.out.println("ARTIST: None artist is associated."+"\n");
            }else{
                System.out.println("ARTIST: "+albumList.get(select).artist+"\n");
            }
            System.out.printf("CRITIC/S: ");
            res = stmt.executeQuery("SELECT \"Albums\".title, \"Album_Critics\".critic\n" +
                    "FROM \"Albums\",\"Album_Critics\"\n" +
                    "WHERE \"Albums\".album_id = \"Album_Critics\".album_id " +
                    "AND \"Albums\".title = '"+albumList.get(select).getTitle()+"';");
            while(res.next()){
                System.out.printf(" "+res.getString(2)+"; ");
            }
            System.out.println("\n");
            System.out.printf("MUSIC/S: ");
            res = stmt.executeQuery("SELECT \"Musics\".title\n" +
                    "FROM \"Musics\",\"Albums_Music\"\n" +
                    "WHERE \"Musics\".music_id = \"Albums_Music\".music_id\n " +
                    "AND \"Albums_Music\".album_id = "+albumList.get(select).getAlbum_id()+";");
            while(res.next()){
                System.out.printf(" "+res.getString(1)+";");
            }
            System.out.println("\n");

            do {
                System.out.println("Would you like to write a critic? (y/n)");
                System.out.printf("-> ");
                op = scan.nextLine();
            }while(!op.toUpperCase().equals("Y") && !op.toUpperCase().equals("N"));

            if(op.toUpperCase().equals("Y")){
                add_critic(albumList.get(select).getAlbum_id(),"Album_Critics","album_id");
            }else{
                back_to_menu();
            }

        }
    }

    public static void searchArtist(String artistSearch) throws SQLException, IOException, InterruptedException {
        int select;
        Artist artist;
        String band = "";
        ArrayList<Artist> artistList = new ArrayList<>();
        String query = "select * from \"Artists\" WHERE artistic_name LIKE '%"+artistSearch+"%';";
        ResultSet res = stmt.executeQuery(query);
        ResultSet set;
        while(res.next()) {
            artist = new Artist(res.getString(1), res.getString(2), res.getString(3), res.getInt(4), res.getString(5), res.getString(6));
            artistList.add(artist);
        }

        for(int i = 0; i < artistList.size(); i++){
            if((set = stmt.executeQuery("SELECT \"Bands\".band FROM \"Bands\",\"Artists\" " +
                    "WHERE \"Bands\".artistic_name = \"Artists\".artistic_name " +
                    "AND \"Artists\".artistic_name = \'"+artistList.get(i).getArtistic_name()+"\';")).next()){

                band = set.getString(1);
                artistList.get(i).band = band;

            }
        }


        if (artistList.isEmpty()){
            System.out.println("\nNo results were found.\n");
        }
        else{
            System.out.println("\nResults found: \n");
            for(int i = 0; i<artistList.size(); i++) {
                System.out.println("Artist [" + (i+1) + "] : " + artistList.get(i).getArtistic_name());
            }
            System.out.print("\nPick an artist: ");
            select = scan.nextInt();
            select -= 1;
            scan.nextLine();
            System.out.println("\n----ARTIST INFORMATION----\n ");
            System.out.println("ARTISTIC NAME: "+artistList.get(select).getArtistic_name()+"\n");
            System.out.println("REAL NAME: "+artistList.get(select).getName()+"\n");
            if(artistList.get(select).band == ""){
                System.out.println("BAND: none existent\n");
            }else{
                System.out.println("BAND: "+artistList.get(select).band+"\n");
            }
            System.out.println("ORIGIN: "+artistList.get(select).getOrigin()+"\n");
            System.out.println("YEARS ACTIVE: "+artistList.get(select).getActive_years()+"\n");
            System.out.println("SHORT BIOGRAPHY: "+artistList.get(select).getBiography()+"\n");
            System.out.println("UPCOMING CONCERTS: "+artistList.get(select).getConcertos()+"\n");
            System.out.printf("COMPOSED: ");
            set = stmt.executeQuery("SELECT \"Musics\".title " +
                    "FROM \"Composers\",\"Musics\"" +
                    "WHERE \"Musics\".music_id = \"Composers\".music_id " +
                    "AND \"Composers\".artistic_name = '"+artistList.get(select).getArtistic_name()+"' ;");
            while(set.next()){
                System.out.printf(""+set.getString(1)+" ; ");
            }
            System.out.println("\n");
            System.out.printf("ALBUMS: ");
            set = stmt.executeQuery("SELECT \"Albums\".title " +
                    "FROM \"Composers_Album\",\"Albums\"" +
                    "WHERE \"Albums\".album_id = \"Composers_Album\".album_id " +
                    "AND \"Composers_Album\".artistic_name = '"+artistList.get(select).getArtistic_name()+"' ;");
            while(set.next()){
                System.out.printf(""+set.getString(1)+" ; ");
            }
            System.out.println("\n");

        }


        back_to_menu();
    }

    public static void my_critics() throws SQLException, IOException, InterruptedException {

        ResultSet set;

        set = stmt.executeQuery("SELECT \"Users\".email, \"Music_Critics\".critic, \"Musics\".title " +
                "FROM \"Users\",\"Music_Critics\",\"Musics\"" +
                "WHERE \"Users\".email = \"Music_Critics\".email " +
                "AND \"Musics\".music_id = \"Music_Critics\".music_id " +
                "AND \"Music_Critics\".email = '"+user.getUsername()+"';");

        clearConsole();
        System.out.println("----MUSICS CRITICS----\n");
        System.out.println("User: "+user.getUsername()+"\n");

        while(set.next()){
            System.out.println("CRITIC : "+set.getString(2)+" to "+set.getString(3));
        }

        System.out.println("\n----ALBUM CRITICS----\n");

        set = stmt.executeQuery("SELECT \"Users\".email, \"Album_Critics\".critic, \"Albums\".title " +
                "FROM \"Users\",\"Album_Critics\",\"Albums\"" +
                "WHERE \"Users\".email = \"Album_Critics\".email " +
                "AND \"Albums\".album_id = \"Album_Critics\".album_id " +
                "AND \"Album_Critics\".email = '"+user.getUsername()+"';");

        while(set.next()){
            System.out.println("CRITIC : "+set.getString(2)+" to "+set.getString(3));
        }

        back_to_menu();

    }

    public static void my_playlists() throws SQLException, IOException, InterruptedException {

        int i;
        int j = 1;
        ArrayList<Playlist> lista = get_playlists(2);
        ResultSet set;
        clearConsole();
        System.out.println("----YOUR PLAYLISTS----");
        System.out.println();
        for(i = 0; i < lista.size(); i++){
            try{
                System.out.println("Playlist ["+(i+1)+"]: "+lista.get(i).getTitulo());
                set = stmt.executeQuery("SELECT \"Playlists\".titulo, \"Musics\".title" +
                        " FROM \"Playlists\",\"Musics\",\"Playlists_Musics\" " +
                        "WHERE \"Playlists\".playlist_id = \"Playlists_Musics\".playlist_id " +
                        "AND \"Musics\".music_id = \"Playlists_Musics\".music_id " +
                        "AND \"Playlists\".titulo = \'"+lista.get(i).getTitulo()+"\';");
                while(set.next()){
                    System.out.println("   Music ["+j+"] : "+set.getString(2));
                    j++;
                }
                j = 1;
            }catch (PSQLException e){
                System.out.println("No musics");
            }
        }
        back_to_menu();
    }

    public static void all_playlists() throws SQLException, IOException, InterruptedException {

        int i;
        int p = 1;
        ArrayList<Playlist> lista = get_playlists(0);
        ResultSet set;
        clearConsole();
        System.out.println("----ALL PLAYLISTS----");
        System.out.println();
        for(i = 0; i < lista.size(); i++){
            try{
                System.out.println("Playlist ["+(i+1)+"]: "+lista.get(i).getTitulo());
                set = stmt.executeQuery("SELECT \"Playlists\".titulo, \"Musics\".title" +
                        " FROM \"Playlists\",\"Musics\",\"Playlists_Musics\" " +
                        "WHERE \"Playlists\".playlist_id = \"Playlists_Musics\".playlist_id " +
                        "AND \"Musics\".music_id = \"Playlists_Musics\".music_id " +
                        "AND \"Playlists\".titulo = \'"+lista.get(i).getTitulo()+"\';");
                while(set.next()){
                    System.out.println("   Music ["+p+"] : "+set.getString(2));
                    p++;
                }
                p = 1;
            }catch (PSQLException e){
                System.out.println("No musics");
            }
        }
        back_to_menu();
    }

    public static void add_music_to_playlist() throws SQLException, IOException, InterruptedException {

        int i;
        int op = 0;
        int mp = 0;
        int lines = 0;
        String option = "";
        ArrayList<Playlist> lista = get_playlists(2);
        PreparedStatement pepstmt;
        ResultSet set;

        clearConsole();
        System.out.println("----ADD MUSIC TO PLAYLIST----\n");
        System.out.println("* Click 0 to Return to Main Menu*\n");
        for(i = 0; i < lista.size(); i++){
            System.out.println("Playlist ["+(i+1)+"]: "+lista.get(i).getTitulo());
        }

        do{
            System.out.printf("\nPlaylist of choice: ");
            option = scan.nextLine();
            op = Integer.parseInt(option);
        }while(op < 0 || op > lista.size());

        System.out.println();
        if(op == 0){
            main_menu();
        }else{
            System.out.println("Adding music to: "+lista.get(op-1).getTitulo());
            ArrayList<Music> musics = get_musics();
            for(i = 0; i < musics.size(); i++){
                System.out.println("Music ["+(i+1)+"]: "+musics.get(i).getTitle()+" by "+musics.get(i).getArtist());
            }
            do{
                System.out.printf("\nMusic of choice: ");
                option = scan.nextLine();
                mp = Integer.parseInt(option);
            }while(mp < 0 || mp > musics.size());
            if(mp == 0){
                main_menu();
            }else{

                set = stmt.executeQuery("SELECT COUNT(*) FROM \"Playlists_Musics\" WHERE music_id = "+musics.get(mp-1).getMusic_id()+" AND playlist_id = "+lista.get(op-1).getPlaylist_id()+";");
                if(set.next()){
                    lines = set.getInt(1);
                    System.out.println("Number of musics: "+lines+"\n");
                }
                if(lines < 1){
                    pepstmt = connection.prepareStatement("INSERT INTO \"Playlists_Musics\"(" +
                            "playlist_id, music_id)" +
                            "VALUES (?, ?);");
                    pepstmt.setInt(1,lista.get(op-1).getPlaylist_id());
                    pepstmt.setInt(2,musics.get(mp-1).getMusic_id());
                    pepstmt.execute();
                    pepstmt.close();

                }else{
                    System.out.println("This music is already in your playlist.");
                }
                System.out.println("Added "+musics.get(mp-1).getTitle()+" to "+lista.get(op-1).getTitulo());
                sleep(2000);
                main_menu();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Init start = new Init();
        sleep(2000);
        scan = new Scanner(System.in);
        connection = start.connection;

        if((stmt = connection.createStatement()) == null) {
            System.out.println("Erro nao foi possível criar uma statement ou retornou null");
            System.exit(-1);
        }

        welcome();

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


            while((mail.length() <= 10 && password.length() <= 6) && (!mail.toUpperCase().equals("ADMIN"))){
                System.out.printf("Email: ");
                mail = scan.nextLine();
                System.out.printf("Password: ");
                password = scan.nextLine();
                password = encrypt(password,4);
            }

            found = check_existence(mail.toUpperCase(),password);

            if(found == 1){
                /*pass*/
                user = outside_user;
                System.out.println("Hello "+user.getName()+". Welcome back!");
                System.out.println("Redirecting to menu.");
                sleep(1000);
                main_menu();
            }else if (found == 2){
                System.out.println("Password is incorrect.");
                sleep(1000);
                login();
            }else if (found == 0){
                System.out.println("Account is inexistent. Redirecting to Register.");
                sleep(1000);
                register();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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
        System.out.println("-----REGISTER-----");
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
                System.out.println("Register successful. Redirecting to login.");
                sleep(2000);
                login();
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
        back_to_menu();
    }

    public static void insert_music() throws SQLException, InterruptedException, IOException {

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
            music_id = get_id("Musics","music_id");
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
            System.out.println("Music insert successful.");

            associate_composer(music_id,"Composers","music_id");
            associate_label(music_id,"Labels_Music","music_id");
            associate_genre(music_id,"Genres_Music","music_id");

            sleep(2000);


        }else{
            System.out.println("You have to be an editor to change database info.");
            sleep(1000);
        }
        main_menu();


    }

    public static void insert_artist() throws SQLException, InterruptedException, IOException {

        String artistic_name, nome, origin, biography, concertos;
        int active_years;
        int lines = 0;
        ResultSet set;
        String g;
        String band;
        PreparedStatement pepstmt;
        clearConsole();

        if (user.getEditor() == 1) {
            System.out.println("-----ADD ARTIST-----");
            System.out.printf("Artist name: ");
            artistic_name = scan.nextLine();
            System.out.printf("Real name: ");
            nome = scan.nextLine();
            System.out.printf("Origin: ");
            origin = scan.nextLine();
            System.out.printf("Years active: ");
            active_years = scan.nextInt();
            scan.nextLine();
            System.out.printf("Short biography: ");
            biography = scan.nextLine();
            System.out.printf("Next concerts: ");
            concertos = scan.nextLine();

            set = stmt.executeQuery("SELECT COUNT(*) FROM \"Artists\" WHERE aristic_name = '"+artistic_name+"';");
            if(set.next()){
                lines = set.getInt(1);
            }
            if(lines < 1){
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

                do{
                    System.out.printf("Is this element part of any band? (y/n): ");
                    g = scan.nextLine();
                }while(!g.toUpperCase().equals("Y") && !g.toUpperCase().equals("N"));

                if (g.toUpperCase().equals("Y")){

                    System.out.printf("Band name: ");
                    band = scan.nextLine();
                    pepstmt = connection.prepareStatement("INSERT INTO \"Bands\"(band, artistic_name" +
                            ")" +
                            "VALUES (?, ?);");
                    pepstmt.setString(1, band);
                    pepstmt.setString(2, artistic_name);

                }

                pepstmt.execute();
                pepstmt.close();

                System.out.println("Artist insertion successful.");

            }else{
                System.out.println("Artist already in data base.");
            }

            sleep(2000);
        } else {
            System.out.println("You have to be an editor to change database info.");
            sleep(1000);
        }
        main_menu();

    }

    public static void insert_album() throws SQLException, InterruptedException, IOException {

        int album_id;
        String title;
        int duration;
        String launch_date;
        int tack_number;
        String artist;
        PreparedStatement pepstmt;
        clearConsole();

        if(user.getEditor() == 1){

            System.out.println("-----ADD ALBUM-----");
            album_id = get_id("Albums","album_id");
            System.out.println("Album ID: "+album_id);
            System.out.printf("Title: ");
            title = scan.nextLine();
            System.out.printf("Duration: ");
            duration = scan.nextInt();
            scan.nextLine();
            System.out.printf("Launch Date: ");
            launch_date = scan.nextLine();
            System.out.printf("Number of tracks: ");
            tack_number = scan.nextInt();
            scan.nextLine();
            System.out.printf("Artist / Band: ");
            artist = scan.nextLine();

            pepstmt = connection.prepareStatement("INSERT INTO \"Albums\"("+
                    "album_id, title, duration, launch_date, track_number, artistic_name)" +
                    "VALUES (?, ?, ?, ?, ?, ?);");
            pepstmt.setInt(1,album_id);
            pepstmt.setString(2,title);
            pepstmt.setInt(3,duration);
            pepstmt.setString(4,launch_date);
            pepstmt.setInt(5,tack_number);
            pepstmt.setString(6,artist);
            pepstmt.execute();
            pepstmt.close();

            System.out.println("Album insert successful.");

            associate_composer(album_id,"Composer_Album","album_id");
            associate_label(album_id,"Labels_Album","album_id");
            associate_genre(album_id,"Genres_Album","album_id");

            sleep(2000);


        }else{
            System.out.println("You have to be an editor to change database info.");
            sleep(1000);
        }
        main_menu();

    }

    public static void associate_composer(int id, String table, String id_type) throws SQLException, IOException, InterruptedException {

        String op = "";
        int ind;
        int lines = 0;
        ResultSet set;
        ArrayList<Artist> artists = get_artists();
        PreparedStatement pepstmt;
        clearConsole();
        System.out.println("-----ASSOCIATE COMPOSER-----\n");
        System.out.println("\n ** Select 0 to go back to main menu **");
        System.out.println("** Select -1 to skip this association **\n");


        for(int i = 0; i < artists.size(); i++){
            System.out.println("Artist ["+(i+1)+"] : "+artists.get(i).getArtistic_name());
        }

        do{
            do {
                System.out.printf("\nChoose Composer:");
                op = scan.nextLine();
                ind = Integer.parseInt(op);
            }while(ind < 0 && ind > artists.size());

            if(ind > 0 && ind <= artists.size() ){

                set = stmt.executeQuery("SELECT COUNT(*) FROM \""+table+"\" WHERE "+id_type+" = "+id+" AND artistic_name = '"+artists.get(ind-1).getArtistic_name()+"';");
                if(set.next()){
                    lines = set.getInt(1);
                }
                if(lines < 1){
                    pepstmt = connection.prepareStatement("INSERT INTO \""+table+"\"(" +
                            ""+id_type+", artistic_name)" +
                            "VALUES (?, ?);");
                    pepstmt.setInt(1,id);
                    pepstmt.setString(2,artists.get(ind-1).getArtistic_name());
                    pepstmt.execute();

                }else{
                    System.out.println("Composer already associated.");

                }

            }

        }while(ind != 0 && ind != -1);
        if(ind == 0){
            back_to_menu();
        }
    }

    public static void associate_label(int id, String table, String id_type) throws SQLException, IOException, InterruptedException {

        String op = "";
        int ind;
        int lines = 0;
        ResultSet set;
        ArrayList<Label> labels = get_labels();
        PreparedStatement pepstmt;
        clearConsole();
        System.out.println("-----ASSOCIATE LABEL-----\n");
        System.out.println("\n ** Select 0 to go back to main menu **");
        System.out.println("** Select -1 to skip this association **\n");

        for(int i = 0; i < labels.size(); i++){
            System.out.println("Label ["+(i+1)+"] : "+labels.get(i).getLabel_name());
        }

        do{
            do {
                System.out.printf("\nChoose Label:");
                op = scan.nextLine();
                ind = Integer.parseInt(op);
            }while(ind < 0 && ind > labels.size());

            if(ind > 0 && ind <= labels.size()){
                set = stmt.executeQuery("SELECT COUNT(*) FROM \""+table+"\" WHERE " + id_type + " = " + id + " AND label = '" + labels.get(ind - 1).getLabel_name() + "';");
                if(set.next()){
                    lines = set.getInt(1);
                }
                if(lines < 1){
                    pepstmt = connection.prepareStatement("INSERT INTO \""+table+"\"(" +
                            ""+id_type+", label)" +
                            "VALUES (?, ?);");
                    pepstmt.setInt(1,id);
                    pepstmt.setString(2,labels.get(ind-1).getLabel_name());
                    pepstmt.execute();

                }else{
                    System.out.println("Label already associated.");

                }
            }

        }while(ind != 0 && ind != -1);
        if(ind == 0){
            back_to_menu();
        }
    }

    public static void associate_genre(int id, String table, String id_type) throws SQLException, IOException, InterruptedException {

        String op = "";
        int ind;
        int lines = 0;
        ResultSet set;
        ArrayList<Genre> genres = get_genres();
        PreparedStatement pepstmt;
        clearConsole();
        System.out.println("-----ASSOCIATE GENRE-----\n");
        System.out.println("\n ** Select 0 to go back to main menu **\n");

        for(int i = 0; i < genres.size(); i++){
            System.out.println("Genre ["+(i+1)+"] : "+genres.get(i).getGenre());
        }

        do{
            do {
                System.out.printf("\nChoose Genre:");
                op = scan.nextLine();
                ind = Integer.parseInt(op);
            }while(ind < 0 && ind > genres.size());

            if(ind > 0 && ind <= genres.size()){

                set = stmt.executeQuery("SELECT COUNT(*) FROM \""+table+"\" WHERE " + id_type + " = " + id + " AND genre = '" + genres.get(ind - 1).getGenre() + "';");
                if(set.next()){
                    lines = set.getInt(1);
                }
                if(lines < 1){
                    pepstmt = connection.prepareStatement("INSERT INTO \""+table+"\"(" +
                            ""+id_type+", genre)" +
                            "VALUES (?, ?);");
                    pepstmt.setInt(1,id);
                    pepstmt.setString(2,genres.get(ind-1).getGenre());
                    pepstmt.execute();

                }else{
                    System.out.println("Genre already associated.");
                }
            }

        }while(ind != 0 && ind != -1);
        if(ind == 0){
            back_to_menu();
        }
    }

    public static void associate_music_album() throws SQLException, InterruptedException, IOException {

        ArrayList<Album> albums = get_albums();
        ArrayList<Music> music = get_musics();
        String ap, mp;
        int apid;
        int mpid;
        int lines=0;
        ResultSet set;
        ResultSet res;
        String art;
        PreparedStatement pepstmt;
        clearConsole();
        System.out.println("----MUSICS -> ALBUMS----\n");
        System.out.println();

        for(int i = 0; i < albums.size(); i++){
            if((set = stmt.executeQuery("SELECT \"Bands\".band , \"Albums\".title FROM \"Albums\",\"Artists\",\"Albums_Artists\",\"Bands\"" +
                    "WHERE \"Artists\".artistic_name = \"Albums_Artists\".artistic_name AND \"Albums\".album_id = \"Albums_Artists\".album_id AND \"Artists\".artistic_name = \"Bands\".artistic_name AND \"Albums\".title LIKE '%"+albums.get(i).getTitle()+"%'"+
                    "GROUP BY \"Bands\".band , \"Albums\".title;")).next()){

                if(albums.get(i).getTitle().equals(set.getString(2))){
                    art = set.getString(1);
                    albums.get(i).artist = art;
                }

            }
        }

        for(int i = 0; i < albums.size(); i++){
            System.out.println("Album ["+(i+1)+"] : "+albums.get(i).getTitle() +" by "+albums.get(i).artist);
        }
        do{
            System.out.printf("\nSelect album to associate: ");
            ap = scan.nextLine();
            apid = Integer.parseInt(ap) - 1;
        }
        while(apid < 0 || apid >= albums.size());


        System.out.println("\n");
        for(int i = 0; i < music.size(); i++){
            System.out.println("Music ["+(i+1)+"] : "+music.get(i).getTitle() +" by "+music.get(i).getArtist());
        }
        do {
            System.out.printf("\nSelect Music to associate: ");
            mp = scan.nextLine();
            mpid = Integer.parseInt(mp) - 1;
        }
        while(mpid < 0 || mpid >= music.size());

        set = stmt.executeQuery("SELECT COUNT(*)\n" +
                "      FROM \"Albums_Music\"\n" +
                "            WHERE album_id = '"+albums.get(apid).getAlbum_id()+"'\n" +
                "                    AND music_id = '"+music.get(mpid).getMusic_id()+"' ;");
        if(set.next()){
            lines = set.getInt(1);
        }
        if(lines < 1){
            pepstmt = connection.prepareStatement("INSERT INTO public.\"Albums_Music\"(\n" +
                    "\tmusic_id, album_id)\n" +
                    "\tVALUES (?, ?);");
            pepstmt.setInt(1,music.get(mpid).getMusic_id());
            pepstmt.setInt(2,albums.get(apid).getAlbum_id());
            pepstmt.execute();
            pepstmt.close();
            System.out.println("Associated "+music.get(mpid).getTitle()+" to "+albums.get(apid).getTitle());
            sleep(2000);
        }else{
            System.out.println("Album and music are already associated to each other.");
            sleep(1000);
        }
        main_menu();
    }

    public static void add_critic(int id, String table, String id_type) throws SQLException, InterruptedException, IOException {

        int critic_id;
        String critic;
        PreparedStatement pepstmt;
        clearConsole();
        System.out.println("----ADD CRITIC----\n");
        critic_id = get_id(table,"critic_id");
        System.out.println("Critic number "+critic_id);
        System.out.printf("Write a critic, be nice: ");
        critic = scan.nextLine();

        pepstmt = connection.prepareStatement("INSERT INTO public.\""+table+"\"(" +
                "critic_id, critic, email, "+id_type+")" +
                "VALUES (?, ?, ?, ?);");
        pepstmt.setInt(1,critic_id);
        pepstmt.setString(2,critic);
        pepstmt.setString(3,user.getUsername());
        pepstmt.setInt(4,id);
        pepstmt.execute();
        pepstmt.close();
        System.out.println("Critic input successful.");
        sleep(2000);
        main_menu();

    }

    public static void delete_account() throws InterruptedException, SQLException, IOException {

        ArrayList<Playlist> playlists;
        String choice = "";
        PreparedStatement pepstmt;
        clearConsole();
        System.out.println("----DELETE ACCOUNT----");
        System.out.println("\nAre you sure you want to delete you account? (y / n)\n");
        do {
            System.out.printf("-> ");
            choice = scan.nextLine();
        }while(!choice.toUpperCase().equals("Y") && !choice.toUpperCase().equals("N"));

        if(choice.toUpperCase().equals("Y")){

            playlists = get_playlists(2);
            for(int i = 0; i < playlists.size();i++){
                pepstmt = connection.prepareStatement("DELETE FROM \"Playlists_Musics\" WHERE playlist_id ="+playlists.get(i).getPlaylist_id()+";");
                pepstmt.execute();
            }
            pepstmt = connection.prepareStatement("DELETE FROM \"Album_Critics\" WHERE email = '"+user.getUsername()+"';");
            pepstmt.execute();
            pepstmt = connection.prepareStatement("DELETE FROM \"Music_Critics\" WHERE email = '"+user.getUsername()+"';");
            pepstmt.execute();
            pepstmt = connection.prepareStatement("DELETE FROM \"Playlists\" WHERE email = '"+user.getUsername()+"';");
            pepstmt.execute();
            pepstmt = connection.prepareStatement("DELETE FROM \"Users\" WHERE email = '"+user.getUsername()+"';");
            pepstmt.execute();
            pepstmt.close();
            System.out.println("All information deleted succesfully. See you soon :(");
            sleep(1000);
            System.exit(1);

        }else{
            System.out.println("Good you choose to stay. :)\n");
            sleep(2000);
            main_menu();
        }

    }

    public static int get_id(String table,String id_type) throws SQLException {

        int id = 0;
        res = stmt.executeQuery("SELECT \""+table+"\"."+id_type+" FROM \""+table+"\";");
        while(res.next()){
            if(id < res.getInt(1)){
                id = res.getInt(1);
            }
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