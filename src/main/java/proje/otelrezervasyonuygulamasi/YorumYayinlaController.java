package proje.otelrezervasyonuygulamasi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class YorumYayinlaController extends GirisController implements Initializable {

    @FXML
    private TextArea kullaniciYorum;

    @FXML
    private ComboBox<String> otel;

    ObservableList<Integer> otelIDleri;
    ObservableList<String> otelIsimleri;

    public YorumYayinlaController() throws IOException, ClassNotFoundException {
    }

    void otelAra() {
        DbHelper db = new DbHelper(); // Veritabanı bağlantı sınıfı
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;
        otelIDleri = FXCollections.observableArrayList();
        otelIsimleri = FXCollections.observableArrayList();

        try {
            connection = db.getConnection();
            String query = "SELECT Otel.otelID ,Otel.otelIsim FROM  Otel JOIN Rez ON Rez.OtelID = Otel.otelID WHERE Rez.KullaniciID = ?"; // SQL sorgusu
            statement = connection.prepareStatement(query);
            statement.setInt(1, GirisController.girisYapanKullaniciID); // Kullanıcının seçtiği otel türünü sorguya ekle
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int otelID = resultSet.getInt("otelID");
                String otelIsim = resultSet.getString("otelIsim");

                otelIDleri.add(otelID);
                otelIsimleri.add(otelIsim);

            }

            otel.setItems(otelIsimleri); // Listeyi arayüzde göster
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
    void yorumYap(ActionEvent event) throws IOException {
        String yorumIcerik = kullaniciYorum.getText();
        int kullaniciID = GirisController.girisYapanKullaniciID; // Giriş yapan kullanıcının ID'si
        String secilenOtelIsim = otel.getValue();
        int secilenIndis = otel.getSelectionModel().getSelectedIndex();
        int otelID = otelIDleri.get(secilenIndis);


        DbHelper db = new DbHelper();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;


        try {
            connection = db.getConnection();
            String query = "INSERT INTO YORUM (musteriID, OtelID, icerik) VALUES (?, ?, ?)"; // Rez tablosuna ekleme sorgusu
            statement = connection.prepareStatement(query);
            statement.setInt(1, kullaniciID); // Müşteri ID'si
            statement.setInt(2, otelID); // Otel ID'si
            statement.setString(3, yorumIcerik);
            statement.executeUpdate();

            System.out.println("Yorum başarıyla yapıldı" + kullaniciID + ", Otel ID: " + otelID);
            kullaniciYorum.clear();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        otelAra();
    }
}
