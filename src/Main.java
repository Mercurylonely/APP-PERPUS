import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;

public class Main extends Application {
    Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    boolean flagEdit;
    TableView<Buku> tableView;
    TableView<Pustakawan> tableViewPustakawan;
    TableView<Anggota> tableViewAnggota;
    TableColumn<Buku, Integer> idBuku;
    TableColumn<Buku, String> judul;
    TableColumn<Buku, String> penerbit;
    TableColumn<Buku, String> penulis;
    TableColumn<Buku, Integer> tahun_terbit;
    TextField tfIdBuku;
    TextField tfJudul;
    TextField tfPenerbit;
    TextField tfPenulis;
    TextField tfTahunTerbit;
    TextField tfIdPustakawan;
    TextField tfNama;
    TextField tfEmail;
    Button bUpdate;
    Button bCancel;
    Button bAdd;
    Button bEdit;
    Button bDel;
    Button bAdd1;
    Button bEdit1;
    Button bUpdate1;
    Button bDel1;
    Button bCancel1;
    Button bAdd2,bEdit2,bDel2,bUpdate2,bCancel2;
    TextField tfIdAnggota,tfNamaA,tfEmailA,tfTeleponA,tfAlamatA,tfTanggalDaftar;
    TextField tfIdPeminjaman,tfTanggalPinjam,TfTanggalKembali;
    BorderPane border;

    @Override
    public void start(Stage stage) {
        border = new BorderPane();
        HBox hbox = addHBox();
        border.setTop(hbox);
        border.setLeft(addVBox());
        border.setCenter(addVBoxHome());
        stage.setTitle("Sistem Perpustakaan");
        Scene scene = new Scene(border);
        stage.setScene(scene);
        stage.show();
    }

