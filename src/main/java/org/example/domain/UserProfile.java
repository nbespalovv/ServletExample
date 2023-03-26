package org.example.domain;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserProfile {
    public UserProfile() {
    }

    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String pass;
    @Column(name = "email")
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public UserProfile(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfile profile = (UserProfile) o;

        if (!login.equals(profile.login)) return false;
        return pass.equals(profile.pass);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + pass.hashCode();
        return result;
    }
}
