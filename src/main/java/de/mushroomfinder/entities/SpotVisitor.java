package de.mushroomfinder.entities;
import de.mushroomfinder.entities.Spot;
import javax.persistence.*;
import java.time.LocalDateTime;

public class SpotVisitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;
    @ManyToOne
    private Spot spot;
    private String visitorName;



    public void setDate(LocalDateTime date){
        this.date = date;
    }
    public LocalDateTime getDate() {
        return date;
    }
}
