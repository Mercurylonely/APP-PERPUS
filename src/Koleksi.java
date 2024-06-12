public class Koleksi {
    private Buku buku;
    private int jumlah;
    private String lokasi;

    public Koleksi(Buku buku, int jumlah, String lokasi) {
        this.buku = buku;
        this.jumlah = jumlah;
        this.lokasi = lokasi;//APA GUNANYA ANJING
    }

    public void updateJumlah(int jumlah) {
        this.jumlah += jumlah;
        System.out.println("Jumlah buku '" + buku.getJudul() + "' di lokasi '" + lokasi + "' berhasil diperbarui menjadi " + this.jumlah);
    }

    // Getter dan setter
    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}
