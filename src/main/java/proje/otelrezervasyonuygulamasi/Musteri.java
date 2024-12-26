package proje.otelrezervasyonuygulamasi;

import java.io.PipedReader;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Musteri extends  Insan implements Serializable {

    private StackS<Yorum> yorumlar;
    private QueueS<Otel> rezervasyonlarim;
    private int MusteriID;
    DbHelper db = new DbHelper();
    int count;


    public Musteri()
    {

    }
    //Select işlemleri için id biliniyorsa idsi yüksek bir nesne oluşturulmasını istemeyiz
    public Musteri(int id,String kullaniciIsim, String kullaniciSoyisim, int kullaniciYas, String kullaniciTelNo, String kullaniciCinsiyet, String kullaniciSifre){
        super(kullaniciIsim, kullaniciSoyisim, kullaniciYas, kullaniciTelNo, kullaniciCinsiyet, kullaniciSifre);
        this.MusteriID = id;
    }
    public Musteri(String kullaniciIsim, String kullaniciSoyisim, int kullaniciYas, String kullaniciTelNo, String kullaniciCinsiyet, String kullaniciSifre) {
        super(kullaniciIsim, kullaniciSoyisim, kullaniciYas, kullaniciTelNo, kullaniciCinsiyet, kullaniciSifre);
        //+1 algoritması
        Connection connection = null;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement("SELECT TOP 1 MusteriID from Musteri ORDER BY MusteriID DESC");
            rs = statement.executeQuery();
            if (rs.next()) {
                //Max müşteri id bulma
                count = rs.getInt("MusteriID");
                this.MusteriID = count+1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //+1 algoritması sonu
        rezervasyonlarim = new QueueS<>();
        yorumlar = new StackS<>();
    }

        void rezervasyon_ekle(Otel otel)
    {
        rezervasyonlarim.enqueue(otel);
    }
    public void yorumEkle(Yorum yorum)
    {
        this.yorumlar.push(yorum);
    }
    public Otel[] getRezervasyonlarim(){
        Otel[] oteller = new Otel[rezervasyonlarim.size];
        QueueS<Otel> tempRezervasyonlarim = new QueueS<>();
        tempRezervasyonlarim.copy(rezervasyonlarim);
        int i = 0;
        while(!tempRezervasyonlarim.isEmpty())
        {
            oteller[i++] = tempRezervasyonlarim.dequeue();
        }
        return oteller;
    }
    public Yorum[] getYorum()
    {
        StackS<Yorum> tempYorumlar = new StackS<>();
        tempYorumlar.copy(yorumlar);
        Yorum[] yorum = new Yorum[tempYorumlar.size];
        int i = 0;
        while(!tempYorumlar.isEmpty())
        {
            yorum[i++] = tempYorumlar.pop();
        }
        return yorum;
    }

    public int getMusteriID() {
        return MusteriID;
    }

}
