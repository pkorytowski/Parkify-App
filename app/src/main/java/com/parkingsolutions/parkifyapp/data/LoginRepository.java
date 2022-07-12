package com.parkingsolutions.parkifyapp.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.parkingsolutions.parkifyapp.DrawerActivity;
import com.parkingsolutions.parkifyapp.MainActivity;
import com.parkingsolutions.parkifyapp.data.model.AuthorizedUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private AuthorizedUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);
        prefs.edit().remove("token").apply();
        prefs.edit().remove("isLogin").apply();
        prefs.edit().remove("id").apply();
        prefs.edit().remove("name").apply();
        prefs.edit().remove("surname").apply();
        prefs.edit().remove("email").apply();
        prefs.edit().remove("rank").apply();
    }

    private void setLoggedInUser(AuthorizedUser user) {
        this.user = user;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);
        //prefs.edit().putString("token", user.getId()).commit();
        user.saveSharedPrefs(MainActivity.context);
        prefs.edit().putBoolean("isLogin", true).commit();
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<AuthorizedUser> login(String username, String password) {
        // handle login
        Result<AuthorizedUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<AuthorizedUser>) result).getData());
        }
        return result;
    }
}