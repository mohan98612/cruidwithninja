package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
 
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
        
        TypedQuery<Event> q = entityManager.createQuery("SELECT x FROM Event x ORDER BY x.id DESC", Event.class);
        List<Event> events = q.setFirstResult(0).setMaxResults(100).getResultList();            
        
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
   /* @Transactional
    public boolean updateEvent(String username, EventDto eventDto) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
        User user = q.setParameter("usernameParam", username).getSingleResult();
        
        if (user == null) {
            return false;
        }
        
        Event event = new Event(user, eventDto.name, eventDto.eventmanager,eventDto.eventdate,eventDto.eventtype ,eventDto.location);
        entityManager.persist(event);
        
        
        return true;
        
    }*/
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
        
 
    }


    
    

