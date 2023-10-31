
package org.august.userinfo;

import org.august.dtomodel.UserModel;

import java.util.HashMap;
import java.util.Map;

public class DatabaseUserInfo {
    private static DatabaseUserInfo kolyaSvestun;
    private final Map<String, UserModel> databaseUser;

    private DatabaseUserInfo() {
        this.databaseUser = new HashMap<>();
    }

    public static synchronized DatabaseUserInfo getInstance() {
        if (kolyaSvestun == null) {
            kolyaSvestun = new DatabaseUserInfo();
        }
        return kolyaSvestun;
    }

    public Map<String, UserModel> getDatabaseUser() {
        return this.databaseUser;
    }
}

