package proje.otelrezervasyonuygulamasi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Otel implements Serializable {



    private String otelIsim;
    private String yoneticiIsim;
    private int odaSayisi;
    private String otelKonum;
    private String otelTuru;
    DbHelper db = new DbHelper();
    private int OtelID;
    int count;

    private StackS<Yorum> otelYorumlar;

    private QueueS<Musteri> otelRezervasyonlar;

    //Select işlemleri için id biliniyorsa idsi yüksek bir nesne oluşturulmasını istemeyiz
    public Otel(int id,String otelIsim, int odaSayisi, String otelKonum, String otelTuru) {
        this.otelIsim = otelIsim;
        this.odaSayisi = odaSayisi;
        this.otelKonum = otelKonum;
        this.otelTuru = otelTuru;
        this.OtelID = id;
    }

    public Otel(String otelIsim, int odaSayisi, String otelKonum, String otelTuru) {
        this.otelIsim = otelIsim;
        this.odaSayisi = odaSayisi;
        this.otelKonum = otelKonum;
        this.otelTuru = otelTuru;
        //+1 algoritması
        Connection connection = null;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement("SELECT TOP 1 OtelID from Otel ORDER BY OtelID DESC");
            rs = statement.executeQuery();
            if (rs.next()) {
                //Max müşteri id bulma
                count = rs.getInt("OtelID");
                this.OtelID = count+1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //+1 algoritması sonu
        this.otelYorumlar = new StackS<>();
        this.otelRezervasyonlar = new QueueS<>();
    }
    public Otel()
    {

    }



    public String getOtelIsim() {
        return otelIsim;
    }

    public void setOtelIsim(String otelIsim) {
        this.otelIsim = otelIsim;
    }


    public int getOdaSayisi() {
        return odaSayisi;
    }

    public void setOdaSayisi(int odaSayisi) {
        this.odaSayisi = odaSayisi;
    }

    public String getOtelKonum() {
        return otelKonum;
    }

    public void setOtelKonum(String otelKonum) {
        this.otelKonum = otelKonum;
    }

    public String getOtelTuru() {
        return otelTuru;
    }

    public void setOtelTuru(String otelTuru) {
        this.otelTuru = otelTuru;
    }

    public void yorumEkle(Yorum yorum)
    {
        otelYorumlar.push(yorum);
    }
    public void rezervasyonEkle(Musteri musteri)
    {
        otelRezervasyonlar.enqueue(musteri);
    }

    public Yorum[] getYorum()
    {

        StackS<Yorum> tempYorumlar = new StackS<>();
        tempYorumlar.copy(otelYorumlar);
        Yorum[] yorum = new Yorum[tempYorumlar.size];

        int i = 0;
        while(!tempYorumlar.isEmpty())
        {
            yorum[i++] = tempYorumlar.pop();
        }
        return yorum;

        //******************************************

            /*
            ObservableList<Musteri> liste = FXCollections.observableArrayList();

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet rs = null;
            try{
                connection = db.getConnection();
                statement = connection.prepareStatement(
                        "SELECT YORUM.icerik,Musteri.kullaniciIsim FROM YORUM\n" +
                                "JOIN Musteri ON Musteri.MusteriID = YORUM.musteriID\n" +
                                "JOIN Rez ON Rez.KullaniciID = YORUM.musteriID\n" +
                                "WHERE Rez.OtelID = ?"
                );
                statement.setInt(1,this.OtelID);
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


*/
            //**********************************************
        }









    public QueueS<Musteri> getRezervasyonQueue()
    {
        return otelRezervasyonlar;
    }
    public void setOtelRezervasyonlar(QueueS<Musteri> rezervasyonlar) {
        otelRezervasyonlar.copy(rezervasyonlar);
    }

    public int getOtelID() {
        return OtelID;
    }
}
