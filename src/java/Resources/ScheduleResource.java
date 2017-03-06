/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import Dao.ScheduleDAO;
import Model.Schedule;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
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
@Path("schedule")
public class ScheduleResource {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Schedule> getSchedules() {
        ScheduleDAO dao = new ScheduleDAO();
        List schedules = dao.getSchedules();
        return schedules;
    }
    
    @Path("/{scheduleId}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Schedule getScheduleWithId(@PathParam("scheduleId") long id) {
        ScheduleDAO dao = new ScheduleDAO();
        Schedule schedule = dao.getScheduleWithId(id);
        return schedule;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response addSchedule(Schedule newSchedule) {
        ScheduleDAO dao = new ScheduleDAO();
        dao.addSchedule(newSchedule);
        return Response.ok().build();
    }
    
    @Path("/{scheduleId}")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Schedule updateSchedule(Schedule newSchedule, @PathParam("scheduleId") long id) {
        ScheduleDAO dao = new ScheduleDAO();
        Schedule schedule = dao.updateSchedule(newSchedule, id);
        return schedule;
    }
    
    @Path("/{scheduleId}")
    @DELETE
    public Response deleteSchedule(@PathParam("scheduleId") long id) {
        ScheduleDAO dao = new ScheduleDAO();
        if (dao.deleteSchedule(id)) return Response.ok().build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @Path("/{scheduleId}/{username}")
    @POST
    public Response addUserToSchedule(@PathParam("scheduleId") long id, @PathParam("username") String username) {
        ScheduleDAO dao = new ScheduleDAO();
        dao.addUserToSchedule(id, username);
        return Response.ok().build();
    } 
    
    @Path("/{scheduleId}/{username}")
    @DELETE
    public Response deleteUserFromSchedule(@PathParam("id") long id, @PathParam("username") String username) {
        ScheduleDAO dao = new ScheduleDAO();
        dao.deleteUserFromSchedule(id, username);
        return Response.ok().build();
    } 
}
