package proje.otelrezervasyonuygulamasi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class YoneticiUIController extends GirisController{

    public YoneticiUIController() throws IOException, ClassNotFoundException {
    }

    @FXML
    void bilgiGuncelle(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("OtelBilgiUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void rezervasyonIptal(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("RezervasyonSilUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void rezervasyonListele(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("YoneticiRezervasyonKontrolUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void yorumGoruntule(ActionEvent event) throws IOException {
        // UI HAZIRLANACAK
          FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("YoneticiYorumGoruntule.fxml"));
          Scene scene = new Scene(fxmlLoader.load());
          Stage stage = new Stage();
          stage.setScene(scene);
          stage.show();
    }

}
