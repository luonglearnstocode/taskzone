/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import Dao.TaskDAO;
import Model.Task;
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
        List tasks = dao.getFinishedTasks(username);
        return tasks;
    }
    
    @Path("/unfinished")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getUnfinishedTasks(@PathParam("username") String username) {      
        TaskDAO dao = new TaskDAO();
        List tasks = dao.getUnfinishedTasks(username);
        return tasks;
    }
    
    @Path("/today")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getTasksDueToday(@PathParam("username") String username) {      
        TaskDAO dao = new TaskDAO();
        List tasks = dao.getTasksDueToday(username);
        return tasks;
    }
    
    @Path("/overdue")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getOverdueTasks(@PathParam("username") String username) {      
        TaskDAO dao = new TaskDAO();
        List tasks = dao.getOverdueTasks(username);
        return tasks;
    }
    
    @Path("/toapprove")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getFinishedButNotYetApprovedTasks(@PathParam("username") String username) {      
        TaskDAO dao = new TaskDAO();
        List tasks = dao.getFinishedButNotYetApprovedTasks(username);
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
    
    
    @Path("/{taskid}/feedback")
    public FeedbackResource getFeedbackResource() {
        return new FeedbackResource();
    }

}
