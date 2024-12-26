package proje.otelrezervasyonuygulamasi;

public class YorumGosterme {
    private String yazar;
    private String icerik;
    public YorumGosterme(String yazar,String icerik){
        this.yazar = yazar;
        this.icerik = icerik;
    }

    public String getIcerik() {
        return icerik;
    }

    public String getYazar() {
        return yazar;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public void setYazar(String musteriAdi) {
        yazar = musteriAdi;
    }
}
