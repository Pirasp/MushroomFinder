package de.mushroomfinder.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="comment_vote")
public class CommentVote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer vote;

	@JsonIgnore
	 @ManyToOne
	 @JoinColumn(name="commentid")
	 private Comment comment;
	
	 @ManyToOne
	 
	 @JoinColumn(name="userid") private User user;
	 

	
	 public User getUser() { return user; }
	
	 public void setUser(User user) { this.user = user; }
	 



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVote() {
		return vote;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}


	 public Comment getComment() { return comment; }
	 
	 public void setComment(Comment comment) { this.comment = comment; }

	
}
