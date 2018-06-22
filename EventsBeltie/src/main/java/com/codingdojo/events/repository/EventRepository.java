package com.codingdojo.events.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codingdojo.events.models.Event;
import com.codingdojo.events.models.User;
import com.codingdojo.events.models.UserEvent;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
//	
	@Query("SELECT e FROM Event e WHERE e.host = ?1")
	List<Event> eventsHostedByUser(User u);
	
	@Query("SELECT u FROM User u JOIN u.events j WHERE j = ?1")
	List<User> usersJoinedEvent(Event e);
///////////////////////////////////////////////////////////////////////////////////////	
////	@Query("SELECT e FROM Event e JOIN e.users j WHERE j = ?1")
////	List<Event> eventsJoinByUser(User u);
//	
//	@Query("Select ue.event from UserEvent ue WHERE ue.user = ?1")
//	List<Event> eventsJoinByUser2(User u);
////	
////	@Query("SELECT e from Event e JOIN e.users j  WHERE j != ?1")
////	List<Event> eventsNotByUser(User u);
//	
//	@Query("SELECT ue.event from UserEvent ue WHERE ue.user !=?1")
//	List<Event> eventsNotByUser2(User u);
//	
//	@Query("SELECT e FROM Event e JOIN e.users j  WHERE j != ?1 AND e.eventstate != ?2 GROUP By j")
//	List<Event> getOutOfState(User u, String state);
////////////////////////////////////////////////////////////////////////////////////////	
	@Transactional
	@Modifying
	@Query("delete UserEvent ue WHERE ue.user = ?1 and ue.event = ?2")
	void deleteJoin(User u, Event e);
}
