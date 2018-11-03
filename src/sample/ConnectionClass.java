package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ConnectionClass {
    public Connection connection;
    public String driver="com.mysql.jdbc.Driver";
    public String userName="root";
    public String password="";
    public String url="jdbc:mysql://localhost:3306/warehouse?autoReconnect=true&useSSL=false";

    public ConnectionClass getConnetion(){
        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(url, this.userName, this.password);
            String query = "insert into goods(name, piece, country) values ('teraz', 2, 'SK')";

            PreparedStatement ps= connection.prepareStatement(query);
            ps.executeUpdate();
            System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
//
//    public ObservableList getGoods(){
//        try {
//            Class.forName(driver).newInstance();
//            connection = DriverManager.getConnection(url, this.userName, this.password);
//
//            String query = "select * from goods";
//            PreparedStatement ps = connection.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//            if(rs.next()){
//                ObservableList users =new Users(rs.getString("firstname"),rs.getString("lastname"),rs.getString("login"),rs.getString("email"));
//                ObservableList list = FXCollections.observableList(
//
//                )
//                ps = connection.prepareStatement(query);
//                ps.setString(1, users.getToken());
//                ps.setInt(2,rs.getInt("id"));
//
//                ps.executeUpdate();
//                System.out.println(ps);
//                return users;
//            }
//            return null;
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
