/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import Model.Task;
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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * REST Web Service
 *
 * @author lwown
 */
@Path("tasks")
public class TaskResource {
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getTasks() {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();

        Query q = session.createQuery("from Task");
        List<Task> tasks = q.list();
        
        session.getTransaction().commit();
        return tasks;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Task postTask(Task newTask) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        session.save(newTask);
        
        session.getTransaction().commit();
        return newTask;
    }
    
    @Path("/{taskid}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Task getTaskWithId(@PathParam("taskid") long id) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from Task where Id = " + id);
        Task task = (Task) q.uniqueResult();
        
        session.getTransaction().commit();
        return task;
    }
    
    @Path("/{taskid}")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Task updateTaskWithId(Task newTask, @PathParam("taskid") long id) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from Task where Id = " + id);
        Task task = (Task) q.uniqueResult();
        task.setDueDate(newTask.getDueDate());
        task.setIsDone(newTask.getIsDone());
        task.setText(newTask.getText());
        session.saveOrUpdate(task);
        
        session.getTransaction().commit();
        return task;
    }
    
    @Path("/{taskid}")
    @DELETE
    @Produces(MediaType.APPLICATION_XML)
    public Task deleteTask(@PathParam("taskid") int id) {
        System.out.println("deleteTask()");
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from Task where id = " + id);
        Task task = (Task) q.uniqueResult();
        System.out.println(task);
        session.delete(task);
        session.getTransaction().commit();
        return task;
    }

    @Path("/populate")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String populate() throws ParseException {
        SessionFactory sf = HibernateStuff.getInstance().getSessionFactory();
        
        Session session = sf.openSession();
        session.beginTransaction();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2017-12-31");
        Date date2 = sdf.parse("2017-04-30");
        
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("task 1", date1));
        tasks.add(new Task("task 2", date2));
        
        tasks.forEach((task) -> {
            session.saveOrUpdate(task);
        });
        
        session.getTransaction().commit();
        
        return "Done";
    }
}
