package controller;

import model.UserModel;

public class LoginController {
    /**
     * Memproses login.
     * @return UserModel jika berhasil, null jika gagal.
     */
    public UserModel prosesLogin(String username, String password) {
        return UserModel.cariUser(username, password);
    }
}