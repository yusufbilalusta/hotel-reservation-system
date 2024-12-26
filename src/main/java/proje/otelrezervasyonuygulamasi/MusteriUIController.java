package proje.otelrezervasyonuygulamasi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MusteriUIController {

    @FXML
    void bulundugunOtelListele(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("RezervasyonListeUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }



    @FXML
    void rezervasyonYap(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("RezervasyonYap.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void yorumYap(ActionEvent event) throws IOException {

         FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("YorumYap.fxml"));
         Scene scene = new Scene(fxmlLoader.load());
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.show();

    }

    @FXML
    void yorumlariGoster(ActionEvent event) throws IOException {
         FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MusteriYorumGoruntule.fxml"));
         Scene scene = new Scene(fxmlLoader.load());
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.show();

    }

}
