package proje.otelrezervasyonuygulamasi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class OtelBilgiUIController extends GirisController implements Initializable {

    @FXML
    private TextField otelIsım;

    @FXML
    private TextField otelKonum;

    @FXML
    private TextField otelOda;

    @FXML
    private ChoiceBox<String> otelTur;
    DbHelper db = new DbHelper();

    public OtelBilgiUIController() throws IOException, ClassNotFoundException
    {
    }

    @FXML
    void bilgiGuncelle(ActionEvent event) throws IOException {
        db.sqlUpdateOtel(GirisController.activeYonetici.getOtel().getOtelID(),otelIsım.getText(),Integer.parseInt(otelOda.getText()),
                otelKonum.getText(),otelTur.getValue());
        ((Node) event.getSource()).getScene().getWindow().hide();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        String[] turler = {"Aile", "Ekonomik", "Bungalov", "Aquapark"};
        otelTur.getItems().addAll(turler);
    }
}
