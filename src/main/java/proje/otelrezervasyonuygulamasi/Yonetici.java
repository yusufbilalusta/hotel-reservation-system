package proje.otelrezervasyonuygulamasi;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Yonetici extends  Insan implements Serializable {

    private Otel otel;
    DbHelper db = new DbHelper();
    int count;
    private int YoneticiID;

    //Select işlemleri için id biliniyorsa idsi yüksek bir nesne oluşturulmasını istemeyiz
    Yonetici(int id,String isim, String soy_isim, int yas, String tel_no, String cinsiyet,Otel myotel, String sifre){
        super(isim,soy_isim,yas,tel_no,cinsiyet, sifre);
        this.otel =myotel;
        this.YoneticiID = id;
    }


    Yonetici(String isim, String soy_isim, int yas, String tel_no, String cinsiyet,Otel myotel, String sifre){
        super(isim,soy_isim,yas,tel_no,cinsiyet, sifre);
        this.otel =myotel;
        //+1 algoritması
        Connection connection = null;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement("SELECT TOP 1 yoneticiID from YONETICI ORDER BY yoneticiID DESC");
            rs = statement.executeQuery();
            if (rs.next()) {
                //Max müşteri id bulma
                count = rs.getInt("yoneticiID");
                this.YoneticiID = count+1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //+1 algoritması sonu
    }
    public Yonetici()
    {

    }



    public void otel_guncelle(String isim, int odaSayisi,  String konum, String odaTuru){
        otel.setOtelIsim(isim);
        otel.setOdaSayisi(odaSayisi);
        otel.setOtelKonum(konum);
        otel.setOtelTuru(odaTuru);
    }

    public void rezervasyon_sil(String isim){
        QueueS<Musteri> tempRezervasyon = new QueueS<>();
        tempRezervasyon.copy(otel.getRezervasyonQueue());
        QueueS<Musteri> grrrPow = new QueueS<>();
        while(!tempRezervasyon.isEmpty()){
            Musteri musteri = tempRezervasyon.dequeue();
            if(!musteri.getKullaniciIsim().equals(isim))
            {
                grrrPow.enqueue(musteri);
            }
        }
        otel.setOtelRezervasyonlar(grrrPow);

    }
    public Otel getOtel()
    {
        return otel;
    }

    public int getYoneticiID() {
        return YoneticiID;
    }
}
