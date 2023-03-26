package org.example.services;

import com.mysql.cj.conf.DatabaseUrlContainer;
import org.example.domain.DatabaseConnectivity;
import org.example.domain.UserProfile;
import org.example.orm.UserProfileDAO;
import org.example.orm.UserProfileDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private static AccountService singleton;

    public static AccountService getInstance() {
        if (singleton == null)
            singleton = new AccountService();
        return singleton;
    }

    //private final DatabaseConnectivity dbc;
    private static UserProfileDAO dao = new UserProfileDAOImpl();
    private final Map<String, UserProfile> sessionIdToProfile;

    private AccountService() {
        //this.dbc = new DatabaseConnectivity();
        this.sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) {
        dao.add(userProfile);
        /*try {
            String query = String.format("INSERT INTO `users` (`login`, `password`, `email`) VALUES ('%s', '%s', '%s');",
                    userProfile.getLogin(), userProfile.getPass(), userProfile.getEmail());
            dbc.getStatement().executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }

    public UserProfile getUserByLogin(String login) {
        return dao.getByLogin(login);
        /*String query = String.format("SELECT * FROM users WHERE login='%s'", login);
        try {
            ResultSet rs = dbc.getStatement().executeQuery(query);
            rs.next();
            UserProfile user = new UserProfile(rs.getString(2),
                    rs.getString(3),
                    rs.getString(4));
            return user;
        } catch (SQLException e) {
            return null;
        }*/
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
