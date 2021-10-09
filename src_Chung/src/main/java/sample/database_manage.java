package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.sql.Timestamp;

public class database_manage {
    static Connection c = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    static PreparedStatement pstsm = null;

    /** khoi tao. */
    public static void set_database() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:./src/main/resources/dict_avva.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /** dong. */
    public static void close_database() throws SQLException {
        if(rs == null) {}
        else {
            rs.close();
        }
        if(pstsm == null){}
        else {
            pstsm.close();
        }
        stmt.close();
        c.close();
    }

    /** tim kiem trong database goc. */
    public static String search(String r) throws SQLException{
        String s="";
        rs = stmt.executeQuery(String.format("select * from av where word = '%s'", r.toLowerCase()));
        while (rs.next()) {
            String drc = rs.getString("html");
            s = s + drc + "\n";

        }
        if(rs==null) {}
        else {
            rs.close();
        }
        return s;
    }

    /** list cac tu.*/
    public static String[] list_word() throws SQLException {
        rs = stmt.executeQuery("select * from av");
        Set<String> a = new LinkedHashSet<> ();
        while(rs.next()) {
            a.add(rs.getString("word"));
        }
        String[] result = new String [a.size()];
        result = a.toArray(result);
        rs.close();
        return result;
    }

    /** nguoi dung them vao.*/
    public static void user_add (String word, String mean) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        pstsm = c.prepareStatement("INSERT INTO user (word, definition, time) VALUES (?,?,?)");
        pstsm.setString(1,word.toLowerCase());
        pstsm.setString(2,mean.toLowerCase());
        pstsm.setString(3,sdf.format(timestamp));
        pstsm.executeUpdate();
        if (pstsm == null){}
        else {
            pstsm.close();
        }
    }

    /** nguoi dung chinh sua.*/
    public static void make_change (String word, String mean) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        pstsm = c.prepareStatement("UPDATE user SET DEFINITION = ?, time = ? WHERE word = ?");
        pstsm.setString(1,mean.toLowerCase());
        pstsm.setString(2, sdf.format(timestamp));
        pstsm.setString(3,word.toLowerCase(Locale.ROOT));
        pstsm.executeUpdate();
        if (pstsm == null){}
        else {
            pstsm.close();
        }
    }

    /** xoa. */
    public static void user_delete (String s) throws SQLException {
        pstsm = c.prepareStatement("DELETE FROM user WHERE word = ?");
        pstsm.setString(1,s);
        pstsm.executeUpdate();
        if (pstsm == null){}
        else {
            pstsm.close();
        }
    }

    /**tim kiem trong database cua nguoi dung.*/
    public static String user_search(String r) throws SQLException {
        String s="";
        rs = stmt.executeQuery(String.format("select * from user where word = '%s'", r.toLowerCase()));
        while (rs.next()) {
            String drc = rs.getString("definition");
            s = s + drc + "\n";
        }
        if(rs == null) {}
        else
            rs.close();
        return s;
    }
}
