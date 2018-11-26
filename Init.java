import java.sql.*;

/**
 * author: André Leite
 */

import static java.lang.Thread.sleep;


public class Init {

    public Connection connection;

    public Init() throws Exception {

        Statement stmt = null;
        ResultSet set = null;
        ResultSetMetaData meta = null;

        connection = getConnection();
        try{

            if((stmt = connection.createStatement()) == null) {
                System.out.println("Erro nao foi possível criar uma statement ou retornou null");
                System.exit(-1);
            }
            init(stmt,connection);

        }catch (NullPointerException e){
            System.out.println(e.getCause());
        }

    }

    public static Connection getConnection() throws Exception {

        try{
            String driver = "org.postgresql.Driver";
            String url = "jdbc:postgresql://192.168.1.6:5432/postgres";
            String username = "postgres";
            String password = "root";
            Class.forName(driver);
            Connection comm = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return comm;
        }catch (Exception e){
            System.out.println(e.fillInStackTrace());
        }

        try{
            String driver = "org.postgresql.Driver";
            String url = "jdbc:postgresql://192.168.1.7:5432/postgres";
            String username = "postgres";
            String password = "root";
            Class.forName(driver);
            Connection comm = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return comm;
        }catch (Exception e){
            System.out.println(e.fillInStackTrace());
        }


        return null;
    }

    public static void init(Statement stmt, Connection c)throws Exception{

        ResultSet set = null;
        ResultSetMetaData meta = null;

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Users\"(Email VARCHAR(255) PRIMARY KEY NOT NULL," +
                "Password VARCHAR(255) NOT NULL," +
                "Nome VARCHAR(255) NOT NULL," +
                "Permit INT NOT NULL);");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Playlists\"(Playlist_id INT PRIMARY KEY NOT NULL," +
                "Email VARCHAR(255) REFERENCES \"Users\"(Email)," +
                "Titulo VARCHAR(255) NOT NULL," +
                "Musics_m INT NOT NULL," +
                "Privacidade INT NOT NULL);");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Artists\"(Artistic_name VARCHAR(255) PRIMARY KEY NOT NULL," +
                "Nome VARCHAR(255) NOT NULL," +
                "Origin VARCHAR(255) NOT NULL," +
                "Active_years INT NOT NULL," +
                "Biography VARCHAR(255) NOT NULL," +
                "Concertos VARCHAR(255) NOT NULL);");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Bands\"(Band VARCHAR(255) NOT NULL," +
                "Artistic_name VARCHAR(255) REFERENCES \"Artists\"(Artistic_name));");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Genres\"(Genre VARCHAR(255) PRIMARY KEY NOT NULL);");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Labels\"(Label VARCHAR(255) PRIMARY KEY NOT NULL);");


        stmt.execute("CREATE TABLE IF NOT EXISTS \"Musics\"(Music_id INT PRIMARY KEY NOT NULL," +
                "Title VARCHAR(255) NOT NULL," +
                "Duration INT NOT NULL," +
                "Launch_Date VARCHAR(255) NOT NULL," +
                "Views INT NOT NULL,"+
                "Lyrics VARCHAR(255) NOT NULL,"+
                "Format VARCHAR(255) NOT NULL);");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Albums\"(Album_id INT PRIMARY KEY NOT NULL," +
                "Title VARCHAR(255) NOT NULL," +
                "Duration INT NOT NULL," +
                "Launch_Date VARCHAR(255) NOT NULL," +
                "Track_Number INT NOT NULL);");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Album_Critics\"(Critic_id INT PRIMARY KEY NOT NULL," +
                "Critic VARCHAR(255) NOT NULL," +
                "Email VARCHAR(255) REFERENCES \"Users\"(Email)," +
                "Album_id INT REFERENCES \"Albums\"(Album_id));");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Music_Critics\"(Critic_id INT PRIMARY KEY NOT NULL," +
                "Critic VARCHAR(255) NOT NULL," +
                "Email VARCHAR(255) REFERENCES \"Users\"(Email)," +
                "Music_id INT REFERENCES \"Musics\"(Music_id));");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Composers\"(Music_id INT REFERENCES \"Musics\"(Music_id)," +
                "Artistic_name VARCHAR(255) REFERENCES \"Artists\"(Artistic_name));");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Composers_Album\"(Album_id INT REFERENCES \"Albums\"(Album_id)," +
                "Artistic_name VARCHAR(255) REFERENCES \"Artists\"(Artistic_name));");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Genres_Music\"(Music_id INT REFERENCES \"Musics\"(Music_id)," +
                "Genre VARCHAR(255) REFERENCES \"Genres\"(genre));");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Genres_Album\"(Album_id INT REFERENCES \"Albums\"(Album_id)," +
                "Genre VARCHAR(255) REFERENCES \"Genres\"(genre));");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Labels_Music\"(Music_id INT REFERENCES \"Musics\"(Music_id)," +
                "label VARCHAR(255) REFERENCES \"Labels\"(label));");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Labels_Album\"(Album_id INT REFERENCES \"Albums\"(Album_id)," +
                "label VARCHAR(255) REFERENCES \"Labels\"(label));");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Albums_Music\"(Music_id INT REFERENCES \"Musics\"(Music_id)," +
                "Album_id INT REFERENCES \"Albums\"(Album_id));");

        stmt.execute("CREATE TABLE IF NOT EXISTS \"Albums_Artists\"(Album_id INT REFERENCES \"Albums\"(Album_id)," +
                "Artistic_name VARCHAR(255) REFERENCES \"Artists\"(Artistic_name));");


        stmt.execute("CREATE TABLE IF NOT EXISTS \"Playlists_Musics\"(Playlist_id INT REFERENCES \"Playlists\"(Playlist_id)," +
                "Music_id INT REFERENCES \"Musics\"(Music_id));");

        System.out.println("Data Base updated.");

    }

    public static void terminate_database(Statement stmt, Connection c) throws Exception{

        ResultSet set = null;
        ResultSetMetaData meta = null;
        String[] tables = {"Users","Album_Critics","Artists","Albums",
                "Bands","Genres", "Music_Critics","Musics","Playlists",
                "Albums_Music","Albums_Artist","Composers","Composers_Album",
                "Genres_Album","Genres_Music","Labels","Labels_Music","Labels_Album",
                "Playlists_Musics"};

        for(int i = 0; i < tables.length; i++){

            stmt.execute("DELETE FROM "+"\""+tables[i]+"\";");
            stmt.execute("DROP TABLE IF EXISTS "+"\""+tables[i]+"\" CASCADE;");
            System.out.println("Cleaning and deleting "+tables[i]+" table");
        }
        System.out.println("Data base tables were eliminated.");
    }


}