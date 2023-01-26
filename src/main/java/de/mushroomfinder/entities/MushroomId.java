package de.mushroomfinder.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mushroom_ids")
public class MushroomId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userid")
    private Long userId;
    @Column(name = "picture")
    private byte[] picture;
    @Column(name = "location")
    private String location;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Integer status;
    @Column(name = "solverid")
    private Long solverid;
    @Column(name = "mushroomid")
    private Long mushroomid;
    @Column(name = "date")
    private LocalDateTime date;

    public void setSolverid(Long id){
        this.solverid = id;
    }
    public Long getSolverid(){
        return solverid;
    }
    public void setDate(LocalDateTime date){
        this.date = date;
    }
    public LocalDateTime getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getMushroomid() {
        return mushroomid;
    }

    public void setMushroomid(Long mushroomid) {
        this.mushroomid = mushroomid;
    }
}
