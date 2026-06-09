package controller;

import model.MahasiswaModel;
import java.util.ArrayList;

public class MahasiswaController {
    /**
     * Ambil data nilai mahasiswa berdasarkan NIM.
     */
    public ArrayList<MahasiswaModel> getNilaiByNIM(String nim) {
        return MahasiswaModel.getByNIM(nim);
    }
}