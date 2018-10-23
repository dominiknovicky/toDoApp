package sample;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;

import static javax.swing.JOptionPane.showMessageDialog;

public class Controller {
    public Button odoslat;
    public TextField nazovTovaru;
    public TextField pocetKusov;
    public ChoiceBox krajinaPovodu;

    public void vkladTovaru(ActionEvent event) throws JSONException {
        JSONObject list =new JSONObject();
        list.put("country", krajinaPovodu.getValue());
        list.put("name", nazovTovaru.getText());
        list.put("piece", pocetKusov.getText());
        try {
            com.mashape.unirest.http.HttpResponse<String> response = Unirest.post("http://localhost:8080/api/v1/good")
                    .header("Content-Type", "application/json")
                    .header("cache-control", "no-cache")
                    .header("Postman-Token", "259977ed-6459-43d2-958f-2dfd35f5e023")
                    .body(list.toString())
                    .asString();
            showMessageDialog(null, "OK");
            krajinaPovodu.setValue(null);
            nazovTovaru.setText("");
            pocetKusov.setText("");

        } catch (UnirestException e) {
            e.printStackTrace();
            showMessageDialog(null, "Skus znova");
            krajinaPovodu.setValue(null);
            nazovTovaru.setText("");
            pocetKusov.setText("");
        }
    }
}
