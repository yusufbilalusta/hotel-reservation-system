package proje.otelrezervasyonuygulamasi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RezervasyonYapController extends GirisController implements Initializable {
    ObservableList<Integer> otelIDleri;
    ObservableList<String> otelIsimleri;
    @FXML
    private ChoiceBox<String> otelTuru;

    @FXML
    private ChoiceBox<String> oteller;

    public RezervasyonYapController() throws IOException, ClassNotFoundException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] turler = {"Aile", "Ekonomik", "Bungalov", "Aquapark"};
        otelTuru.getItems().addAll(turler);
    }

    @FXML
    void rezervasyonYap(ActionEvent event) throws IOException {
        // Kullanıcı ve otel bilgilerini kontrol et
        String secilenOtelIsim = oteller.getSelectionModel().getSelectedItem(); // Seçilen otel ismi
        int secilenIndis = oteller.getSelectionModel().getSelectedIndex();

        int kullaniciID = GirisController.girisYapanKullaniciID; // Giriş yapan kullanıcının ID'si
        System.out.println(kullaniciID);
        System.out.println("Secilen otel: " + secilenOtelIsim);

        // Seçilen otel ismine karşılık gelen otelID'yi bul
        int otelID = otelIDleri.get(secilenIndis);
        if (otelID == -1) {
            System.out.println("Otel ID bulunamadı.");
            return;
        }

        // Rezervasyonu veritabanına ekle
        DbHelper db = new DbHelper();
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = db.getConnection();
            String query = "INSERT INTO Rez (KullaniciID, OtelID) VALUES (?, ?)"; // Rez tablosuna ekleme sorgusu
            statement = connection.prepareStatement(query);
            statement.setInt(1, kullaniciID); // Müşteri ID'si
            statement.setInt(2, otelID); // Otel ID'si
            statement.executeUpdate();

            System.out.println("Rezervasyon başarıyla oluşturuldu! Kullanıcı ID: " + kullaniciID + ", Otel ID: " + otelID);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void otelAra(ActionEvent event) {
        String turu = otelTuru.getSelectionModel().getSelectedItem(); // Kullanıcının seçtiği otel türü
        DbHelper db = new DbHelper(); // Veritabanı bağlantı sınıfı
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;
        otelIDleri = FXCollections.observableArrayList();
        otelIsimleri = FXCollections.observableArrayList();

        try {
            connection = db.getConnection();
            String query = "SELECT otelID, otelIsim FROM Otel WHERE otelTuru = ?"; // SQL sorgusu
            statement = connection.prepareStatement(query);
            statement.setString(1, turu); // Kullanıcının seçtiği otel türünü sorguya ekle
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int otelID = resultSet.getInt("otelID");
                String otelIsim = resultSet.getString("otelIsim");

                otelIDleri.add(otelID);
                otelIsimleri.add(otelIsim);

            }

            oteller.setItems(otelIsimleri); // Listeyi arayüzde göster
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

