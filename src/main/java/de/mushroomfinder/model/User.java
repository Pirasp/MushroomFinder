package de.mushroomfinder.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "Please, inform a valid E-Mail!")
    private String email;
    @Size(min = 6, message = "Password must contain at least 6 characters!")
    private String password;
    @Size(min = 4, message = "Login must contain at least 4 characters!")
    private String login;
    private Integer active;
    @Size(min = 4, message = "Name must contain at least 4 characters!")
    private String name;

    @Transient
    @Size(min = 6, message = "New Password must contain at least 6 characters!")
    private String newPassword1;
    @Size(min = 6, message = "New Password must contain at least 6 characters!")
    @Transient
    private String newPassword2;




    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="userauthority",
            joinColumns = @JoinColumn(name="iduser"),
            inverseJoinColumns = @JoinColumn(name="idauthority")
    )
    //my own type of authority, not from spring security
    private List<Authority> myauthorities = new ArrayList<Authority>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public List<Authority> getMyauthorities() {
        return myauthorities;
    }

    public void setMyauthorities(List<Authority> myauthorities) {
        this.myauthorities = myauthorities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

}
