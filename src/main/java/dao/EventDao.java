package dao;

import java.io.*;  
import java.util.*; 
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
 import models.Image;
 import models.imagesDto;
 import models.ImageDto;
 import javax.persistence.*;
import models.Article;
import models.ArticleDto;
import models.ArticlesDto;
import models.Event;
import models.EventDto;
import models.EventsDto;
import models.User;
import models.UserDto;
import models.Event;
import models.Event_authorids;
import models.Event_authoridsDto;
import models.Events_authoridsDto;
import ninja.jpa.UnitOfWork;
import controllers.LoginLogoutController;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class EventDao {
   
    @Inject

    Provider<EntityManager> entitiyManagerProvider;
     LoginLogoutController newu=new LoginLogoutController();
    long cuser=newu.getcuser();
    
    
    @UnitOfWork
    public EventsDto getAllEvents() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Event> q = entityManager.createQuery("SELECT x FROM Event x", Event.class);
        List<Event> events = q.getResultList();        

        EventsDto eventsDto = new EventsDto();
        eventsDto.events = events;
        
        return eventsDto;
        
    }
    
    @Transactional
    public boolean postEvent(String username, EventDto eventDto) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
        User user = q.setParameter("usernameParam", username).getSingleResult();
        
        if (user == null) {
            return false;
        }
        
        Event event = new Event(user, eventDto.name, eventDto.eventmanager,eventDto.eventdate,eventDto.eventtype ,eventDto.location);
        entityManager.persist(event);
        
        
        return true;
        
    }
    
    @UnitOfWork
    public Event getFirstEventForFrontPage() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Event> q = entityManager.createQuery("SELECT x FROM Event x ORDER BY x.id DESC ", Event.class);
        Event event = q.setMaxResults(1).getSingleResult();      
        
        return event;
        
        
    }
    
    @UnitOfWork
    public List<Event> getOlderEventsForFrontPage() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
       // Query query = entityManager.createQuery("SELECT c.Event_id FROM Event_authorids c WHERE c.Event_id =:idd");
        TypedQuery<Event> q = entityManager.createQuery("SELECT x FROM Event x WHERE x.id IN (SELECT c.Event_id FROM Event_authorids c WHERE c.authorIds =:idd) ", Event.class);
        List<Event> events = q.setParameter("idd",newu.getcuser()).setFirstResult(0).setMaxResults(100).getResultList();            
        //SELECT c.Event_id FROM Event_authorids c WHERE c.authorIds =:idd
        //setParameter("idd",cuser)
        
        return events;
        
        
    }
    @UnitOfWork
    public Event getEvent(Long id) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Event> q = entityManager.createQuery("SELECT x FROM Event x WHERE x.id = :idParam", Event.class);
        Event event = q.setParameter("idParam", id).getSingleResult();        
        
        return event;
        
        
    }
    @UnitOfWork
    public void deleteEvent(Long id) {
    
       /* EntityManager em = entitiyManagerProvider.get();
        
        /*TypedQuery<Event> q = entityManager.createQuery("DELETE  FROM Event x WHERE x.id=:idParam",Event.class);
       // Event event = q.setParameter("idParam", id).getSingleResult();        
       q.executeUpdate();
        //return event;
    	 Query query = em.createQuery("DELETE FROM Event c WHERE c.id =1");
    		  query.executeUpdate();*/
    	 EntityManager em = entitiyManagerProvider.get();
         
         TypedQuery<Event> q = em.createQuery("SELECT x FROM Event x WHERE x.id = :idParam", Event.class);
         Event event = q.setParameter("idParam", id).getSingleResult();
         em.getTransaction().begin();
         em.remove(event);
         em.getTransaction().commit();
        
        
    }
    @Transactional
    public boolean updateEvent(Long id ,String username, EventDto eventDto) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
        User user = q.setParameter("usernameParam", username).getSingleResult();
        
        if (user == null) {
            return false;
        }
        Query query = entityManager.createQuery("UPDATE Event  x SET x.name='"+ eventDto.name + "'  WHERE x.id ='" + id +"'");
      query.executeUpdate();
      Query query1 = entityManager.createQuery("UPDATE Event  x SET x.eventmanager='"+ eventDto.eventmanager + "'  WHERE x.id ='" + id +"'");
      query1.executeUpdate();
      Query query2 = entityManager.createQuery("UPDATE Event  x SET x.eventtype='"+ eventDto.eventtype + "'  WHERE x.id ='" + id +"'");
      query2.executeUpdate();
      Query query3 = entityManager.createQuery("UPDATE Event  x SET x.eventdate='"+ eventDto.eventdate + "'  WHERE x.id ='" + id +"'");
      query3.executeUpdate();
      Query query5 = entityManager.createQuery("UPDATE Event  x SET x.location='"+ eventDto.location + "'  WHERE x.id ='" + id +"'");
      query5.executeUpdate();
        // Event event = new Event(user, eventDto.name, eventDto.eventmanager,eventDto.eventdate,eventDto.eventtype ,eventDto.location);
       // entityManager.persist(event);
        
        
        return true;
        
    }
    @Transactional
    public boolean newuser(String username,UserDto userDto) {
    	
        
    //    if (username != null && password != null) {
            
    	EntityManager entityManager = entitiyManagerProvider.get();
            User u= new User(userDto.username,userDto.password,userDto.username);
            entityManager.persist(u);
          
            return true;
        }
        //else {
        	
        	//return false;
        //}
            @UnitOfWork
            public User currentuser(String username) {
                
                EntityManager entityManager = entitiyManagerProvider.get();
                
                TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE x.username = :nameParam", User.class);
                User user = q.setParameter("nameParam", username).getSingleResult();        
                
                return user;
                
                
            } 
           @UnitOfWork 
         public  List idds() {
        	  
        	   
        	 EntityManager entityManager = entitiyManagerProvider.get();
           //return  entityManager.createQuery("SELECT c.Event_id FROM Event_authorids c WHERE c.authorIds =:idd").setParameter("idd",cuser).getResultList();
        	 ArrayList<Integer> a=new ArrayList<Integer>();
             a=(ArrayList<Integer>) entityManager.createQuery("SELECT c.Event_id FROM Event_authorids c WHERE c.authorIds =:idd").setParameter("idd",newu.getcuser()).getResultList();
        	 return a;
         }
           @UnitOfWork 
		public String getimagename(Long id) {
			// TODO Auto-generated method stub
			EntityManager entityManager = entitiyManagerProvider.get();
			String b= (String) entityManager.createQuery("SELECT c.imagename FROM Image c WHERE c.id =:idd").setParameter("idd",id).getSingleResult();
		     return b;
		}
        
 
    }


    
    

