public class Buku {
    private int id_Buku;
    private String judul;
    private String penerbit;
    private String penulis;
    private int tahun_terbit;

    // Konstruktor
    public Buku(int id_Buku, String judul, String penerbit, String penulis, int tahun_terbit) {
        this.id_Buku = id_Buku;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.tahun_terbit = tahun_terbit;
    }

    // Getter dan Setter
    public int getIdBuku() {
        return id_Buku;
    }

    public void setIdBuku(int idBuku) {
        this.id_Buku = idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public int getTahunTerbit() {
        return tahun_terbit;
    }

    public void setTahunTerbit(int tahunTerbit) {
        this.tahun_terbit = tahun_terbit;
    }
}
