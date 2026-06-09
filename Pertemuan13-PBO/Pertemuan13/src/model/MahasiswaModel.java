package model;

import koneksi.Koneksi;
import java.sql.*;
import java.util.ArrayList;

public class MahasiswaModel {
    // Atribut
    private int id;
    private String nim;
    private String nama;
    private String jurusan;
    private String mataKuliah;
    private int nilaiTugas1;
    private int nilaiTugas2;
    private int nilaiUTS;
    private int nilaiTugas3;
    private int nilaiTugas4;
    private int nilaiUAS;

    // Constructor
    public MahasiswaModel(int id, String nim, String nama, String jurusan,
                          String mataKuliah,
                          int nilaiTugas1, int nilaiTugas2, int nilaiUTS,
                          int nilaiTugas3, int nilaiTugas4, int nilaiUAS) {
        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.mataKuliah = mataKuliah;
        this.nilaiTugas1 = nilaiTugas1;
        this.nilaiTugas2 = nilaiTugas2;
        this.nilaiUTS = nilaiUTS;
        this.nilaiTugas3 = nilaiTugas3;
        this.nilaiTugas4 = nilaiTugas4;
        this.nilaiUAS = nilaiUAS;
    }

    // Getter
    public int getId() { return id; }
    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public String getJurusan() { return jurusan; }
    public String getMataKuliah() { return mataKuliah; }
    public int getNilaiTugas1() { return nilaiTugas1; }
    public int getNilaiTugas2() { return nilaiTugas2; }
    public int getNilaiUTS() { return nilaiUTS; }
    public int getNilaiTugas3() { return nilaiTugas3; }
    public int getNilaiTugas4() { return nilaiTugas4; }
    public int getNilaiUAS() { return nilaiUAS; }

    /**
     * Kalkulasi Nilai Akhir
     * Menghitung nilai akhir berbobot.
     * T1(10%) + T2(15%) + UTS(25%) + T3(10%) + T4(15%) + UAS(25%)
     */
    public double getNilaiAkhir() {
        return (nilaiTugas1 * 0.10)
             + (nilaiTugas2 * 0.15)
             + (nilaiUTS * 0.25)
             + (nilaiTugas3 * 0.10)
             + (nilaiTugas4 * 0.15)
             + (nilaiUAS * 0.25);
    }

    // QUERY: Ambil semua data (JOIN mahasiswa + nilai)
    public static ArrayList<MahasiswaModel> getAll() {
        ArrayList<MahasiswaModel> list = new ArrayList<>();
        String sql = "SELECT m.id, m.nim, m.nama, m.jurusan, "
                   + "n.mata_kuliah, n.nilai_tugas1, n.nilai_tugas2, n.nilai_UTS, "
                   + "n.nilai_tugas3, n.nilai_tugas4, n.nilai_UAS "
                   + "FROM mahasiswa m "
                   + "JOIN nilai n ON m.nim = n.nim";
                   
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
             
            while (rs.next()) {
                list.add(new MahasiswaModel(
                    rs.getInt("id"),
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("jurusan"),
                    rs.getString("mata_kuliah"),
                    rs.getInt("nilai_tugas1"),
                    rs.getInt("nilai_tugas2"),
                    rs.getInt("nilai_UTS"),
                    rs.getInt("nilai_tugas3"),
                    rs.getInt("nilai_tugas4"),
                    rs.getInt("nilai_UAS")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getAll: " + e.getMessage());
        }
        return list;
    }

    // QUERY: Ambil data berdasarkan NIM
    public static ArrayList<MahasiswaModel> getByNIM(String nim) {
        ArrayList<MahasiswaModel> list = new ArrayList<>();
        String sql = "SELECT m.id, m.nim, m.nama, m.jurusan, "
                   + "n.mata_kuliah, n.nilai_tugas1, n.nilai_tugas2, n.nilai_UTS, "
                   + "n.nilai_tugas3, n.nilai_tugas4, n.nilai_UAS "
                   + "FROM mahasiswa m "
                   + "JOIN nilai n ON m.nim = n.nim "
                   + "WHERE m.nim = ?";
                   
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, nim);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(new MahasiswaModel(
                    rs.getInt("id"),
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("jurusan"),
                    rs.getString("mata_kuliah"),
                    rs.getInt("nilai_tugas1"),
                    rs.getInt("nilai_tugas2"),
                    rs.getInt("nilai_UTS"),
                    rs.getInt("nilai_tugas3"),
                    rs.getInt("nilai_tugas4"),
                    rs.getInt("nilai_UAS")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getByNIM: " + e.getMessage());
        }
        return list;
    }

    // QUERY: Tambah data mahasiswa + nilai
    public static boolean tambah(String nim, String nama, String jurusan,
                                 String mataKuliah,
                                 int t1, int t2, int uts,
                                 int t3, int t4, int uas) {
                                 
        String sqlMhs = "INSERT INTO mahasiswa (nim, nama, jurusan) VALUES (?, ?, ?)";
        String sqlNilai = "INSERT INTO nilai (nim, mata_kuliah, nilai_tugas1, nilai_tugas2, nilai_UTS, nilai_tugas3, nilai_tugas4, nilai_UAS) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        
        try (Connection conn = Koneksi.getConnection()) {
            conn.setAutoCommit(false); // gunakan transaksi
            
            try (PreparedStatement psMhs = conn.prepareStatement(sqlMhs);
                 PreparedStatement psNilai = conn.prepareStatement(sqlNilai)) {
                 
                psMhs.setString(1, nim);
                psMhs.setString(2, nama);
                psMhs.setString(3, jurusan);
                psMhs.executeUpdate();
                
                psNilai.setString(1, nim);
                psNilai.setString(2, mataKuliah);
                psNilai.setInt(3, t1);
                psNilai.setInt(4, t2);
                psNilai.setInt(5, uts);
                psNilai.setInt(6, t3);
                psNilai.setInt(7, t4);
                psNilai.setInt(8, uas);
                psNilai.executeUpdate();
                
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error tambah (rollback): " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error tambah: " + e.getMessage());
            return false;
        }
    }

    // QUERY: Hapus data mahasiswa + nilai berdasarkan ID
    public static boolean hapus(int id) {
        String sqlGetNim = "SELECT nim FROM mahasiswa WHERE id = ?";
        String sqlHapusNilai = "DELETE FROM nilai WHERE nim = ?";
        String sqlHapusMhs = "DELETE FROM mahasiswa WHERE id = ?";
        
        try (Connection conn = Koneksi.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Dapatkan NIM
                String nim = null;
                PreparedStatement psGet = conn.prepareStatement(sqlGetNim);
                psGet.setInt(1, id);
                ResultSet rs = psGet.executeQuery();
                if (rs.next()) nim = rs.getString("nim");
                psGet.close();
                
                if (nim == null) return false;
                
                // Hapus nilai dulu (foreign key)
                PreparedStatement psNilai = conn.prepareStatement(sqlHapusNilai);
                psNilai.setString(1, nim);
                psNilai.executeUpdate();
                psNilai.close();
                
                // Hapus mahasiswa
                PreparedStatement psMhs = conn.prepareStatement(sqlHapusMhs);
                psMhs.setInt(1, id);
                psMhs.executeUpdate();
                psMhs.close();
                
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error hapus (rollback): " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error hapus: " + e.getMessage());
            return false;
        }
    }
}