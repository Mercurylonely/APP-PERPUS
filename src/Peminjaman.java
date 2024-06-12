import java.util.Date;

public class Peminjaman {
    private int idPeminjaman;
    private Anggota anggota;
    private Buku buku;
    private Date tanggalPinjam;
    private Date tanggalKembali;

    public Peminjaman(int idPeminjaman, Anggota anggota, Buku buku, Date tanggalPinjam, Date tanggalKembali) {
        this.idPeminjaman = idPeminjaman;
        this.anggota = anggota;
        this.buku = buku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
    }

    public void buatPeminjaman(Anggota anggota, Buku buku) {
        this.anggota = anggota;
        this.buku = buku;
        this.tanggalPinjam = new Date(); // Set tanggal pinjam menjadi tanggal hari ini
        System.out.println("Peminjaman buku '" + buku.getJudul() + "' oleh anggota '" + anggota.getNama() + "' berhasil dibuat.");
    }

    public void kembalikanBuku() {
        this.tanggalKembali = new Date(); // Set tanggal kembali menjadi tanggal hari ini
        System.out.println("Buku '" + buku.getJudul() + "' telah dikembalikan oleh anggota '" + anggota.getNama() + "'.");
    }

    public String cekStatus() {
        if (tanggalKembali != null) {
            return "Buku telah dikembalikan";
        } else {
            return "Buku belum dikembalikan";
        }
    }

    // Getter dan setter
    public int getId() {
        return idPeminjaman;
    }

    public void setId(int id) {
        this.idPeminjaman = idPeminjaman;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public Date getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(Date tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public Date getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(Date tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }
}
