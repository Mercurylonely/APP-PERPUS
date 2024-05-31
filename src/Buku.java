public class Buku {
    private int idBuku;
    private String judul;
    private String penerbit;
    private String penulis;
    private int tahunTerbit;
    private boolean tersedia = true;

    public Buku(int idBuku, String judul, String penerbit, String penulis, int tahunTerbit) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;// Default, saat buku dibuat, tersedia
    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
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
        return tahunTerbit;
    }

    public void setTahunTerbit(int tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }
    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }
    @Override
    public String toString() {
        return "Buku{" +
                "idBuku=" + idBuku +
                ", judul='" + judul + '\'' +
                ", penerbit='" + penerbit + '\'' +
                ", penulis='" + penulis + '\'' +
                ", tahunTerbit=" + tahunTerbit +
                '}';
    }
}
