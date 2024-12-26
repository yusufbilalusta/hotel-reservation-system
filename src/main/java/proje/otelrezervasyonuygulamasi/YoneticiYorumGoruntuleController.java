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

public class YoneticiYorumGoruntuleController extends GirisController implements Initializable {

    @FXML
    private TableView<YorumGosterme> Tablo;

    @FXML
    private TableColumn<YorumGosterme, String> icerik; //Bu tabloya icerikyorum stringlerinden oluşan liste gelecek

    @FXML
    private TableColumn<YorumGosterme, String> yazar; //Bu tabloysa musteriismi stringlerinden oluşan liste gelecek
    String icerikyorum;
    String musteriismi;

    public YoneticiYorumGoruntuleController() throws IOException, ClassNotFoundException {
    }

    public ObservableList<YorumGosterme> getYorumlar()
    {
        ObservableList<YorumGosterme> liste = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db2.getConnection();
            statement = connection.prepareStatement(
                    "SELECT YORUM.icerik,Musteri.kullaniciIsim FROM YORUM\n" +
                            "JOIN Musteri ON YORUM.musteriID = Musteri.MusteriID\n" +
                            "WHERE YORUM.otelID = ?"
            );
            statement.setInt(1, GirisController.activeYonetici.getOtel().getOtelID());
            rs = statement.executeQuery();
            while (rs.next()) {
                //YORUMUN İÇERİĞİ BURADA
                icerikyorum = rs.getString("icerik");
                musteriismi = rs.getString("kullaniciIsim");
                YorumGosterme gecici = new YorumGosterme(musteriismi,icerikyorum);
                liste.add(gecici);
            }
            return liste;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<YorumGosterme> veri = getYorumlar();
        icerik.setCellValueFactory(new PropertyValueFactory<YorumGosterme, String>("icerik"));
        yazar.setCellValueFactory(new PropertyValueFactory<YorumGosterme, String>("yazar"));
        Tablo.setItems(veri);
    }
}
