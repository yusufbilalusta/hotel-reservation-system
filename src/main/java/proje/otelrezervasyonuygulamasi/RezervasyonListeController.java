package proje.otelrezervasyonuygulamasi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RezervasyonListeController extends GirisController implements Initializable {
    @FXML
    private TableView<Otel> Tablo;
    @FXML
    private TableColumn<Otel, String> isimColumn;

    @FXML
    private TableColumn<Otel, String> odaNoColumn;

    @FXML
    private TableColumn<Otel, String> otelTuruColumn;

    @FXML
    private TableColumn<Otel, String> konumColumn;


    public RezervasyonListeController() throws IOException, ClassNotFoundException {
    }

    public ObservableList<Otel> getRezervasyonlar() {
        int kullaniciID = GirisController.girisYapanKullaniciID; // Giriş yapan kullanıcının ID'si
        ObservableList<Otel> liste = FXCollections.observableArrayList();

        DbHelper db = new DbHelper();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = db.getConnection();

            // Kullanıcıya ait otel bilgilerini almak için JOIN sorgusu
            String query = """
                SELECT Otel.otelIsim, Otel.odaSayisi, Otel.otelTuru, Otel.otelKonum
                FROM Rez
                JOIN Otel ON Otel.otelID = Rez.otelID
                WHERE Rez.kullaniciID = ?
            """;

            statement = connection.prepareStatement(query);
            statement.setInt(1, kullaniciID); // Kullanıcı ID'sini sorguya ekle
            resultSet = statement.executeQuery();

            // Otel bilgilerini listeye ekle
            while (resultSet.next()) {
                String otelIsim = resultSet.getString("otelIsim");
                int odaSayisi = resultSet.getInt("odaSayisi");
                String otelTuru = resultSet.getString("otelTuru");
                String otelKonum = resultSet.getString("otelKonum");

                // Otel nesnesi oluştur ve listeye ekle
                Otel otel = new Otel(otelIsim, odaSayisi, otelKonum, otelTuru);
                liste.add(otel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return liste;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Otel> veri = getRezervasyonlar(); // Rezervasyonları çek
        System.out.println(veri);

        // Kolonlarla otel özelliklerini eşleştir
        isimColumn.setCellValueFactory(new PropertyValueFactory<Otel, String>("otelIsim"));
        odaNoColumn.setCellValueFactory(new PropertyValueFactory<Otel, String>("odaSayisi"));
        otelTuruColumn.setCellValueFactory(new PropertyValueFactory<Otel, String>("otelTuru"));
        konumColumn.setCellValueFactory(new PropertyValueFactory<Otel, String>("otelKonum"));

        // Veriyi tabloya ata
        Tablo.setItems(veri);
    }
}


