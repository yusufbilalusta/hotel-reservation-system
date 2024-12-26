package proje.otelrezervasyonuygulamasi;
import java.sql.*;

public class DbHelper {
    //You need to fulfill password field.
    String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=proje;user=sa;password=*****;encrypt=true;trustServerCertificate=true;";
    // Bağlantı nesnesi
    Connection conn = null;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl);
    }


    public int sqlInsertMusteri(Musteri musteri) {

        Connection connection = null;
        PreparedStatement statement;
        ResultSet rs;
        int flag = 1;
        String kadi = musteri.getKullaniciIsim();
        String ksoyad = musteri.getKullaniciSoyisim();
        String kcinsiyet = musteri.getKullaniciCinsiyet();
        String kno = musteri.getKullaniciTelNo();
        String ksifre = musteri.getKullaniciSifre();
        int MusteriID = musteri.getMusteriID();
        int yas = musteri.getKullaniciYas();

        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM Musteri WHERE kullaniciIsim = ? ");
            statement.setString(1, kadi);
            rs = statement.executeQuery();

            if (rs.next()) { // Eğer öyle bir kullanıcı var ise
                flag = 1;
            } else { // Eğer öyle bir kullanıcı yok ise insert edeceğiz
                statement = connection.prepareStatement("INSERT INTO Musteri("+
                        "MusteriID,kullaniciIsim,kullaniciSoyisim,kullaniciYas,kullaniciTelNo," +
                        "kullaniciCinsiyet,kullaniciSifre) values (?,?,?,?,?,?,?)");
                statement.setInt(1, MusteriID);
                statement.setString(2, kadi);
                statement.setString(3, ksoyad);
                statement.setInt(4, yas);
                statement.setString(5, kno);
                statement.setString(6, kcinsiyet);
                statement.setString(7, ksifre);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    flag = 0; // Insertion successful
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    return flag;
    }
    public int sqlInsertYonetici(Yonetici yonetici) {

        Connection connection = null;
        PreparedStatement statement;
        ResultSet rs;
        ResultSet rs2;
        int flag = 1;
        String kadi = yonetici.getKullaniciIsim();
        String ksoyad = yonetici.getKullaniciSoyisim();
        String kcinsiyet = yonetici.getKullaniciCinsiyet();
        String kno = yonetici.getKullaniciTelNo();
        String ksifre = yonetici.getKullaniciSifre();
        int yid = yonetici.getYoneticiID();
        Otel otel = yonetici.getOtel();
        int oid = otel.getOtelID();
        int yas = yonetici.getKullaniciYas();
        int otelid;

        try {
            connection = getConnection();
            //otel id çekme :
            statement = connection.prepareStatement("INSERT INTO Otel(otelID,otelIsim,odaSayisi,otelKonum,otelTuru) values (?,?,?,?,?)");
            statement.setInt(1,oid);
            statement.setString(2,otel.getOtelIsim());
            statement.setInt(3,otel.getOdaSayisi());
            statement.setString(4,otel.getOtelKonum());
            statement.setString(5, otel.getOtelTuru());
            int rowsAffected = statement.executeUpdate();

            if(rowsAffected>0){
            //otel oluşturulursa devam ediliyor



            statement = connection.prepareStatement("SELECT * FROM YONETICI WHERE isim = ? ");
            statement.setString(1, kadi);
            rs2 = statement.executeQuery();

            if (rs2.next()) { // Eğer öyle bir kullanıcı var ise
                flag = 1;
            } else { // Eğer öyle bir kullanıcı yok ise insert edeceğiz
                statement = connection.prepareStatement("INSERT INTO YONETICI(" +
                        "yoneticiID,isim,yas,tel,cinsiyet," +
                        "otelID,sifre,soyisim) values (?,?,?,?,?,?,?,?)");
                statement.setInt(1, yid);
                statement.setString(2, kadi);
                statement.setInt(3, yas);
                statement.setString(4, kno);
                statement.setString(5, kcinsiyet);
                statement.setInt(6, oid);
                statement.setString(7, ksifre);
                statement.setString(8,ksoyad);
                int rowsAffected2 = statement.executeUpdate();
                if (rowsAffected2 > 0) {
                    flag = 0; // Insertion successful
                }
            }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return flag;
    }
    public Yonetici selectYonetici(String kadi,String ksifre){
        Yonetici TempYonetici = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int flag = 0; // 0: Yanlış giriş, 1: dogru giris
        try{
            connection = getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM YONETICI WHERE isim = ? AND sifre = ?"
            );
            statement.setString(1,kadi);
            statement.setString(2,ksifre);
            rs = statement.executeQuery();
            if (rs.next()) {
                TempYonetici = new Yonetici(
                        rs.getInt("yoneticiID"),
                        rs.getString("isim"),
                        rs.getString("soyisim"),
                        rs.getInt("yas"),
                        rs.getString("tel"),
                        rs.getString("cinsiyet"),
                        selectOtel(rs.getInt("otelID")), //otel nesnesi dönmesi gerekiyor selectotel fonksiyonu yap
                        rs.getString("sifre")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return TempYonetici;
    }
    public Otel selectOtel(int otelid) {
        Otel TempOtel = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int flag = 0; // 0: Yanlış giriş, 1: dogru giris
        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM Otel WHERE otelID = ?"
            );
            statement.setInt(1, otelid);
            rs = statement.executeQuery();
            if (rs.next()) {
                TempOtel = new Otel(
                        rs.getInt("otelID"),
                        rs.getString("otelIsim"),
                        rs.getInt("odaSayisi"),
                        rs.getString("otelKonum"),
                        rs.getString("otelTuru")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return TempOtel;
    }
    public void sqlUpdateOtel(int OtelID,String otelIsim,int otelOda,String otelKonum,String otelTur){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int flag = 0; // 0: Yanlış giriş, 1: dogru giris
        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                    "UPDATE Otel SET otelIsim = ?,odaSayisi = ?,otelKonum = ?,otelTuru = ? WHERE otelID = ?"
            );
            statement.setString(1,otelIsim);
            statement.setInt(2,otelOda);
            statement.setString(3,otelKonum);
            statement.setString(4,otelTur);
            statement.setInt(5,OtelID);
            int rowsAffected = statement.executeUpdate();
            // Check if the update affected any rows
            if (rowsAffected > 0) {
                System.out.println("Update oldu oley!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public int sqlGirisYap(String isim, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int flag = 0; // 0: Yanlış giriş, 1: Müşteri, 2: Yönetici

        try {
            connection = getConnection();

            // Öncelikle müşteriler tablosunda arama yap
            statement = connection.prepareStatement(
                    "SELECT * FROM MUSTERI WHERE kullaniciIsim = ? AND kullaniciSifre = ?"
            );
            statement.setString(1, isim);
            statement.setString(2, password);
            rs = statement.executeQuery();

            if (rs.next()) {
                flag = 1; // Müşteri bulundu
                GirisController.girisYapanKullaniciID = rs.getInt("MusteriID");
            } else {
                // Eğer müşteri bulunamazsa, yönetici tablosunda arama yap
                statement = connection.prepareStatement(
                        "SELECT * FROM YONETICI WHERE isim = ? AND sifre = ?"
                );
                statement.setString(1, isim);
                statement.setString(2, password);
                rs = statement.executeQuery();

                if (rs.next()) {
                    flag = 2; // Yönetici bulundu
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Veritabanı hatası oluştu: " + e.getMessage());
        } finally {
            // Kaynakları kapatma işlemi
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return flag;}
    public void sqlDeleteRez(Musteri musteri,Otel otel){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                    "DELETE FROM Rez WHERE KullaniciID = ? AND OtelID = ?"
            );
            statement.setInt(1,musteri.getMusteriID());
            statement.setInt(2,otel.getOtelID());
            int rowsAffected = statement.executeUpdate();
            // Check if the update affected any rows
            if (rowsAffected > 0) {
                System.out.println("delete oldu oley!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
