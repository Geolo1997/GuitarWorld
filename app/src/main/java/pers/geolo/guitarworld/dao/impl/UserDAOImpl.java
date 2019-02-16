package pers.geolo.guitarworld.dao.impl;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import pers.geolo.guitarworld.base.BaseApplication;
import pers.geolo.guitarworld.dao.UserDAO;
import pers.geolo.guitarworld.model.User;
import pers.geolo.guitarworld.util.EncryptionUtils;

public class UserDAOImpl implements UserDAO {

    SharedPreferences preferences;

    SharedPreferences.Editor editor;

    public UserDAOImpl() {
        preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getContext());
    }

    @Override
    public void save(User user) {
        save(user.getUsername(), user.getPassword());
    }

    @Override
    public void save(String username, String password) {
        editor = preferences.edit();
        editor.putString("username", username);
        String encryptedPassword = EncryptionUtils.encrypt(password);
        editor.putString("password", encryptedPassword);
        editor.apply();
    }

    @Override
    public void save(String username) {
        editor = preferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    @Override
    public void setAutoLogin(boolean checked) {
        editor = preferences.edit();
        editor.putBoolean("isAutoLogin", checked);
        editor.apply();
    }

    @Override
    public User getUser() {
        String username = preferences.getString("username", null);
        String encryptedPassword = preferences.getString("password", null);
        String origin = EncryptionUtils.decrypt(encryptedPassword);
        return new User(username, origin);
    }

    @Override
    public boolean isAutoLogin() {
        return preferences.getBoolean("isAutoLogin", false);
    }

    @Override
    public void clearPassword() {
        editor = preferences.edit();
        editor.putString("password", null);
        editor.apply();
    }
}
