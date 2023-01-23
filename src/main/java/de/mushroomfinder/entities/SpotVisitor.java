package de.mushroomfinder.entities;
import de.mushroomfinder.entities.Spot;
import javax.persistence.*;
import java.time.LocalDateTime;
import de.mushroomfinder.repository.SpotVisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "spotvisitors")
public class SpotVisitor {
    @Autowired
    private SpotVisitorRepository spotVisitorRepository;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="idspot")
    private Spot spot;

    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "visitorName")
    private String visitorName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Spot getSpot() {
        return spot;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public void setDate(LocalDateTime date){
        this.date = date;
    }
    public LocalDateTime getDate() {
        return date;
    }

    public SpotVisitor getLastVisitor() {
        return spotVisitorRepository.findFirstBySpotOrderByVisitDateDesc(this);
    }

    public Long getTotalVisitors() {
        return spotVisitorRepository.countBySpot(this);
    }

}
