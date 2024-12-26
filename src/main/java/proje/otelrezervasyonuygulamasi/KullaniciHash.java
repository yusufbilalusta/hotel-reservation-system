package proje.otelrezervasyonuygulamasi;

import java.io.Serializable;

class KullaniciHash<T extends Insan> implements Serializable
{
    private HashMapNode[] kullanici;
    KullaniciHash()
    {
        kullanici = new HashMapNode[26]; // Turkce karakterler yasak zort.
    }
    public void insert(String kullaniciIsim, String kullaniciSifre, T kullaniciHesap)
    {
        int indis = (int)kullaniciIsim.charAt(0);
        indis -= 97;
        HashMapNode yeniKullanici = new HashMapNode(kullaniciIsim, kullaniciSifre, kullaniciHesap);
        if(kullanici[indis] == null)
        {
            kullanici[indis] = yeniKullanici;
            return;
        }
        HashMapNode temp = kullanici[indis];
        while(temp.next != null)
        {
            temp = temp.next;
        }
        temp.next = yeniKullanici;
    }
    public String getSifre(String kullaniciIsim)
    {
        int indis = (int)kullaniciIsim.charAt(0);
        indis -= 97;
        HashMapNode temp = kullanici[indis];
        while(temp != null)
        {
            if(temp.kullaniciIsim.equals(kullaniciIsim))
            {
                return temp.kullaniciSifre;
            }
            temp = temp.next;
        }
        return "-1";
    }
    public T getKullaniciHesap(String kullaniciIsim)
    {
        int indis = (int)kullaniciIsim.charAt(0);
        indis -= 97;
        HashMapNode temp = kullanici[indis];
        while(temp != null)
        {
            if(temp.kullaniciIsim.equals(kullaniciIsim))
            {
                return (T) temp.kullaniciHesap;
            }
            temp = temp.next;
        }
        return null;
    }
    public Yonetici getOtelSahibi(String otelIsmi)
    {
        for(int i = 0; i <kullanici.length; i++)
        {
            HashMapNode temp = kullanici[i];
            while(temp != null)
            {
                try
                {
                    Yonetici hesap = (Yonetici) temp.kullaniciHesap;
                    if(hesap.getOtel().getOtelIsim().equals(otelIsmi))
                    {
                        return hesap;
                    }
                }
                catch(Exception ignored)
                {

                }
                finally
                {
                    temp = temp.next;
                }
            }
        }
        return null;
    }
    public String[][] getOteller()
    {
        int j = 0;
        String[][] oteller = new String[26][2];
        for(int i = 0; i <kullanici.length; i++)
        {
            HashMapNode temp = kullanici[i];
            while(temp != null)
            {
                try
                {
                    Yonetici hesap = (Yonetici) temp.kullaniciHesap;
                    oteller[j][0] = hesap.getOtel().getOtelIsim();
                    oteller[j][1] = hesap.getOtel().getOtelTuru();
                    j++;
                }
                catch(Exception ignored)
                {

                }
                finally
                {
                    temp = temp.next;
                }
            }
        }
        return oteller;
    }
    
}