package de.mushroomfinder.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.mushroomfinder.entities.Spot;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import de.mushroomfinder.repository.SpotVisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "spotvisitors")
public class SpotVisitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="idspot")
    @JsonIgnore
    private Spot spot;

    @JsonProperty
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
    @JsonProperty
    @Column(name = "visitorname")
    private String visitorname;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Spot getSpot() {
        return spot;
    }

    public String getVisitorname() {
        return visitorname;
    }

    public void setVisitorname(String visitorname) {
        this.visitorname = visitorname;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
    }


}
