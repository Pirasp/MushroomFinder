package de.mushroomfinder.entities;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="verificationtoken")
public class VerificationToken {
	
	private static final int EXPIRATION = 60*36;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String token;
	
	@OneToOne
	@JoinColumn(name="userid", referencedColumnName="id")
	private User user;
	
	@Column(name="expirydate")
	private Date expiryDate;
	
	private Date calcExpiryDate(int expTimeInMin) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expTimeInMin);
		return new Date(cal.getTime().getTime());
	}
	public VerificationToken() {
		
	}
	
	public VerificationToken(User user, String token) {
		this.user = user;
		this.token = token;
		this.expiryDate = calcExpiryDate(EXPIRATION);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public static int getExpiration() {
		return EXPIRATION;
	}
}
