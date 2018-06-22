package com.codingdojo.events.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.events.models.Event;
import com.codingdojo.events.models.User;
import com.codingdojo.events.models.UserEvent;

@Repository
public interface UserEventRepository extends CrudRepository<UserEvent, Long> {

	////////////////////////////////////////////////////////////////////////////////////
	//@Query("SELECT e FROM Event e JOIN e.users j WHERE j = ?1")
	//List<Event> eventsJoinByUser(User u);
	
	@Query("SELECT ue.event FROM UserEvent ue WHERE ue.user = ?1")
	List<Event> eventsJoinByUser2(User u);
	//
	//@Query("SELECT e from Event e JOIN e.users j  WHERE j != ?1")
	//List<Event> eventsNotByUser(User u);
	
	@Query("SELECT ue.event from UserEvent ue WHERE ue.user != ?1")
	List<Event> eventsNotByUser2(User u);
	
	@Query("SELECT e FROM Event e JOIN e.users j  WHERE j != ?1 AND e.eventstate != ?2 GROUP By j")
	List<Event> getOutOfState(User u, String state);
	////////////////////////////////////////////////////////
	
	@Query("SELECT ue.event, Count(ue.user) as number FROM UserEvent ue where ue.event.eventstate = ?1 group by ue.event")
	List<Object[]> test(String state);
	
	@Query("SELECT ue.event, Count(ue.user) as number FROM UserEvent ue where ue.event.eventstate != ?1 group by ue.event")
	List<Object[]> test2(String state);
		
}
