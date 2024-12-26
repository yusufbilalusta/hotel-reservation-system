package proje.otelrezervasyonuygulamasi;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;


public class GirisController implements Serializable {



    DbHelper db2 = new DbHelper();
    @FXML
    public Button btn_giris;
    @FXML
    public TextField txtf_kullanici_adi, txtf_sifre;

    @FXML
    private Label label_uyarı;
    public static Yonetici activeYonetici = null;
    public static Musteri activeMusteri = null;
    public static int girisYapanKullaniciID;
    public static int girisYapanKullaniciTipi; //0 için Musteri 1 için Yonetici

    public GirisController() throws IOException, ClassNotFoundException {
    }


    public void butonkod() throws IOException {

        if (!txtf_kullanici_adi.getText().isBlank() && !txtf_sifre.getText().isBlank()) {
            giris_control_sql();
        } else {
            label_uyarı.setText("Lütfen Kullanıcı Adı Ve Şifre Giriniz");
        }


    }

    public void uyeOl() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("uye_ol.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    public void giris_control_sql() throws IOException {
        // DbHelper örneği oluşturuluyor
        DbHelper dbHelper = new DbHelper();

        // Kullanıcı adı ve şifreyi alıyoruz
        String kullaniciIsim = txtf_kullanici_adi.getText().toLowerCase();
        String kullaniciSifre = txtf_sifre.getText();

        // Giriş kontrolü için sqlGirisYap çağrılıyor
        int result = dbHelper.sqlGirisYap(kullaniciIsim, kullaniciSifre);

        if (result == 2) { // Giriş başarılı
            System.out.println("Yönetici giriş yaptı: ");
            //Yonetici objesi static olur
            activeYonetici = db2.selectYonetici(kullaniciIsim,kullaniciSifre);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("YoneticiUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else if (result == 1) {
            System.out.println("Müşteri giriş yaptı: ");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MusteriUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else { // Giriş başarısız
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GirisYanlisUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 350, 230);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }


    }
}










