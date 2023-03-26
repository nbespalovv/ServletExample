package org.example.orm;

import org.example.domain.UserProfile;

import java.util.List;

public interface UserProfileDAO {
    UserProfile get(long id);
    List<UserProfile> getAll();
    void add(UserProfile dataSet);
    UserProfile getByLogin(String login);
}