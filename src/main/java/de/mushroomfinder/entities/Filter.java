package de.mushroomfinder.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="filter")
public class Filter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private float distance;
	
	
	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	@ManyToMany(/*fetch = FetchType.EAGER*/)
	@JoinTable(
			name="filter_mushroom",
			joinColumns = @JoinColumn(name="filterid"),
			inverseJoinColumns = @JoinColumn(name="mushroomid")
			)
	@LazyCollection(LazyCollectionOption.FALSE)
	List<Mushroom> mushrooms = new ArrayList<Mushroom>();
	
	@OneToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Mushroom> getMushrooms() {
		return mushrooms;
	}

	public void setMushrooms(List<Mushroom> mushrooms) {
		this.mushrooms = mushrooms;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	
	
}
