package engine.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AppUser {
    @Id
    @GeneratedValue
    private Integer id;

    private String email;
    private String password;
    private String authority;

    public AppUser(String email, String password, String authority) {
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public AppUser(Integer id, String email, String password, String authority) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public AppUser() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
