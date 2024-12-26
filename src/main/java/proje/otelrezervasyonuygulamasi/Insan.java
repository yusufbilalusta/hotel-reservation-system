package proje.otelrezervasyonuygulamasi;

abstract class Insan {

    private String kullaniciIsim;
    private String kullaniciSoyisim;
    private int kullaniciYas;
    private String kullaniciTelNo;
    private String kullaniciCinsiyet;
    private String kullaniciSifre;


     public Insan(String isim, String soy_isim, int yas, String tel_no, String cinsiyet, String sifre) {
         this.kullaniciIsim = isim;
         this.kullaniciSoyisim = soy_isim;
         this.kullaniciYas = yas;
         this.kullaniciTelNo = tel_no;
         this.kullaniciCinsiyet = cinsiyet;
         this.kullaniciSifre = sifre;
     }
     public Insan()
     {

     }

     public String getKullaniciIsim() {
         return kullaniciIsim;
     }

     public void setKullaniciIsim(String kullaniciIsim) {
         this.kullaniciIsim = kullaniciIsim;
     }

     public String getKullaniciSoyisim() {
         return kullaniciSoyisim;
     }

     public void setKullaniciSoyisim(String kullaniciSoyisim) {
         this.kullaniciSoyisim = kullaniciSoyisim;
     }

     public int getKullaniciYas() {
         return kullaniciYas;
     }

     public void setKullaniciYas(int kullaniciYas) {
         this.kullaniciYas = kullaniciYas;
     }

     public String getKullaniciTelNo() {
         return kullaniciTelNo;
     }

     public void setKullaniciTelNo(String kullaniciTelNo) {
         this.kullaniciTelNo = kullaniciTelNo;
     }

     public String getKullaniciCinsiyet() {
         return kullaniciCinsiyet;
     }

     public void setKullaniciCinsiyet(String kullaniciCinsiyet) {this.kullaniciCinsiyet = kullaniciCinsiyet;}
     public String getKullaniciSifre(){return kullaniciSifre;}
     public void setKullaniciSifre(String kullaniciSifre){this.kullaniciSifre = kullaniciSifre;}
}
