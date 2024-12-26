package proje.otelrezervasyonuygulamasi;

import javafx.beans.property.SimpleStringProperty;
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

public class MusteriYorumGoruntuleController extends GirisController implements Initializable {
    int kullaniciID = GirisController.girisYapanKullaniciID;
    ObservableList<String> otelIsimleri = FXCollections.observableArrayList();
    ObservableList<Yorum> liste = FXCollections.observableArrayList();

    @FXML
    private TableView<Yorum> Tablo;

    @FXML
    private TableColumn<Yorum, String> otelIsim;

    @FXML
    private TableColumn<Yorum, String> yapilanYorum;

    public MusteriYorumGoruntuleController() throws IOException, ClassNotFoundException {
    }

    public ObservableList<Yorum> getYorumlar() {
        DbHelper db = new DbHelper();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = db.getConnection();

            // Kullanıcıya ait otel bilgilerini almak için JOIN sorgusu
            String query = """
                SELECT Otel.otelID, Otel.otelIsim, YORUM.icerik FROM YORUM JOIN Otel ON Otel.otelID = YORUM.otelID WHERE YORUM.musteriID = ?
            """;

            statement = connection.prepareStatement(query);
            statement.setInt(1, kullaniciID); // Kullanıcı ID'sini sorguya ekle
            resultSet = statement.executeQuery();

            // Otel bilgilerini ve yorumları listeye ekle
            while (resultSet.next()) {
                int otelID = resultSet.getInt("otelID");
                String icerik = resultSet.getString("icerik");
                String otelIsimValue = resultSet.getString("otelIsim");

                // Yorum nesnesini oluştur
                Yorum yorum = new Yorum(icerik, GirisController.girisYapanKullaniciID, otelID);

                // Otel isimlerini ve yorumları listeye ekle
                liste.add(yorum);
                otelIsimleri.add(otelIsimValue); // Otel ismini ayrı bir listeye ekle
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
        // Veritabanından yorumları al
        ObservableList<Yorum> veri = getYorumlar();

        // Tablo sütunlarını veri kaynaklarına bağla
        otelIsim.setCellValueFactory(cellData -> {
            int index = Tablo.getItems().indexOf(cellData.getValue());
            return index >= 0 && index < otelIsimleri.size()
                    ? new SimpleStringProperty(otelIsimleri.get(index))
                    : new SimpleStringProperty("");
        });

        yapilanYorum.setCellValueFactory(new PropertyValueFactory<>("icerik"));

        // Veriyi tabloya bağla
        Tablo.setItems(veri);
    }
}

