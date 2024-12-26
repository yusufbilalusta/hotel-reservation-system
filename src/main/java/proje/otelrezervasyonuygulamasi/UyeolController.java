package proje.otelrezervasyonuygulamasi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class UyeolController extends GirisController implements Initializable{

    @FXML
    private Button btn_uyeol;

    @FXML
    private ChoiceBox<String> kullaniciCinsiyet;

    @FXML
    private TextField kullaniciIsim;

    @FXML
    private TextField kullaniciSifre;

    @FXML
    private TextField kullaniciSoyisim;

    @FXML
    private TextField kullaniciTelNo;

    @FXML
    private ChoiceBox<String> kullaniciTur;

    @FXML
    private TextField kullaniciYas;
    DbHelper dbq = new DbHelper();

    public UyeolController() throws IOException, ClassNotFoundException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] cinsiyetler = {"Erkek", "Kadın"};
        kullaniciCinsiyet.getItems().addAll(cinsiyetler);
        String[] turler = {"Yönetici", "Müşteri"};
        kullaniciTur.getItems().addAll(turler);
    }
    public boolean bosGirisVarMi()
    {
        if(kullaniciIsim.getText().isEmpty() || kullaniciSifre.getText().isEmpty() || kullaniciSoyisim.getText().isEmpty()
                || kullaniciTelNo.getText().isEmpty() || kullaniciYas.getText().isEmpty() || kullaniciTur.getValue().isEmpty() ||
                kullaniciCinsiyet.getValue().isEmpty())
        {
            return false;
        }
        return true;
    }
    public boolean yasGirisDogruMu()
    {
        int yas;
        try
        {
            yas = Integer.parseInt(kullaniciYas.getText());
        }
        catch(NumberFormatException exception)
        {
            return false;
        }
        return yas <= 180 && yas >= 0;
    }
    @FXML
    void uyeOl(ActionEvent event) throws IOException {
        int flag = 1;
        if(bosGirisVarMi() && yasGirisDogruMu())
        {
            String isim = kullaniciIsim.getText();
            String soyisim = kullaniciSoyisim.getText();
            String telNo = kullaniciTelNo.getText();
            int yas = Integer.parseInt(kullaniciYas.getText());
            String sifre = kullaniciSifre.getText();
            String cinsiyet = kullaniciCinsiyet.getValue();
            if(kullaniciTur.getValue().equals("Yönetici"))
            {
                Otel yeniOtel = new Otel("Boş",0,"Boş","Boş");
                Yonetici yeniYonetici = new Yonetici(isim,soyisim,yas,telNo,cinsiyet,yeniOtel,sifre);
                int sayi = dbq.sqlInsertYonetici(yeniYonetici);
                if(sayi==1){
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("UyeOlUyariUI.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(),350, 230);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }
            }
            else
            {
                Musteri yeniMusteri = new Musteri(isim, soyisim, yas, telNo, cinsiyet, sifre);
                int sayi = dbq.sqlInsertMusteri(yeniMusteri);
                if(sayi==1){
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("UyeOlUyariUI.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(),350, 230);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }
            }
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("UyeOlUyariUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),350, 230);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }


    }
}
