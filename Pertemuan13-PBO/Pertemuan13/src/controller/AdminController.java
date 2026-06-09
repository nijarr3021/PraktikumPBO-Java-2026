package controller;

import model.MahasiswaModel;
import java.util.ArrayList;

public class AdminController {
    /**
     * Ambil semua data mahasiswa beserta nilainya.
     */
    public ArrayList<MahasiswaModel> getAllMahasiswa() {
        return MahasiswaModel.getAll();
    }

    /**
     * Tambah mahasiswa baru beserta nilainya.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean tambahMahasiswa(String nim, String nama, String jurusan,
                                   String mataKuliah,
                                   int t1, int t2, int uts,
                                   int t3, int t4, int uas) {
        return MahasiswaModel.tambah(nim, nama, jurusan, mataKuliah,
                                     t1, t2, uts, t3, t4, uas);
    }

    /**
     * Hapus mahasiswa beserta nilainya berdasarkan ID.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean hapusMahasiswa(int id) {
        return MahasiswaModel.hapus(id);
    }
}