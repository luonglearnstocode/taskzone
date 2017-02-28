/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import Dao.TaskDAO;
import Model.Feedback;
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
import javax.ws.rs.core.Response;
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
@Path("/")
public class TaskResource {
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getTasks(@PathParam("username") String username) {      
        TaskDAO dao = new TaskDAO();
        List tasks = dao.getTasks(username);
        return tasks;
    }
    
    @Path("/finished")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getFinishedTasks(@PathParam("username") String username) {      
        TaskDAO dao = new TaskDAO();
        List tasks = dao.getFinishedTask(username);
        return tasks;
    }
    
    @Path("/unfinished")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getUnfinishedTasks(@PathParam("username") String username) {      
        TaskDAO dao = new TaskDAO();
        List tasks = dao.getUnfinishedTask(username);
        return tasks;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response addTask(@PathParam("username") String username, Task newTask) {
        TaskDAO dao = new TaskDAO();
        dao.addTask(username, newTask);
        return Response.ok().build();
    }
    
    @Path("/{taskid}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Task getTaskWithId(@PathParam("taskid") long id) {
        TaskDAO dao = new TaskDAO();
        Task task = dao.getTaskWithId(id);
        return task;
    }
    
    @Path("/{taskid}")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Task updateTaskWithId(Task newTask, @PathParam("taskid") long id) {
        TaskDAO dao = new TaskDAO();
        Task task = dao.updateTask(newTask, id);
        return task;
    }
    
    @Path("/{taskid}")
    @DELETE
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteTask(@PathParam("username") String username, @PathParam("taskid") int id) {
        TaskDAO dao = new TaskDAO();
        if (dao.deleteTask(username, id)) return Response.ok().build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
//    @Path("/{taskid}/feedback")
//    @GET
//    @Produces(MediaType.APPLICATION_XML)
//    public Feedback getFeedback(@PathParam("taskid") long id) {
//        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
//        session.beginTransaction();
//        
//        Query q = session.createQuery("from Task where Id = " + id);
//        Task task = (Task) q.uniqueResult();
//        Feedback fb = task.getFeedback();
//        
//        session.getTransaction().commit();
//        return fb;
//    }
    
    @Path("/{taskid}/feedback")
    public FeedbackResource getFeedbackResource() {
        return new FeedbackResource();
    }

//    @Path("/populate")
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String populate() throws ParseException {
//        SessionFactory sf = HibernateStuff.getInstance().getSessionFactory();
//        
//        Session session = sf.openSession();
//        session.beginTransaction();
//        
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date1 = sdf.parse("2017-12-31");
//        Date date2 = sdf.parse("2017-04-30");
//        
//        ArrayList<Task> tasks = new ArrayList<>();
//        tasks.add(new Task("task 1", date1));
//        tasks.add(new Task("task 2", date2));
//        
//        tasks.forEach((task) -> {
//            session.saveOrUpdate(task);
//        });
//        
//        session.getTransaction().commit();
//        
//        return "Done";
//    }
}
