/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import Model.Task;
import Model.User;
import Util.HibernateStuff;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * REST Web Service
 *
 * @author lwown
 */
@Path("users")
public class UserResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<User> getUsers() {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();

        Query q = session.createQuery("from User");
        List<User> users = q.list();
        
        session.getTransaction().commit();
        return users;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public User addUser(User user) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        session.save(user);
        
        session.getTransaction().commit();
        return user;
    }
    
    @Path("/{username}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public User getUserWithUsername(@PathParam("username") String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from User where userName = '" + username + "'");
        User user = (User) q.uniqueResult();
        
        session.getTransaction().commit();
        return user;
    }
    
    @Path("/{username}")
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public User addTask(Task newTask, @PathParam("username") String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from User where userName = '" + username + "'");
        User user = (User) q.uniqueResult();
        user.getTasks().add(newTask);
        session.saveOrUpdate(newTask);
        session.saveOrUpdate(user);
        
        session.getTransaction().commit();
        return user;
    }
    
    @Path("/{username}")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public User updateUser(User newUser, @PathParam("username") String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from User where userName = '" + username + "'");
        User user = (User) q.uniqueResult();

        user.setFullName(newUser.getFullName());
        user.setPassword(newUser.getPassword());
        
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        return user;
    }
    
    @Path("/{username}")
    @DELETE
    @Produces(MediaType.APPLICATION_XML)
    public User deleteUser(@PathParam("username") String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from User where userName = '" + username + "'");
        User user = (User) q.uniqueResult();

        session.delete(user);
        session.getTransaction().commit();
        return user;
    }
    
    @Path("/populate")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String populate() throws ParseException {
        SessionFactory sf = HibernateStuff.getInstance().getSessionFactory();
        
        Session session = sf.openSession();
        session.beginTransaction();
        
        User u1 = new User("abc");
        User u2 = new User("def");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2017-12-31");
        Date date2 = sdf.parse("2017-04-30");
        
        Task t1 = new Task("task 1", date1);
        Task t2 = new Task("task 2", date2);
        Task t3 = new Task("task 3", date2);
        
        u1.getTasks().add(t1);
        u1.getTasks().add(t2);
        u2.getTasks().add(t3);
        
        session.saveOrUpdate(t1);
        session.saveOrUpdate(t2);
        session.saveOrUpdate(t3);
        session.saveOrUpdate(u1);
        session.saveOrUpdate(u2);
        
        session.getTransaction().commit();
        
        return "Done";
    }
    
}
