import java.util.Date;
public class Anggota {
    private int idAnggota;
    private String namaA;
    private String alamatA;
    private String teleponA;
    private String emailA;
    private String tanggalDaftar;

    public Anggota(int idAnggota, String namaA, String alamatA, String teleponA, String emailA, String tanggalDaftar) {
        this.idAnggota = idAnggota;
        this.namaA = namaA;
        this.alamatA = alamatA;
        this.teleponA = teleponA;
        this.emailA = emailA;
        this.tanggalDaftar = tanggalDaftar;
    }

    public void daftar() {
        System.out.println("Anggota " + namaA + " telah berhasil mendaftar.");
    }

    public void pinjamBuku(Buku buku) {
        System.out.println(namaA + " telah meminjam buku dengan judul '" + buku.getJudul() + "'.");
    }

    public void kembalikanBuku(Buku buku) {
        System.out.println(namaA + " telah mengembalikan buku dengan judul '" + buku.getJudul() + "'.");
    }

    // Getter dan setter
    public int getIdAnggota() {
        return idAnggota;
    }

    public void setId(int id) {
        this.idAnggota = idAnggota;
    }

    public String getNama() {
        return namaA;
    }

    public void setNama(String nama) {
        this.namaA = nama;
    }

    public String getAlamat() {
        return alamatA;
    }

    public void setAlamat(String alamat) {
        this.alamatA = alamat;
    }

    public String getTelepon() {
        return teleponA;
    }

    public void setTelepon(String telepon) {
        this.teleponA = telepon;
    }

    public String getEmail() {
        return emailA;
    }

    public void setEmail(String email) {
        this.emailA = email;
    }

    public String getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(String tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }
}
