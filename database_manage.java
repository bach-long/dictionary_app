package dictionary;


/**Điều chỉnh lại hàm delete
 * thêm hàm add memo
 */

import java.sql.*;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.*;

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

    /** tim kiem trong database goc, table name la av hoac va, tra ve [0] la nghia va [1] la comment. */
    public static String[] search(String r, String table_name) throws SQLException{
        String[] result = new String[2];
        r = r.trim();
        r = r.replaceAll("\\s+","");
        result[0] = "";
        result[1] = "";
        rs = stmt.executeQuery(String.format("select * from %s where word = '%s'",table_name, r.toLowerCase()));
        int i = 0;
        while (rs.next()) {
            if(i == 0) {
                result[1] = result[1] + rs.getString("comment");
            }
            String drc = rs.getString("html");
            result[0] = result[0] + drc + "\n";
            i++;
        }
        if(result[0] == ""){
            rs.close();
            result[0] = "<h1 style=\"color:Tomato;\"> This word doesn't exist </h1>";
        }
        if(rs == null) {}
        else
        rs.close();
        return result;

    }

    /** list cac tu.*/
    public static String[] list_word() throws SQLException {
        rs = stmt.executeQuery("select * from av");
        Set<String> a = new LinkedHashSet<> ();
        while(rs.next()) {
            a.add(rs.getString("word"));
        }
        rs = stmt.executeQuery("select * from user");
        while(rs.next()) {
            a.add(rs.getString("word"));
        }
        String[] result = new String [a.size()];
        result = a.toArray(result);
        rs.close();
        return result;
    }

    /** nguoi dung them vao , table_name la table dc them.*/
    public static void user_add (String word, String mean) throws SQLException {
        word = word.replace("\n","<br>");
        mean = mean.replace("\n","<br>");
        word = word.trim();
        mean = mean.trim();
        word = word.replaceAll("\\s+"," ");
        mean = mean.replaceAll("\\s+"," ");
        mean = mean + "<br>";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        pstsm = c.prepareStatement("INSERT INTO USER (word, definition, time) VALUES (?,?,?)");
        pstsm.setString(1,word.toLowerCase());
        pstsm.setString(2,mean.toLowerCase());
        pstsm.setString(3,sdf.format(timestamp));
        pstsm.executeUpdate();
        if (pstsm == null){}
        else {
            pstsm.close();
        }
    }

    /**add word vao trong mot memo da duoc tao*/
    public static void add_memo (String word, String memo) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        pstsm = c.prepareStatement(String.format("INSERT INTO %s (word, time) VALUES (?,?)",memo));
        pstsm.setString(1,word.toLowerCase());
        pstsm.setString(2,sdf.format(timestamp));
        pstsm.executeUpdate();
        if (pstsm == null){}
        else {
            pstsm.close();
        }
    }

    /**comment to origin table
     * co the la av hoac va. */
    public static void comment(String s, String word, String table_name) throws SQLException {
        s = s.replace("\n","<br>");
        s = "<h4>" +s + "</h4>" + "<br>";
        pstsm = c.prepareStatement(String.format("UPDATE %s SET COMMENT = ? WHERE word = ?",table_name));
        pstsm.setString(1,s);
        pstsm.setString(2,word);
        pstsm.executeUpdate();
        if (pstsm == null){}
        else {
            pstsm.close();
        }
    }

    /** nguoi dung chinh sua (chi doi voi table add).*/
    public static void make_change (String word, String mean) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        word = word.replace("\n","<br>");
        mean = mean.replace("\n","<br>");
        word = word.trim();
        mean = mean.trim();
        word = word.replaceAll("\\s+"," ");
        mean = mean.replaceAll("\\s+"," ");
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

    /** xoa trong cac table memo cua user (ko cho phep xoa add , av, va) */
    public static void user_delete (String word, String table_name) throws SQLException {
        pstsm = c.prepareStatement(String.format("DELETE FROM %s WHERE word = ?", table_name));
        word = word.replace("\n", "<br>");
        word = word.trim();
        word = word.replaceAll("\\s+", " ");
        pstsm.setString(1, word);
        pstsm.executeUpdate();
        if (pstsm == null) {
        } else {
            pstsm.close();
        }
    }

    /**tim kiem trong table add.*/
    public static String user_search(String r) throws SQLException {
        String s="";
        r = r.trim();
        r = r.replaceAll("\\s+"," ");
        rs = stmt.executeQuery(String.format("select * from user where word = '%s'", r.toLowerCase()));
        while (rs.next()) {
            String drc = rs.getString("definition");
            s = s + drc;
        }
        if (s == "") {
            return "<h1 style=\"color:Tomato;\"> This word doesn't exist </h1>";
        }
        if(rs == null) {}
        else
        rs.close();
        return "<h4>" + s + "</h4>";
    }

    /** nhan tham so la ten table va can co mot list_name de truyen
     * tra ve false neu nhom da ton tai
     * them nhom va tra ve true trong TH nguoc lai.*/
    public static boolean add_group(String s) throws SQLException {
        ArrayList<String> a = get_list("name");
        s = s.replaceAll("\\s+","_");
        if(a.contains(s)) {
            return false;
        }
        else {
            pstsm = c.prepareStatement("CREATE TABLE " + s + " (" +
                    "   word           TEXT," +
                    "   time           TEXT" +
                    ");");
            pstsm.executeUpdate();
            pstsm = c.prepareStatement("Insert into name (word) values (?)");
            pstsm.setString(1,s);
            pstsm.executeUpdate();
            if (pstsm == null) {
            } else {
                pstsm.close();
            }
            return true;
        }
    }

    public static void delete_table(String table_name) throws SQLException {
        table_name = table_name.replaceAll("\\s+","_");
        pstsm = c.prepareStatement(String.format("Drop table %s", table_name));
        pstsm.executeUpdate();
        pstsm = c.prepareStatement("Delete from name where word = ?");
        pstsm.setString(1,table_name);
        pstsm.executeUpdate();
        if(pstsm == null) {
        } else {
            pstsm.close();
        }
    }


    /**tra ve mot arrayList cac word o mot table duoc chon. */
    public static ArrayList<String> get_list(String table_name) throws SQLException {
        rs = stmt.executeQuery(String.format("select * from %s",table_name));
        ArrayList<String> s = new ArrayList<>();
        while(rs.next()) {
            s.add(rs.getString("word"));
        }
        rs.close();
        return s;
    }
}

