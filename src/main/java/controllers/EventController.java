package controllers;
import ninja.Context;
import models.EventDto;
import models.EventsDto;
import models.Article;
import models.ArticleDto;
import models.Image;
import models.Event;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.params.PathParam;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import com.google.inject.Inject;

import dao.EventDao;
import etc.LoggedInUser;

public class EventController {

    @Inject
    EventDao eventDao;

    public Result geteventsJson() {

      EventsDto eventsDto = eventDao.getAllEvents();

        return Results.json().render(eventsDto);

    }
    @FilterWith(SecureFilter.class)
    public Result eventNewPost(@LoggedInUser String username,
                                 Context context,
                                 @JSR303Validation EventDto eventDto,
                                 Validation validation) {

        if (validation.hasViolations()) {

            context.getFlashScope().error("Please correct field.");
            context.getFlashScope().put("name", eventDto.name);
            context.getFlashScope().put("eventmanager", eventDto.eventmanager);
            context.getFlashScope().put("eventdate", eventDto.eventdate);
            context.getFlashScope().put("eventtype", eventDto.eventtype);
            context.getFlashScope().put("location", eventDto.location);
            return Results.redirect("/event/new");

        }else {
            
            eventDao.postEvent(username, eventDto);
            
            context.getFlashScope().success("New article created.");
            
            return Results.redirect("/");
            //return Results.redirect("image/${id}")

        }
        

    
   /* public Result getEventJson(@PathParam("id") Long id) {
    
        Event event = eventDao.getevent(id);
        
        return Results.json().render(event);
    
    }*/
    }
    public Result articleShow(@PathParam("id") Long id) {

        Event event = null;

        if (id != null) {

            event = eventDao.getEvent(id);

        }

        return Results.html().render("event", event);

    }
    public Result eventdelete(@PathParam("id") Long id,Context context) {

       // Event event = null;

        if (id != null) {

             eventDao.deleteEvent(id);

        }
        context.getFlashScope().success("Event Deleted");
        return Results.redirect("/");

    }
  

        // Event event = null;

    	 @FilterWith(SecureFilter.class)
    	    public Result updateevent(@PathParam("id") Long id,@LoggedInUser String username,
    	                                 Context context,
    	                                 @JSR303Validation EventDto eventDto,
    	                                 Validation validation) {

    	     /*   if (validation.hasViolations()) {

    	            context.getFlashScope().error("Please correct field.");
    	            context.getFlashScope().put("name", eventDto.name);
    	            context.getFlashScope().put("eventmanager", eventDto.eventmanager);
    	            context.getFlashScope().put("eventdate", eventDto.eventdate);
    	            context.getFlashScope().put("eventtype", eventDto.eventtype);
    	            context.getFlashScope().put("location", eventDto.location);
    	            return Results.redirect("/Update_event");

    	        }else {*/
    	            
    	            eventDao.updateEvent(id,username, eventDto);
    	            
    	            context.getFlashScope().success("Event Updated");
    	            
    	            return Results.redirect("/");

    	        }
    	        

      

    }
    
