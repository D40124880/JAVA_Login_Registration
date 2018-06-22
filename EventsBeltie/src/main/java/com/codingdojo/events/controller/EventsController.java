package com.codingdojo.events.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.events.models.Comment;
import com.codingdojo.events.models.Event;
import com.codingdojo.events.models.User;
import com.codingdojo.events.models.UserEvent;
import com.codingdojo.events.service.EventService;

@Controller
public class EventsController {
	
	private final EventService eventService;
	
	public EventsController(EventService eventService) {
		this.eventService = eventService;
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(Model model, @ModelAttribute Event event, HttpSession session) {
		Long tempUser = (Long) session.getAttribute("uid");
		User userDetail = eventService.findUser(tempUser);
		model.addAttribute("user", userDetail);
		
//		In state section
		List<Event> allinstate = new ArrayList<Event>();
		List<Object[]> testRun = eventService.test(userDetail.getState());
		for( int x = 0; x < testRun.size(); x++) {
			Object[] runTest = testRun.get(x);
			Event rtEvent = (Event) runTest[0];
			allinstate.add(rtEvent);
		}
		List<Event> instate = new ArrayList<Event>();
		List<Event> hoster = new ArrayList<Event>();
		List<Event> joinis = new ArrayList<Event>();
		for(Event e : allinstate) {
			if(e.getUsers().contains(userDetail) && e.getHost() == userDetail) {
					hoster.add(e);
			} else if(e.getUsers().contains(userDetail)) {
					joinis.add(e);
			} else {
					instate.add(e);
			}
		}
//		List<Event> hosted = eventService.userHosted(userDetail);
		model.addAttribute("host", hoster);
//		List<Event> joined = eventService.userJoined(userDetail);
		model.addAttribute("join", joinis);
//		List<Event> exclude = eventService.userExclude(userDetail);
		model.addAttribute("excl", instate);
//		in state section end
//		Out of state section
		List<Event> outofstate = new ArrayList<Event>();
		List<Object[]> testRun2 = eventService.test2(userDetail.getState());
		for( int x = 0; x < testRun2.size(); x++) {
			Object[] runTest2 = testRun2.get(x);
			Event rtEvent = (Event) runTest2[0];
			outofstate.add(rtEvent);
		}
		List<Event> outinstate = new ArrayList<Event>();
		List<Event> outhoster = new ArrayList<Event>();
		List<Event> outjoinis = new ArrayList<Event>();
		for(Event e : outofstate) {
			if(e.getUsers().contains(userDetail) && e.getHost() == userDetail) {
					outhoster.add(e);
			} else if(e.getUsers().contains(userDetail)) {
					outjoinis.add(e);
			} else {
					outinstate.add(e);
			}
		}
//		List<Event> hosted = eventService.userHosted(userDetail);
		model.addAttribute("ohost", outhoster);
//		List<Event> joined = eventService.userJoined(userDetail);
		model.addAttribute("ojoin", outjoinis);
//		List<Event> exclude = eventService.userExclude(userDetail);
		model.addAttribute("oexcl", outinstate);
//		List<Event> outOfState = eventService.getOutOfState(userDetail, userDetail.getState());
//		model.addAttribute("ofs", outOfState);
//		Out of state section end
		
		return "/events/Dashboard.jsp";		
	}
	
	@RequestMapping(value="/event/new", method=RequestMethod.POST)
	public String createEvent(@Valid @ModelAttribute Event event, BindingResult result, HttpSession session, Model model, @RequestParam("eventdate") String eventdate) {
		Long tempUser = (Long) session.getAttribute("uid");
		User userDetail = eventService.findUser(tempUser);
		event.setHost(userDetail);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		try {
			Date tempDate = format.parse(eventdate);
			Date curDate = new Date();
			if(tempDate.before(curDate)) {
				model.addAttribute("error", "Date can not be before current date");
				return "/events/Dashboard.jsp";
			} else {
				event.setEventDate(tempDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(result.hasErrors()) {
			System.out.println("has errors");
			return "/events/Dashboard.jsp";
		} else {		
			Event tempEvent = eventService.createEvent(event);
			UserEvent tempUE = new UserEvent();
			tempUE.setEvent(tempEvent);
			tempUE.setUser(userDetail);
			eventService.addlink(tempUE);
			return "redirect:/dashboard";
		}
	}
	
	@RequestMapping("/events/{eid}")
	public String displayEvent(@ModelAttribute Comment comment, Model model, @PathVariable("eid") Long eid) {
		Event tempEvent = eventService.findEvent(eid);
		model.addAttribute("edata", tempEvent);
		List<User> Joined = eventService.userJoinEvent(tempEvent);
		model.addAttribute("ujoin", Joined);
		return "/events/Show.jsp";
	}
	
	@RequestMapping(value="/events/{eid}", method=RequestMethod.POST)
	public String addComment(@Valid @ModelAttribute Comment comment, BindingResult result, @PathVariable("eid") Long eid) {
		Event tempEvent = eventService.findEvent(eid);
		comment.setEvent(tempEvent);
		if(result.hasErrors()) {
			return "/events/Show.jsp";
		} else {
			eventService.createComment(comment);
			return "redirect:/events/" + eid;
		}
	}
	
	@RequestMapping("/events/{eid}/edit")
	public String editEvent(Model model, @PathVariable("eid") Long eid) {
		Event event = eventService.findEvent(eid);
		System.out.println(event.getId());
		model.addAttribute("event", event);
		return "/events/Edit.jsp";
	}
	
	@RequestMapping(value="/events/{id}/edit", method=RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("event") Event event, BindingResult result, @RequestParam("eventdate") String eventdate, HttpSession session) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		try {
			Date tempDate = format.parse(eventdate);
			Date curDate = new Date();
			if(tempDate.before(curDate)) {
				event.setEventDate(curDate);
			} else {
				event.setEventDate(tempDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(result.hasErrors()) {
			return "/events/Edit.jsp";
		} else {
			System.out.println(event);
			Long tempUser = (Long) session.getAttribute("uid");
			User userDetail = eventService.findUser(tempUser);
			event.setHost(userDetail);
			Event tempEvent = eventService.updateEvent(event);
//			UserEvent tempUE = new UserEvent();
//			tempUE.setEvent(tempEvent);
//			tempUE.setUser(userDetail);
//			eventService.addlink(tempUE);
			return "redirect:/dashboard";
		}
	}
	
	@RequestMapping("/events/{jid}/join")
	public String join(@PathVariable("jid") Long jid, HttpSession session) {
		Long tempUser = (Long) session.getAttribute("uid");
		User userDetail = eventService.findUser(tempUser);
		Event eventDetail = eventService.findEvent(jid);
		UserEvent tempUE = new UserEvent();
		tempUE.setEvent(eventDetail);
		tempUE.setUser(userDetail);
		eventService.addlink(tempUE);
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value="/events/{id}", method=RequestMethod.DELETE)
	public String destroy(@PathVariable("id") Long id) {
		eventService.deleteEvent(id);
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value="/events/cancel/{id}", method=RequestMethod.DELETE)
	public String destroyJoin(@PathVariable("id") Long id, HttpSession session) {
		Long tempUser = (Long) session.getAttribute("uid");
		User userDetail = eventService.findUser(tempUser);
		Event eventDetail = eventService.findEvent(id);
		eventService.deleteJoin(userDetail, eventDetail);
		return "redirect:/dashboard";
	}

}	
