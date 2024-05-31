import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application {
    private ArrayList<Pustakawan> listPustakawan = new ArrayList<>();
    private ArrayList<Anggota> listAnggota = new ArrayList<>();
    private ArrayList<Buku> listBuku = new ArrayList<>();
    private ArrayList<Peminjaman> listPeminjaman = new ArrayList<>();


    BorderPane border;
    TableView<Buku> tableView;
    TableColumn<Buku, Integer> idBukuCol;
    TableColumn<Buku, String> judulCol;
    TableColumn<Buku, String> penerbitCol;
    TableColumn<Buku, String> penulisCol;
    TableColumn<Buku, Integer> tahunTerbitCol;
    TextField tfIdBuku;
    TextField tfJudul;
    TextField tfPenerbit;
    TextField tfPenulis;
    TextField tfTahunTerbit;
    Button bUpdate;
    Button bCancel;
    Button bAdd;
    Button bEdit;
    Button bDel;

    @Override
    public void start(Stage stage) throws Exception {
        border = new BorderPane();
        HBox hbox = addHBox();
        border.setTop(hbox);
        border.setLeft(addVBox());
        border.setCenter(addVBoxHome());

        stage.setTitle("Sistem Perpustakaan");
        Scene scene = new Scene(border, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        Text title = new Text("Sistem Perpustakaan");
        title.setFont(Font.font("Verdana", 20));
        title.setFill(javafx.scene.paint.Color.WHITE);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(title);
        return hbox;
    }

    private VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        Text title = new Text("Perpustakaan");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);
        Hyperlink options[] = {
                new Hyperlink("Home"),
                new Hyperlink("Buku"),
                new Hyperlink("Pustakawan"),
                new Hyperlink("Anggota"),
                new Hyperlink("Peminjaman"),
                new Hyperlink("Koleksi Buku"),
                new Hyperlink("Selesai")
        };
        for (int i = 0; i < 7; i++) {
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
            vbox.getChildren().add(options[i]);
        }
        options[0].setOnAction(e -> border.setCenter(addVBoxHome()));
        options[1].setOnAction(e -> {
            // Memperbarui TableView setiap kali menu buku dipilih
            border.setCenter(addVBoxBuku());
            // Menyematkan data buku ke TableView
            tableView.getItems().setAll(listBuku);
        });

        options[2].setOnAction(e -> border.setCenter(addVBoxPustakawan()));
        options[3].setOnAction(e -> border.setCenter(addVBoxAnggota()));
        options[4].setOnAction(e -> border.setCenter(addVBoxPeminjaman(listBuku, listAnggota)));
        options[5].setOnAction(e -> border.setCenter(addVBoxKoleksiBuku()));
        options[6].setOnAction(e -> System.exit(0));
        return vbox;
    }

    private VBox addVBoxHome() {
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        Text tjudul = new Text("Home");
        tjudul.setFont(Font.font("Arial", 18));
        FileInputStream input= null;
        try {
            input = new FileInputStream("C:/Users/lenov/IdeaProjects/APP PERPUSTAKAAN/src/Image trying.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(input);
        ImageView imageview=new ImageView(image);
        vb.getChildren().add(imageview);
        vb.getChildren().add(tjudul);
        return vb;
    }

    private VBox addVBoxBuku() {
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        Text tjudul = new Text("Form Data Buku");
        tjudul.setFont(Font.font("Arial", 18));
        vb.getChildren().add(tjudul);
        vb.getChildren().add(spTableBuku());
        vb.getChildren().add(gpFormBuku());
        return vb;
    }

    private StackPane spTableBuku() {
        StackPane sp = new StackPane();
        tableView = new TableView<>();
        idBukuCol = new TableColumn<>("Id Buku");
        judulCol = new TableColumn<>("Judul");
        penerbitCol = new TableColumn<>("Penerbit");
        penulisCol = new TableColumn<>("Penulis");
        tahunTerbitCol = new TableColumn<>("Tahun Terbit");
        tableView.getColumns().addAll(idBukuCol, judulCol, penerbitCol, penulisCol, tahunTerbitCol);
        // Set cell value factories for columns
        idBukuCol.setCellValueFactory(new PropertyValueFactory<>("idBuku"));
        judulCol.setCellValueFactory(new PropertyValueFactory<>("judul"));
        penerbitCol.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        penulisCol.setCellValueFactory(new PropertyValueFactory<>("penulis"));
        tahunTerbitCol.setCellValueFactory(new PropertyValueFactory<>("tahunTerbit"));
        // Add dummy data for demonstration
        tableView.getItems().addAll(
                new Buku(1, "Judul Buku 1", "Penerbit A", "Penulis X", 2000),
                new Buku(2, "Judul Buku 2", "Penerbit B", "Penulis Y", 2005),
                new Buku(3, "Judul Buku 3", "Penerbit C", "Penulis Z", 2010)
        );
        sp.getChildren().add(tableView);
        return sp;
    }

    private GridPane gpFormBuku() {
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        Label lbIdBuku = new Label("Id Buku");
        Label lbJudul = new Label("Judul");
        Label lbPenerbit = new Label("Penerbit");
        Label lbPenulis = new Label("Penulis");
        Label lbTahunTerbit = new Label("Tahun Terbit");
        tfIdBuku = new TextField();
        tfJudul = new TextField();
        tfPenerbit = new TextField();
        tfPenulis = new TextField();
        tfTahunTerbit = new TextField();
        bUpdate = new Button("Update");
        bCancel = new Button("Cancel");
        bAdd = new Button("Add");
        bEdit = new Button("Edit");
        bDel = new Button("Delete");
        // Add button actions
        bAdd.setOnAction(e -> addBook());
        bEdit.setOnAction(e -> editBook());
        bDel.setOnAction(e -> deleteBook());
        bUpdate.setOnAction(e -> updateBook());
        bCancel.setOnAction(e -> clearFields());

        // Add elements to grid pane
        gp.addRow(0, lbIdBuku, tfIdBuku);
        gp.addRow(1, lbJudul, tfJudul);
        gp.addRow(2, lbPenerbit, tfPenerbit);
        gp.addRow(3, lbPenulis, tfPenulis);
        gp.addRow(4, lbTahunTerbit, tfTahunTerbit);
        HBox buttonBox = new HBox(10, bAdd, bEdit, bDel, bUpdate, bCancel);
        buttonBox.setAlignment(Pos.CENTER);
        gp.addRow(5, buttonBox);
        return gp;
    }

    private void addBook() {
        int id = Integer.parseInt(tfIdBuku.getText());
        String judul = tfJudul.getText();
        String penerbit = tfPenerbit.getText();
        String penulis = tfPenulis.getText();
        int tahunTerbit = Integer.parseInt(tfTahunTerbit.getText());
        Buku newBook = new Buku(id, judul, penerbit, penulis, tahunTerbit);
        listBuku.add(newBook); // Adding to book list
        tableView.getItems().add(newBook);
        clearFields();
    }


    private void editBook() {
        Buku selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            // Set text fields with selected book's data
            tfIdBuku.setText(String.valueOf(selectedBook.getIdBuku()));
            tfJudul.setText(selectedBook.getJudul());
            tfPenerbit.setText(selectedBook.getPenerbit());
            tfPenulis.setText(selectedBook.getPenulis());
            tfTahunTerbit.setText(String.valueOf(selectedBook.getTahunTerbit()));
            // Disable add button and enable update button while editing
            bAdd.setDisable(true);
            bUpdate.setDisable(false);
        } else {
            showAlert("Edit", "Pilih buku yang akan diedit.");
        }
    }


    private void updateBook() {
        Buku selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            // Hapus buku yang lama dari list
            listBuku.remove(selectedBook);
            // Perbarui data buku dengan data yang baru dari input
            selectedBook.setIdBuku(Integer.parseInt(tfIdBuku.getText()));
            selectedBook.setJudul(tfJudul.getText());
            selectedBook.setPenerbit(tfPenerbit.getText());
            selectedBook.setPenulis(tfPenulis.getText());
            selectedBook.setTahunTerbit(Integer.parseInt(tfTahunTerbit.getText()));
            // Tambahkan buku yang diperbarui ke list
            listBuku.add(selectedBook);
            // Perbarui TableView
            tableView.getItems().setAll(listBuku);
            // Bersihkan kolom input
            clearFields();
            // Aktifkan kembali tombol Tambah dan nonaktifkan tombol Perbarui
            bAdd.setDisable(false);
            bUpdate.setDisable(true);
        } else {
            showAlert("Update", "Pilih buku yang akan diupdate.");
        }
    }


    private void deleteBook() {
        Buku selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            listBuku.remove(selectedBook);
            tableView.getItems().remove(selectedBook);
        }
    }


    private void clearFields() {
        tfIdBuku.clear();
        tfJudul.clear();
        tfPenerbit.clear();
        tfPenulis.clear();
        tfTahunTerbit.clear();
        tableView.getSelectionModel().clearSelection();
    }

    private VBox addVBoxPustakawan() {
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        Text tjudul = new Text("Form Data Pustakawan");
        tjudul.setFont(Font.font("Arial", 18));
        vb.getChildren().add(tjudul);

        // Form elements
        Label lblNama = new Label("Nama:");
        TextField tfNama = new TextField();

        Label lblAlamat = new Label("Alamat:");
        TextField tfAlamat = new TextField();

        Label lblTelepon = new Label("Nomor Telepon:");
        TextField tfTelepon = new TextField();

        // Button
        Button btnSave = new Button("Simpan");
        btnSave.setOnAction(e -> {
            String nama = tfNama.getText();
            String alamat = tfAlamat.getText();
            String telepon = tfTelepon.getText();
            Pustakawan pustakawan = new Pustakawan(nama, alamat, telepon);
            listPustakawan.add(pustakawan);
            System.out.println("Data pustakawan berhasil disimpan: " + pustakawan);
            // Clear text fields after saving
            tfNama.clear();
            tfAlamat.clear();
            tfTelepon.clear();
        });

        // Button for showing saved pustakawans
        Button btnShow = new Button("Tampilkan Pustakawan");
        btnShow.setOnAction(e -> {
            System.out.println("Daftar Pustakawan:");
            for (Pustakawan p : listPustakawan) {
                System.out.println(p);
            }
        });

        // Add elements to VBox
        vb.getChildren().addAll(
                lblNama, tfNama,
                lblAlamat, tfAlamat,
                lblTelepon, tfTelepon,
                btnSave,
                btnShow
        );

        // Set spacing and padding
        vb.setSpacing(10);
        vb.setPadding(new Insets(10));

        return vb;
    }


    private VBox addVBoxAnggota() {
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        Text tjudul = new Text("Form Data Anggota");
                tjudul.setFont(Font.font("Arial", 18));
        vb.getChildren().add(tjudul);

        // Form elements
        Label lblNama = new Label("Nama:");
        TextField tfNama = new TextField();

        Label lblAlamat = new Label("Alamat:");
        TextField tfAlamat = new TextField();

        Label lblTelepon = new Label("Nomor Telepon:");
        TextField tfTelepon = new TextField();

        // Button
        Button btnSave = new Button("Simpan");
        btnSave.setOnAction(e -> {
            String nama = tfNama.getText();
            String alamat = tfAlamat.getText();
            String telepon = tfTelepon.getText();
            Anggota anggota = new Anggota(nama, alamat, telepon);
            listAnggota.add(anggota);
            System.out.println("Data anggota berhasil disimpan: " + anggota);
            // Clear text fields after saving
            tfNama.clear();
            tfAlamat.clear();
            tfTelepon.clear();
        });

        // Button for showing saved anggota
        Button btnShow = new Button("Tampilkan Anggota");
        btnShow.setOnAction(e -> {
            System.out.println("Daftar Anggota:");
            for (Anggota a : listAnggota) {
                System.out.println(a);
            }
        });

        // Add elements to VBox
        vb.getChildren().addAll(
                lblNama, tfNama,
                lblAlamat, tfAlamat,
                lblTelepon, tfTelepon,
                btnSave,
                btnShow
        );

        // Set spacing and padding
        vb.setSpacing(10);
        vb.setPadding(new Insets(10));

        return vb;
    }


    private VBox addVBoxPeminjaman(ArrayList<Buku> listBuku, ArrayList<Anggota> listAnggota) {
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        Text tjudul = new Text("Form Data Peminjaman");
        tjudul.setFont(Font.font("Arial", 18));
        vb.getChildren().add(tjudul);

        // Form elements
        Label lblAnggota = new Label("Nama Anggota:");
        ComboBox<Anggota> cbAnggota = new ComboBox<>();
        cbAnggota.getItems().addAll(listAnggota);

        Label lblBuku = new Label("Judul Buku:");
        ComboBox<Buku> cbBuku = new ComboBox<>();
        cbBuku.getItems().addAll(listBuku);

        DatePicker dpTanggalPinjam = new DatePicker();
        dpTanggalPinjam.setPromptText("Tanggal Pinjam");

        DatePicker dpTanggalKembali = new DatePicker();
        dpTanggalKembali.setPromptText("Tanggal Kembali");

        // Button
        Button btnPinjam = new Button("Pinjam");
        btnPinjam.setOnAction(e -> {
            Anggota anggota = cbAnggota.getValue();
            Buku buku = cbBuku.getValue();
            LocalDate tanggalPinjam = dpTanggalPinjam.getValue();
            LocalDate tanggalKembali = dpTanggalKembali.getValue();
            if (anggota != null && buku != null && tanggalPinjam != null && tanggalKembali != null) {
                System.out.println("Peminjaman: Anggota: " + anggota + ", Buku: " + buku +
                        ", Tanggal Pinjam: " + tanggalPinjam + ", Tanggal Kembali: " + tanggalKembali);
                // Logic to handle peminjaman
            } else {
                showAlert("Peminjaman", "Harap lengkapi semua field.");
            }
        });

        // Add elements to VBox
        vb.getChildren().addAll(
                lblAnggota, cbAnggota,
                lblBuku, cbBuku,
                new HBox(10, new Label("Tanggal Pinjam:"), dpTanggalPinjam),
                new HBox(10, new Label("Tanggal Kembali:"), dpTanggalKembali),
                btnPinjam
        );

        // Set spacing and padding
        vb.setSpacing(10);
        vb.setPadding(new Insets(10));

        return vb;
    }



    private VBox addVBoxKoleksiBuku() {
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        Text tjudul = new Text("Koleksi Buku");
        tjudul.setFont(Font.font("Arial", 18));
        vb.getChildren().add(tjudul);

        // List view for displaying book collection
        ListView<String> listView = new ListView<>();
        listView.setPrefSize(400, 300);

        // Button to refresh book collection
        Button btnRefresh = new Button("Refresh");
        btnRefresh.setOnAction(e -> {
            // Clear previous items
            listView.getItems().clear();
            // Add all books to the list view
            for (Buku buku : listBuku) {
                listView.getItems().add(buku.toString());
            }
        });

        // Add elements to VBox
        vb.getChildren().addAll(listView, btnRefresh);

        // Set spacing and padding
        vb.setSpacing(10);
        vb.setPadding(new Insets(10));

        return vb;
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void pinjamBuku(Anggota anggota, Buku buku) {
        // Memeriksa apakah buku tersedia sebelum dipinjam
        if (buku.isTersedia()) {
            // Jika tersedia, tandai status buku sebagai tidak tersedia
            buku.setTersedia(false);
            // Buat objek peminjaman baru dan tambahkan ke daftar peminjaman
            Peminjaman peminjaman = new Peminjaman(anggota, buku, LocalDate.now(), null);
            listPeminjaman.add(peminjaman);
            System.out.println("Buku berhasil dipinjam oleh " + anggota.getNama());
        } else {
            System.out.println("Buku sedang tidak tersedia");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}


