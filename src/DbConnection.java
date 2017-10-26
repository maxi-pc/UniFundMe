/**
 * Created by Maxi on 10/22/2017.
 * this java class connects to the database and creates error exceptions if something is wrong.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {

    public Connection Connect(){
        try {
            String url="jdbc:mysql://158.69.54.97:3306/maxpme_UniFundMe";
            String user="maxpme_root";
            String password="csis2017";

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn= DriverManager.getConnection(url, user, password);
            return conn;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
