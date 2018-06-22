package com.codingdojo.events.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(min = 2, message="First Name must be longer than two characters")
	private String fname;
	@Size(min = 2, message="Last Name must be longer than two characters")
	private String lname;
	@Email(message = "Email must be valid")
	private String email;
	@Size(min = 2, message="City must be longer than two chracters")
	private String city;
	@NotNull(message = "State can not be left blank")
	private String state;
	@Size(min = 5, message="Password must be greater than 5 characters")
	private String password;
	@Transient
	private String passconf;
	@Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    
    @OneToMany(mappedBy="host", fetch=FetchType.LAZY)
    private List<Event> eventHost;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "users_events",
    		joinColumns = @JoinColumn(name="user_id"),
    		inverseJoinColumns = @JoinColumn(name="event_id")
	)
    private List<Event> events;
    
	public User() {
	}

	public User(Long id, String fname, String lname, String email,
			String city, String state, String password, String passconf,
			Date createdAt, Date updatedAt, List<Event> events) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.city = city;
		this.state = state;
		this.password = password;
		this.passconf = passconf;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.events = events;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassconf() {
		return passconf;
	}

	public void setPassconf(String passconf) {
		this.passconf = passconf;
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

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
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
		return "User [id=" + id + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", city=" + city
				+ ", state=" + state + ", password=" + password + ", passconf=" + passconf + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}	
}
