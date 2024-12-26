package proje.otelrezervasyonuygulamasi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

public class RezervasyonSilUIController extends GirisController implements Initializable {

    @FXML
    private TableView<Musteri> Tablo;

    @FXML
    private TableColumn<Musteri, String> isimColumn;

    @FXML
    private TableColumn<Musteri, String> soyisimColumn;

    @FXML
    private TableColumn<Musteri, Integer> yasColumn;

    @FXML
    private TableColumn<Musteri, String> cinsiyetColumn;

    public RezervasyonSilUIController() throws IOException, ClassNotFoundException {
    }
    Musteri TempMusteri = null;

    @FXML
    void rezervasyonSil(ActionEvent event) throws IOException {
        Musteri musteri = Tablo.getSelectionModel().getSelectedItem();
        db2.sqlDeleteRez(musteri,GirisController.activeYonetici.getOtel());
        Tablo.getItems().removeAll(musteri);
    }

    public ObservableList<Musteri> getRezervasyonlar() {
        ObservableList<Musteri> liste = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db2.getConnection();
            statement = connection.prepareStatement(
                    "SELECT M.MusteriID,M.kullaniciIsim,M.kullaniciSoyisim,M.kullaniciYas,M.kullaniciTelNo,M.kullaniciCinsiyet,M.kullaniciSifre FROM REZ\n" +
                            "JOIN Musteri M ON M.MusteriID = REZ.KullaniciID\n" +
                            "JOIN Otel O ON O.otelID = Rez.OtelID\n" +
                            "JOIN YONETICI Y ON Y.otelID = O.otelID\n" +
                            "WHERE Y.otelID = ?"
            );
            statement.setInt(1, GirisController.activeYonetici.getOtel().getOtelID());
            rs = statement.executeQuery();
            while (rs.next()) {
                TempMusteri = new Musteri(
                        rs.getInt("MusteriID"),
                        rs.getString("kullaniciIsim"),
                        rs.getString("kullaniciSoyisim"),
                        rs.getInt("kullaniciYas"),
                        rs.getString("kullaniciTelNo"),
                        rs.getString("kullaniciCinsiyet"),
                        rs.getString("kullaniciSifre")
                );
                liste.add(TempMusteri);
            }
            return liste;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Musteri> veri = getRezervasyonlar();

        isimColumn.setCellValueFactory(new PropertyValueFactory<Musteri, String>("kullaniciIsim"));
        soyisimColumn.setCellValueFactory(new PropertyValueFactory<Musteri, String>("kullaniciSoyisim"));
        yasColumn.setCellValueFactory(new PropertyValueFactory<Musteri, Integer>("kullaniciYas"));
        cinsiyetColumn.setCellValueFactory(new PropertyValueFactory<Musteri, String>("kullaniciCinsiyet"));
        Tablo.setItems(veri);


    }
}
