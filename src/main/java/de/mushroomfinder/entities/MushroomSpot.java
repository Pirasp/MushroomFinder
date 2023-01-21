package de.mushroomfinder.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name="mushroomspots")
public class MushroomSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty
    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty
    private double latitude;
    @JsonProperty
    private double longitude;

/*    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idmushroom", referencedColumnName = "id")
    Mushroom mushroom;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    Spot spot;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

/*    public Mushroom getMushroom() {
        return mushroom;
    }

    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture){
        this.picture = picture;
    }
}
