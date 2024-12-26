package proje.otelrezervasyonuygulamasi;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Yorum implements Serializable {
    private String icerik;
    private int kullaniciID;
    private int otel;

    DbHelper db = new DbHelper();
    private int YorumID;
    int count;

    //private Otel otel;
    Yorum(String icerik, int  kullaniciID, int otelID) {
        this.icerik = icerik;
        this.kullaniciID = kullaniciID;
        this.otel = otel;
        //+1 algoritması
        Connection connection = null;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement("SELECT TOP 1 yorumID from YORUM ORDER BY yorumID DESC");
            rs = statement.executeQuery();
            if (rs.next()) {
                //Max müşteri id bulma
                count = rs.getInt("yorumID");
                this.YorumID = count+1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //+1 algoritması sonu
    }

    public String getIcerik() {
        return icerik;
    }

    public int getMusteri() {
        return this.kullaniciID;
    }

    public int getOtel() {
        return otel;
    }
}
