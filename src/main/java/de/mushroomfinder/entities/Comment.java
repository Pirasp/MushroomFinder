package de.mushroomfinder.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    String message;
    int votes;
    
    //avoid infinite loop when returning spots as json in the spotsRestController
    @JsonIgnore
	@ManyToOne
    @JoinColumn(name="idspot")
    Spot spot;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate date;
	
	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
	List <CommentVote> commentVotes;
	

	public List<CommentVote> getCommentVotes() {
		return commentVotes;
	}
	public void setCommentVotes(List<CommentVote> commentVotes) {
		this.commentVotes = commentVotes;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getVotes() {
		this.votes = 0;
		List<CommentVote> votes = this.getCommentVotes();
		if(votes == null) {
			return 0;
		}
		for(int i = 0 ; i<votes.size(); i++) {
			this.votes +=votes.get(i).getVote();
		}
		return this.votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	public Spot getSpot() {
		return spot;
	}
	public void setSpot(Spot spot) {
		this.spot = spot;
	}

 
}
