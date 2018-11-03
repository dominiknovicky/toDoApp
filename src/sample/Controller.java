package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static javax.swing.JOptionPane.showMessageDialog;

public class Controller implements Initializable {
    public Button odoslat;
    public Button delete;
    public TextField zadajMeno;
    public TextField zadajPocetKusov;
    public ChoiceBox zadajKrajinu;
    public TextField nazovTovaru;
    public TextField pocetKusov;
    public ChoiceBox krajinaPovodu;
    public TableView<Good> table;
    public ObservableList<Good> list = FXCollections.observableArrayList();
    public ObservableList<Good> list2 = FXCollections.observableArrayList();

    private Connection conn;
    private String driver = "com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/warehouse?useSSL=false";
    private String username="root";
    private String password="";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Good, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(180);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Good, String> nameColumn = new TableColumn<>("Meno");
        nameColumn.setMinWidth(180);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Good, String> pieceColumn = new TableColumn<>("Pocet kusov");
        pieceColumn.setMinWidth(180);
        pieceColumn.setCellValueFactory(new PropertyValueFactory<>("piece"));

        TableColumn<Good, String> countryColumn = new TableColumn<>("Krajina");
        countryColumn.setMinWidth(180);
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        VBox vBox = new VBox();
        vBox.getChildren().addAll();
        table.getColumns().addAll(idColumn,nameColumn,pieceColumn,countryColumn);
    }


    public void vkladTovaru(ActionEvent event) {
        String country = String.valueOf(krajinaPovodu.getValue());
        String name = String.valueOf(nazovTovaru.getText());
        String piece = String.valueOf(pocetKusov.getText());

        if (country == "null" && name.length() == 0 && piece.length() == 0){
            showMessageDialog(null, "Vypln vsetky polia.");

        } else {
//            list.put("country", krajinaPovodu.getValue());
//            list.put("name", nazovTovaru.getText());
//            list.put("piece", pocetKusov.getText());
//
//            try {
//                com.mashape.unirest.http.HttpResponse<String> response = Unirest.post("http://localhost:8080/api/v1/good")
//                        .header("Content-Type", "application/json")
//                        .header("cache-control", "no-cache")
//                        .body(list.toString())
//                        .asString();
//                showMessageDialog(null, "OK");
//                krajinaPovodu.setValue(null);
//                nazovTovaru.setText("");
//                pocetKusov.setText("");
//
//            } catch (UnirestException e) {
//                e.printStackTrace();
//                showMessageDialog(null, "Skus znova");
//                krajinaPovodu.setValue(null);
//                nazovTovaru.setText("");
//                pocetKusov.setText("");
//            }

            try {
                conn = DriverManager.getConnection(url, this.username, this.password);
                String query = "insert into goods(name, piece, country) values (?,?,?)";

                PreparedStatement ps= conn.prepareStatement(query);
                ps.setString(1,name);
                ps.setInt(2, Integer.parseInt(piece));
                ps.setString(3,country);
                ps.executeUpdate();

                showMessageDialog(null, "OK");
                krajinaPovodu.setValue(null);
                nazovTovaru.setText("");
                pocetKusov.setText("");
            }catch(Exception e){
                e.printStackTrace();
                showMessageDialog(null, "Skus znova");
                krajinaPovodu.setValue(null);
                nazovTovaru.setText("");
                pocetKusov.setText("");
            }
        }
    }

    public void sklad() {

        list.clear();
//        try {
//            HttpResponse<String> response = Unirest.get("http://localhost:8080/api/v1/good")
//                    .header("cache-control", "no-cache")
//                    .asString();
//
//            JSONArray jsonarray = new JSONArray(response.getBody());
//            for(int i=0; i<jsonarray.length(); i++){
//                JSONObject obj = jsonarray.getJSONObject(i);
//
//                int id = obj.getInt("id");
//                String name = obj.getString("name");
//                int piece = obj.getInt("piece");
//                String country = obj.getString("country");
//
//                list.add(new Good(id,name,piece,country));
//            }
//            table.setItems(list);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            conn = DriverManager.getConnection(url, this.username, this.password);

            String query = "select * from goods;";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Good good =new Good(rs.getInt("id"), rs.getString("name"), rs.getInt("piece"), rs.getString("country"));
                list.add(good);
            }
            table.setItems(list);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteRowFromTable(ActionEvent e) {
//        try {
//            int id = (table.getSelectionModel().getSelectedItem()).getId();
//            list.clear();
//            HttpResponse<String> response = Unirest.delete("http://localhost:8080/api/v1/good/"+id+"good")
//                    .header("cache-control", "no-cache")
//                    .asString();
//            sklad();
//        } catch (Exception e1) {
//            showMessageDialog(null, "Vyber element.");
//
//        }
        try {
            int id = (table.getSelectionModel().getSelectedItem()).getId();
            conn = DriverManager.getConnection(url, this.username, this.password);
            String query = "delete from goods where id like "+id;
            PreparedStatement ps = conn.prepareStatement(query);
            System.out.println(query);
            ps.executeUpdate();
            sklad();
        } catch (Exception e1) {
            showMessageDialog(null, "Vyber element.");
        }
    }

    public void getDataFromRow(){
        int piece = (table.getSelectionModel().getSelectedItem()).getPiece();
        String name = (table.getSelectionModel().getSelectedItem()).getName();
        String country = (table.getSelectionModel().getSelectedItem()).getCountry();
        zadajMeno.setText(name);
        zadajPocetKusov.setText(String.valueOf(piece));
        zadajKrajinu.setValue(country);
    }

    public void updateData(){
        String country = String.valueOf(zadajKrajinu.getValue());
        String name = String.valueOf(zadajMeno.getText());
        String piece = String.valueOf(zadajPocetKusov.getText());
        int id = (table.getSelectionModel().getSelectedItem()).getId();

        if (country == "null" && name.length() == 0 && piece.length() == 0){
            showMessageDialog(null, "Vypln vsetky polia.");

        } else {

            try {
                conn = DriverManager.getConnection(url, this.username, this.password);
                String query = "update goods set name=?, piece=piece+?, country=? where id like ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1,name);
                ps.setInt(2, Integer.parseInt(piece));
                ps.setString(3,country);
                ps.setInt(4,id);
                ps.executeUpdate();
                sklad();
                zadajPocetKusov.setText("");
                zadajMeno.setText("");
                zadajKrajinu.setValue(null);
                showMessageDialog(null, "OK");
            }catch(Exception e){
                e.printStackTrace();
                showMessageDialog(null, "Skus znova");
            }

        }
    }
}