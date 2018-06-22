package com.codingdojo.events.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.events.models.Comment;
import com.codingdojo.events.models.Event;
import com.codingdojo.events.models.User;
import com.codingdojo.events.models.UserEvent;
import com.codingdojo.events.repository.CommentRepository;
import com.codingdojo.events.repository.EventRepository;
import com.codingdojo.events.repository.UserEventRepository;
import com.codingdojo.events.repository.UserRepository;

@Service
public class EventService {
	
	private final UserRepository userRepo;
	private final EventRepository eventRepo;
	private final UserEventRepository usereventRepo;
	private final CommentRepository commentRepo;
	
	public EventService(UserRepository userRepo, EventRepository eventRepo, UserEventRepository usereventRepo, CommentRepository commentRepo) {
		this.userRepo = userRepo;
		this.eventRepo = eventRepo;
		this.usereventRepo = usereventRepo;
		this.commentRepo = commentRepo;
	}
	
	public User findUser(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			return null;
		}
	}
	
	public List<Event> getOutOfState(User u, String state){
		return usereventRepo.getOutOfState(u, state);
	}
//	
	public List<Event> userHosted(User u){
		System.out.println("Calling event repo");
		return eventRepo.eventsHostedByUser(u);
	}
	
	
	public List<Event> userJoined(User u){
		List<Event> events = usereventRepo.eventsJoinByUser2(u);
		for(Event e : events) {
			System.out.println(e.getName());
		}
		return usereventRepo.eventsJoinByUser2(u);
	}
	
	public List<User> userJoinEvent(Event e){
		return eventRepo.usersJoinedEvent(e);
	}
//	
	public List<Event> userExclude(User u){
		return usereventRepo.eventsNotByUser2(u);
	}
	
	public Event findEvent(Long id) {
		Optional<Event> optionalEvent = eventRepo.findById(id);
		if(optionalEvent.isPresent()) {
			return optionalEvent.get();
		} else {
			return null;
		}
	}
	
	public Event createEvent(Event event) {
		return eventRepo.save(event);
	}

	public UserEvent addlink(UserEvent ue) {
		return usereventRepo.save(ue);
		
	}
	
	public Comment createComment(Comment c) {
		return commentRepo.save(c);
	}
	
	public Event updateEvent(Event e) {
		return eventRepo.save(e);
	}
	
	public void deleteEvent(Long id) {
		eventRepo.deleteById(id);
	}
	
	public void deleteJoin(User u, Event e) {
		eventRepo.deleteJoin(u, e);
	}
	
	public List<Object[]> test(String s){
		return usereventRepo.test(s);
	}
	
	public List<Object[]> test2(String s){
		return usereventRepo.test2(s);
	}
}
