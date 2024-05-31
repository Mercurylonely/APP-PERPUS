import java.time.LocalDate;

public class Peminjaman {
    private Anggota anggota;
    private Buku buku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;

    public Peminjaman(Anggota anggota, Buku buku, LocalDate tanggalPinjam, LocalDate tanggalKembali) {
        this.anggota = anggota;
        this.buku = buku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
    }
    public Buku getBuku() {
        return buku;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public Anggota getAnggota() {
        return anggota;
    }
    // tambahkan getter dan setter sesuai kebutuhan
    private void kembalikanBuku(Peminjaman peminjaman) {
        // Perbarui status buku menjadi tersedia
        Buku buku = peminjaman.getBuku();
        buku.setTersedia(true);
        // Atur tanggal pengembalian
        peminjaman.setTanggalKembali(LocalDate.now());
        System.out.println("Buku berhasil dikembalikan oleh " + peminjaman.getAnggota().getNama());
    }
}