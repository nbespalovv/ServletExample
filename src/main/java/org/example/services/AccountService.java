package org.example.services;

import org.example.domain.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private static AccountService singleton;
    public static AccountService getInstance() {
        if (singleton == null)
            singleton = new AccountService();
        return singleton;
    }

    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    private AccountService() {
        this.loginToProfile = new HashMap<>();
        this.sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
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
