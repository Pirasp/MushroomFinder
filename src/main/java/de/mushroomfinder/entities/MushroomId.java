package de.mushroomfinder.entities;

import javax.persistence.*;

@
        Entity
@Table(name = "mushroom_ids")
public class MushroomId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userid;
    private byte[] picture;
    private String location;
    private String description;
    private Integer status;
    private Long mushroomid;
    //getters and setters
}