    private HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        Text tjudul = new Text("Sistem Perpustakaan");
        tjudul.setFont(Font.font("Verdana", 20));
        tjudul.setFill(Color.WHITESMOKE);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(tjudul);
        return hbox;
    }

    private VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        Text title = new Text("Perpustakaan");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);
        Hyperlink[] options = new Hyperlink[] {
                new Hyperlink("Home"),
                new Hyperlink("Buku"),
                new Hyperlink("Pustakawan"),
                new Hyperlink("Anggota"),
                new Hyperlink("Peminjaman"),
                new Hyperlink("Koleksi Buku"),
                new Hyperlink("Selesai")};
        for (int i = 0; i < 7; i++) {
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
            vbox.getChildren().add(options[i]);
        }
        options[0].setOnAction(e -> border.setCenter(addVBoxHome()));
        options[1].setOnAction(e -> border.setCenter(addVBoxBuku()));
        options[2].setOnAction(e -> border.setCenter(addVBoxPustakawan()));
        options[3].setOnAction(e -> border.setCenter(addVBoxAnggota()));
        options[4].setOnAction(e -> border.setCenter(addVBoxPeminjaman()));
        options[5].setOnAction(e ->border.setCenter(addVboxKoleksiBuku()));
        options[6].setOnAction(e -> System.exit(0));
        return vbox;
    }

    private VBox addVBoxHome() {
        VBox vb = new VBox();
        vb.setFillWidth(true);
        Text tjudul = new Text("Home");
        tjudul.setFont(Font.font("Arial", 18));
        vb.setAlignment(Pos.CENTER);
        try {
            FileInputStream input = new FileInputStream("C:/Users/lenov/IdeaProjects/APP PERPUSTAKAAN/src/Image trying.png");
            Image image = new Image(input);
            ImageView imageview = new ImageView(image);
            imageview.setFitHeight(500);
            imageview.setPreserveRatio(true);
            vb.getChildren().addAll(tjudul, imageview);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return vb;
    }

    private StackPane spTableBuku() {
        StackPane sp = new StackPane();
        tableView = new TableView<>();
        idBuku = new TableColumn<>("idBuku");
        judul = new TableColumn<>("judul");
        penerbit = new TableColumn<>("penerbit");
        penulis = new TableColumn<>("penulis");
        tahun_terbit = new TableColumn<>("tahunTerbit");
        tableView.getColumns().addAll(idBuku, judul, penerbit, penulis, tahun_terbit);
        idBuku.setCellValueFactory(new PropertyValueFactory<>("idBuku"));
        judul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        penerbit.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        penulis.setCellValueFactory(new PropertyValueFactory<>("penulis"));
        tahun_terbit.setCellValueFactory(new PropertyValueFactory<>("tahunTerbit"));
        idBuku.setPrefWidth(50);
        judul.setPrefWidth(300);
        penulis.setPrefWidth(200);
        penerbit.setPrefWidth(200);
        tahun_terbit.setPrefWidth(100);
        tableView.setPrefWidth(850);
        showListBuku();
        sp.getChildren().add(tableView);
        return sp;
    }

    private GridPane gpFormBuku() {
        flagEdit = false;
        GridPane gp = new GridPane();
        gp.setPrefHeight(500);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        Label lbJudulForm = new Label("Form Data Buku");
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
        bDel = new Button("Del");
        bAdd.setPrefWidth(100);
        bEdit.setPrefWidth(100);
        bDel.setPrefWidth(100);
        bUpdate.setPrefWidth(100);
        bCancel.setPrefWidth(100);
        bUpdate.setDisable(true);
        bCancel.setDisable(true);
        bUpdate.setOnAction(e -> updateDataBuku());
        bEdit.setOnAction(e -> editDataBuku());
        bAdd.setOnAction(e -> addDataBuku());
        bCancel.setOnAction(e -> cancelEditBuku());
        bDel.setOnAction(e -> deleteDataBuku());
        TilePane tp1 = new TilePane();
        tp1.getChildren().addAll(bAdd, bEdit, bDel, bUpdate, bCancel);
        gp.addRow(0, new Label(""), lbJudulForm);
        gp.addRow(1, lbIdBuku, tfIdBuku);
        gp.addRow(2, lbJudul, tfJudul);
        gp.addRow(3, lbPenerbit, tfPenerbit);
        gp.addRow(4, lbPenulis, tfPenulis);
        gp.addRow(5, lbTahunTerbit, tfTahunTerbit);
        gp.addRow(6, new Label(""), tp1);
        teksAktif(false);
        buttonAktif(false);
        return gp;
    }

    private VBox addVBoxBuku() {
        VBox vb = new VBox();
        Text tjudul = new Text("Form Data Buku");
        tjudul.setFont(Font.font("Arial", 18));
        vb.getChildren().add(tjudul);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(spTableBuku());
        vb.getChildren().add(gpFormBuku());
        return vb;
    }

    public void buttonAktif(boolean nonAktif) {
        bAdd.setDisable(nonAktif);
        bEdit.setDisable(nonAktif);
        bDel.setDisable(nonAktif);
        bUpdate.setDisable(!nonAktif);
        bCancel.setDisable(!nonAktif);
    }

    public void teksAktif(boolean aktif) {
        tfIdBuku.setEditable(aktif);
        tfJudul.setEditable(aktif);
        tfPenerbit.setEditable(aktif);
        tfPenulis.setEditable(aktif);
        tfTahunTerbit.setEditable(aktif);
    }

    public void clearTeks() {
        tfIdBuku.setText("");
        tfJudul.setText("");
        tfPenerbit.setText("");
        tfPenulis.setText("");
        tfTahunTerbit.setText("");
    }

    public ObservableList<Buku> getlistBuku() {
        ObservableList<Buku> listBuku = FXCollections.observableArrayList();
        String sql = "SELECT * FROM buku";
        conn = DBConnection.getConn();
        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                Buku b = new Buku(rs.getInt("Id_Buku"), rs.getString("judul"),
                        rs.getString("penerbit"), rs.getString("penulis"),
                        rs.getInt("tahun_terbit"));
                listBuku.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBuku;
    }

    public void showListBuku() {
        ObservableList<Buku> listBuku = getlistBuku();
        tableView.setItems(listBuku);
    }

    private void updateDataBuku() {
        int idBuku, tahunTerbit;
        String judul, penulis, penerbit;
        idBuku = Integer.parseInt(tfIdBuku.getText());
        judul = tfJudul.getText();
        penulis = tfPenulis.getText();
        penerbit = tfPenerbit.getText();
        tahunTerbit = Integer.parseInt(tfTahunTerbit.getText());
        Buku b = new Buku(idBuku, judul, penerbit, penulis, tahunTerbit);
        if (!flagEdit) {
            tableView.getItems().add(b);
            String sql = "INSERT INTO buku(Id_Buku, judul, penerbit, penulis, tahun_terbit) VALUES (?, ?, ?, ?, ?)";
            conn = DBConnection.getConn();
            try {
                st = conn.prepareStatement(sql);
                st.setInt(1, idBuku);
                st.setString(2, judul);
                st.setString(3, penerbit);
                st.setString(4, penulis);
                st.setInt(5, tahunTerbit);
                st.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            int idx = tableView.getSelectionModel().getSelectedIndex();
            String sql = "UPDATE buku SET judul=?, penerbit=?, penulis=?, tahun_terbit=? WHERE Id_Buku=?";
            conn = DBConnection.getConn();
            try {
                st = conn.prepareStatement(sql);
                st.setString(1, judul);
                st.setString(2, penerbit);
                st.setString(3, penulis);
                st.setInt(4, tahunTerbit);
                st.setInt(5, idBuku);
                st.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            tableView.getItems().set(idx, b);
        }
        showListBuku();
        teksAktif(false);
        buttonAktif(false);
        clearTeks();
        flagEdit = true;
    }

    private void editDataBuku() {
        buttonAktif(true);
        teksAktif(true);
        flagEdit = true;
        int idx = tableView.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            Buku selectedBuku = tableView.getItems().get(idx);
            tfIdBuku.setText(String.valueOf(selectedBuku.getIdBuku()));
            tfJudul.setText(selectedBuku.getJudul());
            tfPenerbit.setText(selectedBuku.getPenerbit());
            tfPenulis.setText(selectedBuku.getPenulis());
            tfTahunTerbit.setText(String.valueOf(selectedBuku.getTahunTerbit()));
        }
    }

    private void addDataBuku() {
        flagEdit = false;
        clearTeks();
        teksAktif(true);
        buttonAktif(true);
    }

    private void cancelEditBuku() {
        teksAktif(false);
        buttonAktif(false);
    }

    private void deleteDataBuku() {
        int idx = tableView.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            Buku selectedBuku = tableView.getItems().get(idx);
            int idbuku = selectedBuku.getIdBuku();
            String sql = "DELETE FROM buku WHERE id_Buku = ?";
            try (Connection conn = DBConnection.getConn();
                 PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, idbuku);
                st.executeUpdate();
                tableView.getItems().remove(idx);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private VBox addVBoxPustakawan() {
        VBox vb1 = new VBox();
        Text tjudul1 = new Text("Form Data Pustakawan");
        tjudul1.setFont(Font.font("Arial", 18));
        vb1.getChildren().add(tjudul1);
        vb1.setAlignment(Pos.CENTER);
        vb1.getChildren().add(spTablePustakawan());
        vb1.getChildren().add(gpFormPustakawan());
        return vb1;
    }

    private StackPane spTablePustakawan() {
        StackPane sp2 = new StackPane();
        tableViewPustakawan = new TableView<>();
        TableColumn<Pustakawan, Integer> idPustakawan = new TableColumn<>("Id Pustakawan");
        TableColumn<Pustakawan, String> nama = new TableColumn<>("Nama");
        TableColumn<Pustakawan, String> email = new TableColumn<>("Email");
        tableViewPustakawan.getColumns().addAll(idPustakawan, nama, email);
        idPustakawan.setCellValueFactory(new PropertyValueFactory<>("idPustakawan"));
        nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        idPustakawan.setPrefWidth(150);
        nama.setPrefWidth(200);
        email.setPrefWidth(200);
        tableViewPustakawan.setPrefWidth(850);
        showListPustakawan();
        sp2.getChildren().add(tableViewPustakawan);
        return sp2;
    }

    private GridPane gpFormPustakawan() {
        GridPane gp1 = new GridPane();
        gp1.setPrefHeight(500);
        gp1.setAlignment(Pos.CENTER);
        gp1.setVgap(10);
        gp1.setHgap(10);
        gp1.setPadding(new Insets(10, 10, 10, 10));
        Label lbJudulForm1 = new Label("Form Data Pustakawan");
        Label lbIdPustakawan = new Label("Id Pustakawan");
        Label lbNama = new Label("Nama");
        Label lbEmail = new Label("Email");
        tfIdPustakawan = new TextField();
        tfNama = new TextField();
        tfEmail = new TextField();
        bUpdate1 = new Button("Update");
        bCancel1 = new Button("Cancel");
        bAdd1 = new Button("Add");
        bEdit1 = new Button("Edit");
        bDel1 = new Button("Del");
        bAdd1.setPrefWidth(100);
        bEdit1.setPrefWidth(100);
        bDel1.setPrefWidth(100);
        bUpdate1.setPrefWidth(100);
        bCancel1.setPrefWidth(100);
        bUpdate1.setDisable(true);
        bCancel1.setDisable(true);
        bUpdate1.setOnAction(e -> updateDataPustakawan());
        bEdit1.setOnAction(e -> editDataPustakawan());
        bAdd1.setOnAction(e -> addDataPustakawan());
        bCancel1.setOnAction(e -> cancelEditPustakawan());
        bDel1.setOnAction(e -> deleteDataPustakawan());
        TilePane tp2 = new TilePane();
        tp2.getChildren().addAll(bAdd1, bEdit1, bDel1, bUpdate1, bCancel1);
        gp1.addRow(0, new Label(""), lbJudulForm1);
        gp1.addRow(1, lbIdPustakawan, tfIdPustakawan);
        gp1.addRow(2, lbNama, tfNama);
        gp1.addRow(3, lbEmail, tfEmail);
        gp1.addRow(4, new Label(""), tp2);
        teksAktif1(false);
        buttonAktif1(false);
        return gp1;
    }

    private void updateDataPustakawan() {
        int idPustakawan = Integer.parseInt(tfIdPustakawan.getText());
        String nama = tfNama.getText();
        String email = tfEmail.getText();
        Pustakawan pustakawan = new Pustakawan(idPustakawan, nama, email);
        if (!flagEdit) {
            tableViewPustakawan.getItems().add(pustakawan);
            String sql = "INSERT INTO pustakawan(id_pustakawan, nama, email) VALUES (?, ?, ?)";
            try (Connection conn = DBConnection.getConn();
                 PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, idPustakawan);
                st.setString(2, nama);
                st.setString(3, email);
                st.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            int idx = tableViewPustakawan.getSelectionModel().getSelectedIndex();
            String sql = "UPDATE pustakawan SET nama=?, email=? WHERE id_pustakawan=?";
            try (Connection conn = DBConnection.getConn();
                 PreparedStatement st = conn.prepareStatement(sql)) {
                st.setString(1, nama);
                st.setString(2, email);
                st.setInt(3, idPustakawan);
                st.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            tableViewPustakawan.getItems().set(idx, pustakawan);
        }
        showListPustakawan();
        teksAktif1(false);
        buttonAktif1(false);
        clearTeks1();
        flagEdit = true;
    }

    private void editDataPustakawan() {
        buttonAktif1(true);
        teksAktif1(true);
        flagEdit = true;
        int idx = tableViewPustakawan.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            Pustakawan selectedPustakawan = tableViewPustakawan.getItems().get(idx);
            tfIdPustakawan.setText(String.valueOf(selectedPustakawan.getIdPustakawan()));
            tfNama.setText(selectedPustakawan.getNama());
            tfEmail.setText(selectedPustakawan.getEmail());
        }
    }

    private void addDataPustakawan() {
        flagEdit = false;
        clearTeks1();
        teksAktif1(true);
        buttonAktif1(true);
    }

    private void cancelEditPustakawan() {
        teksAktif1(false);
        buttonAktif1(false);
    }

    private void deleteDataPustakawan() {
        int idx = tableViewPustakawan.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            Pustakawan selectedPustakawan = tableViewPustakawan.getItems().get(idx);
            int idPustakawan = selectedPustakawan.getIdPustakawan();
            String sql = "DELETE FROM pustakawan WHERE id_pustakawan = ?";
            try (Connection conn = DBConnection.getConn();
                 PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, idPustakawan);
                st.executeUpdate();
                tableViewPustakawan.getItems().remove(idx);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ObservableList<Pustakawan> getListPustakawan() {
        ObservableList<Pustakawan> listPustakawan = FXCollections.observableArrayList();
        String sql = "SELECT * FROM pustakawan";
        try (Connection conn = DBConnection.getConn();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Pustakawan p = new Pustakawan(rs.getInt("id_pustakawan"), rs.getString("nama"),
                        rs.getString("email"));
                listPustakawan.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPustakawan;
    }

    public void showListPustakawan() {
        ObservableList<Pustakawan> listPustakawan = getListPustakawan();
        tableViewPustakawan.setItems(listPustakawan);
    }

    public void buttonAktif1(boolean nonAktif) {
        bAdd1.setDisable(nonAktif);
        bEdit1.setDisable(nonAktif);
        bDel1.setDisable(nonAktif);
        bUpdate1.setDisable(!nonAktif);
        bCancel1.setDisable(!nonAktif);
    }

    public void teksAktif1(boolean aktif) {
        tfIdPustakawan.setEditable(aktif);
        tfNama.setEditable(aktif);
        tfEmail.setEditable(aktif);
    }

    public void clearTeks1() {
        tfIdPustakawan.setText("");
        tfNama.setText("");
        tfEmail.setText("");
    }
    private VBox addVBoxAnggota() {
        VBox vb2 = new VBox();
        Text tjudul2 = new Text("Form Data Anggota");
        tjudul2.setFont(Font.font("Arial", 18));
        vb2.getChildren().add(tjudul2);
        vb2.setAlignment(Pos.CENTER);
        vb2.getChildren().add(spTableAnggota());
        vb2.getChildren().add(gpFormAnggota());
        return vb2;
    }
    private StackPane spTableAnggota() {
        StackPane sp3 = new StackPane();
        tableViewAnggota = new TableView<>();
        TableColumn<Anggota, Integer> idAnggota = new TableColumn<>("Id Anggota");
        TableColumn<Anggota, String> namaA = new TableColumn<>("Nama");
        TableColumn<Anggota, String> alamatA = new TableColumn<>("Alamat");
        TableColumn<Anggota, String> teleponA = new TableColumn<>("Telepon");
        TableColumn<Anggota, String> emailA = new TableColumn<>("Email");
        TableColumn<Anggota, String> tanggalDaftar = new TableColumn<>("TanggalDaftar");
        tableViewAnggota.getColumns().addAll(idAnggota, namaA,alamatA,teleponA,emailA,tanggalDaftar);
        idAnggota.setCellValueFactory(new PropertyValueFactory<>("idAnggota"));
        namaA.setCellValueFactory(new PropertyValueFactory<>("nama"));
        alamatA.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        teleponA.setCellValueFactory(new PropertyValueFactory<>("telepon"));
        emailA.setCellValueFactory(new PropertyValueFactory<>("email"));
        tanggalDaftar.setCellValueFactory(new PropertyValueFactory<>("TanggalDaftar"));
        idAnggota.setPrefWidth(150);
        namaA.setPrefWidth(200);
        alamatA.setPrefWidth(200);
        teleponA.setPrefWidth(200);
        emailA.setPrefWidth(200);
        tanggalDaftar.setPrefWidth(200);
        tableViewAnggota.setPrefWidth(850);
        showListAnggota();
        sp3.getChildren().add(tableViewAnggota);
        return sp3;
    }
    private GridPane gpFormAnggota() {
        GridPane gp2 = new GridPane();
        gp2.setPrefHeight(500);
        gp2.setAlignment(Pos.CENTER);
        gp2.setVgap(10);
        gp2.setHgap(10);
        gp2.setPadding(new Insets(10, 10, 10, 10));
        Label lbJudulForm2= new Label("Form Data Anggota");
        Label lbIdAnggota = new Label("Id Anggota");
        Label lbNamaA = new Label("Nama");
        Label lbAlamatA = new Label("Alamat");
        Label lbTelepon = new Label("Telepon");
        Label lbEmailA = new Label("Email");
        Label lbTanggalDaftar = new Label("Tanggal Daftar");
        tfIdAnggota = new TextField();
        tfNamaA = new TextField();
        tfAlamatA = new TextField();
        tfTeleponA = new TextField();
        tfEmailA = new TextField();
        tfTanggalDaftar = new TextField();
        bUpdate2 = new Button("Update");
        bCancel2 = new Button("Cancel");
        bAdd2 = new Button("Add");
        bEdit2 = new Button("Edit");
        bDel2 = new Button("Del");
        bAdd2.setPrefWidth(100);
        bEdit2.setPrefWidth(100);
        bDel2.setPrefWidth(100);
        bUpdate2.setPrefWidth(100);
        bCancel2.setPrefWidth(100);
        bUpdate2.setDisable(true);
        bCancel2.setDisable(true);
        bUpdate2.setOnAction(e -> updateDataAnggota());
        bEdit2.setOnAction(e -> editDataAnggota());
        bAdd2.setOnAction(e -> addDataAnggota());
        bCancel2.setOnAction(e -> cancelEditAnggota());
        bDel2.setOnAction(e -> deleteDataAnggota());
        TilePane tp2 = new TilePane();
        tp2.getChildren().addAll(bAdd2, bEdit2, bDel2, bUpdate2, bCancel2);
        gp2.addRow(0, new Label(""), lbJudulForm2);
        gp2.addRow(1, lbIdAnggota, tfIdAnggota);
        gp2.addRow(2, lbNamaA, tfNamaA);
        gp2.addRow(3, lbAlamatA, tfAlamatA);
        gp2.addRow(4, lbTelepon, tfTeleponA);
        gp2.addRow(5, lbEmailA, tfEmailA);
        gp2.addRow(6, lbTanggalDaftar, tfTanggalDaftar);
        gp2.addRow(7, new Label(""), tp2);
        teksAktif1(false);
        buttonAktif1(false);
        return gp2;
    }
    private void updateDataAnggota() {
        int idAnggota = Integer.parseInt(tfIdAnggota.getText());
        String namaA = tfNamaA.getText();
        String alamatA = tfAlamatA.getText();
        String teleponA = tfTeleponA.getText();
        String emailA = tfEmailA.getText();
        String tanggalDaftar = tfTanggalDaftar.getText();
        Anggota anggota = new Anggota(idAnggota, namaA,alamatA,teleponA,emailA,tanggalDaftar);
        if (!flagEdit) {
            tableViewAnggota.getItems().add(anggota);
            String sql = "INSERT INTO anggota(id_anggota, nama, alamat, telepon, email, TanggalDaftar) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection conn = DBConnection.getConn();
                 PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, idAnggota);
                st.setString(2, namaA);
                st.setString(3, alamatA);
                st.setString(4, teleponA);
                st.setString(5, emailA);
                st.setString(6, tanggalDaftar);
                st.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            int idx = tableViewAnggota.getSelectionModel().getSelectedIndex();
            String sql = "UPDATE anggota SET nama=?, alamat=?, telepon=?, email=?, TanggalDaftar=? WHERE id_anggota=?";
            try (Connection conn = DBConnection.getConn();
                 PreparedStatement st = conn.prepareStatement(sql)) {
                st.setString(1, namaA);
                st.setString(2, alamatA);
                st.setString(3, teleponA);
                st.setString(4, emailA);
                st.setString(5, tanggalDaftar);
                st.setInt(6, idAnggota);
                st.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            tableViewAnggota.getItems().set(idx, anggota);
        }
        showListAnggota();
        teksAktif2(false);
        buttonAktif2(false);
        clearTeks2();
        flagEdit = true;
    }

    private void editDataAnggota() {
        buttonAktif2(true);
        teksAktif2(true);
        flagEdit = true;
        int idx = tableViewAnggota.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            Anggota selectedAnggota = tableViewAnggota.getItems().get(idx);
            tfIdAnggota.setText(String.valueOf(selectedAnggota.getIdAnggota()));
            tfNamaA.setText(selectedAnggota.getNama());
            tfAlamatA.setText(selectedAnggota.getAlamat());
            tfTeleponA.setText(selectedAnggota.getTelepon());
            tfEmailA.setText(selectedAnggota.getEmail());
            tfTanggalDaftar.setText(selectedAnggota.getTanggalDaftar());
        }
    }
    private void addDataAnggota() {
        flagEdit = false;
        clearTeks2();
        teksAktif2(true);
        buttonAktif2(true);
    }
    private void cancelEditAnggota() {
        teksAktif2(false);
        buttonAktif2(false);
    }

    private void deleteDataAnggota() {
        int idx = tableViewAnggota.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            Anggota selectedAnggota = tableViewAnggota.getItems().get(idx);
            int idAnggota = selectedAnggota.getIdAnggota();
            String sql = "DELETE FROM anggota WHERE id_anggota = ?";
            try (Connection conn = DBConnection.getConn();
                 PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, idAnggota);
                st.executeUpdate();
                tableViewAnggota.getItems().remove(idx);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public ObservableList<Anggota> getListAnggota() {
        ObservableList<Anggota> listAnggota = FXCollections.observableArrayList();
        String sql = "SELECT * FROM anggota";
        try (Connection conn = DBConnection.getConn();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Anggota a = new Anggota(rs.getInt("id_anggota"), rs.getString("nama"),rs.getString("alamat"),
                        rs.getString("telepon"), rs.getString("email"),rs.getString("TanggalDaftar"));
                listAnggota.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAnggota;
    }
    public void showListAnggota() {
        ObservableList<Anggota> listAnggota = getListAnggota();
        tableViewAnggota.setItems(listAnggota);
    }
    public void buttonAktif2(boolean nonAktif) {
        bAdd2.setDisable(nonAktif);
        bEdit2.setDisable(nonAktif);
        bDel2.setDisable(nonAktif);
        bUpdate2.setDisable(!nonAktif);
        bCancel2.setDisable(!nonAktif);
    }
    public void teksAktif2(boolean aktif) {
        tfIdAnggota.setEditable(aktif);
        tfNamaA.setEditable(aktif);
        tfAlamatA.setEditable(aktif);
        tfTeleponA.setEditable(aktif);
        tfEmailA.setEditable(aktif);
        tfTanggalDaftar.setEditable(aktif);
    }

    public void clearTeks2() {
        tfIdAnggota.setText("");
        tfNamaA.setText("");
        tfAlamatA.setText("");
        tfTeleponA.setText("");
        tfEmailA.setText("");
        tfTanggalDaftar.setText("");
    }
    private VBox addVboxKoleksiBuku() {
        VBox vb3 = new VBox();
        Text tjudul3 = new Text("Koleksi Buku");
        tjudul3.setFont(Font.font("Arial", 18));
        vb3.getChildren().add(tjudul3);
        vb3.setAlignment(Pos.CENTER);
        vb3.getChildren().add(spTableKoleksiBuku());
        return vb3;
    }

    private StackPane spTableKoleksiBuku() {
        StackPane sp = new StackPane();
        TableView<Buku> tableViewKoleksiBuku = new TableView<>();
        TableColumn<Buku, Integer> idBuku = new TableColumn<>("ID Buku");
        TableColumn<Buku, String> judul = new TableColumn<>("Judul");
        TableColumn<Buku, String> penerbit = new TableColumn<>("Penerbit");
        TableColumn<Buku, String> penulis = new TableColumn<>("Penulis");
        TableColumn<Buku, Integer> tahunTerbit = new TableColumn<>("Tahun Terbit");
        tableViewKoleksiBuku.getColumns().addAll(idBuku, judul, penerbit, penulis, tahunTerbit);
        idBuku.setCellValueFactory(new PropertyValueFactory<>("idBuku"));
        judul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        penerbit.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        penulis.setCellValueFactory(new PropertyValueFactory<>("penulis"));
        tahunTerbit.setCellValueFactory(new PropertyValueFactory<>("tahunTerbit"));
        tableViewKoleksiBuku.setPrefWidth(850);
        showListBuku(); // Menampilkan data buku
        tableViewKoleksiBuku.setItems(tableView.getItems()); // Menggunakan data yang sama dari table Buku
        sp.getChildren().add(tableViewKoleksiBuku);
        return sp;
    }

    public static void main(String[] args) {
        launch(args);
    }
}



