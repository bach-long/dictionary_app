import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.String;

public class database_manage {
    static Connection c = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void set_database() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./src/main/resources/dict_avva.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void close_database() throws SQLException {
        rs.close();
        stmt.close();
        c.close();
    }

    public static String search() throws SQLException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s="";
        try {
            rs = stmt.executeQuery(String.format("select * from av where word = '%s'",br.readLine().toLowerCase()));
            String word = rs.getString("word");
            String prc = rs.getString("pronounce");
            s = s + word + "\n"  + prc + "\n";
            while(rs.next()) {
                String drc = rs.getString("description");
                s = s + drc + "\n";
            }
            rs.close();
        } catch (IOException e) {
            System.out.println( e.getMessage() );
            rs.close();
        }
        return s;
    }
}

