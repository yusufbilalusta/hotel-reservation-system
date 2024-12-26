package proje.otelrezervasyonuygulamasi;

import java.io.Serializable;

class HashMapNode<T extends Insan> implements Serializable
{
    public HashMapNode next;
    public String kullaniciIsim;
    public String kullaniciSifre;
    public T kullaniciHesap;
    HashMapNode(String kullaniciIsim, String kullaniciSifre, T kullaniciHesap)
    {
        this.kullaniciIsim = kullaniciIsim;
        this.kullaniciSifre = kullaniciSifre;
        this.kullaniciHesap = kullaniciHesap;

    }
}