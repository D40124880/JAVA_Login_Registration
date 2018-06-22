package com.codingdojo.events.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.codingdojo.events.models.User;

@Entity
@Table(name="events")
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Size(min = 2, message="Event name must be greater than 2 characters")
	private String name;
	private Date eventDate;
	@Size(min = 2, message="Location must be longer than 2 characters")
	private String location;
	private String eventstate;
	@Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "host_id")
    private User host;
    
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="users_events",
			joinColumns=@JoinColumn(name="event_id"),
			inverseJoinColumns = @JoinColumn(name="user_id")
	)
	private List<User> users;
	
//	Gives me an array of people that have joined this event == [user1, user2, user15]
//			
//	Is the current user in this array? If the answer is yes, show unjoin. If the answer is no, then show join
//	
	@OneToMany(mappedBy="event", fetch=FetchType.LAZY)
	private List<Comment> comments;

	public Event() {
	}

	

	public Event(Long id, String name,
			Date eventDate,
			String location, String eventstate,
			Date createdAt, Date updatedAt, User host, List<User> users, List<Comment> comments) {
		this.id = id;
		this.name = name;
		this.eventDate = eventDate;
		this.location = location;
		this.eventstate = eventstate;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.host = host;
		this.users = users;
		this.comments = comments;
	}

	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Date getEventDate() {
		return eventDate;
	}



	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getEventstate() {
		return eventstate;
	}



	public void setEventstate(String eventstate) {
		this.eventstate = eventstate;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}



	public User getHost() {
		return host;
	}



	public void setHost(User host) {
		this.host = host;
	}



	public List<User> getUsers() {
		return users;
	}



	public void setUsers(List<User> users) {
		this.users = users;
	}



	public List<Comment> getComments() {
		return comments;
	}



	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}



	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }



	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", eventDate=" + eventDate + ", location=" + location
				+ ", eventstate=" + eventstate + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", host="
				+ host + ", users=" + users + ", comments=" + comments + "]";
	}

	
}