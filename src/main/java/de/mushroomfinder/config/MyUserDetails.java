package de.mushroomfinder.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import de.mushroomfinder.entities.Authority;
import de.mushroomfinder.entities.User;


public class MyUserDetails implements UserDetails{


    private static final long serialVersionUID = 1L;
    private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;


    public MyUserDetails(User user) {

        // TODO Auto-generated constructor stub
        this.userName= user.getLogin();
        this.password= user.getPassword();
        System.out.println("password of the user is="+password);
        System.out.println("userName of the user is="+this.userName);
        this.active = (user.getActive()>0)? true : false;

        List<Authority> myauthorities = (List<Authority>) user.getMyauthorities();

        System.out.println("the user "+  user.getLogin() +" has "+
                myauthorities.size() +" authorities");

        ///authorities is a collection of GrantedAuthority from Spring Security
        authorities = new ArrayList<>();

        for (int i=0; i< myauthorities.size(); i++){
            authorities.add(new SimpleGrantedAuthority(myauthorities.get(i).getDescription().toUpperCase()));
            System.out.println("the profile" + i +" of "+ user.getLogin() + " is "+ myauthorities.get(0).getDescription());
        }



    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return this.authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return this.active;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

}
