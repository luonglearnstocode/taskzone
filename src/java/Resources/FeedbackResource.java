/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import Dao.FeedbackDAO;
import Model.Feedback;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author lwown
 */
@Path("/f")
public class FeedbackResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Feedback getFeedback(@PathParam("taskid") long id) {
        FeedbackDAO dao = new FeedbackDAO();
        Feedback fb = dao.getFeedBack(id);
        return fb;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Feedback updateFeedback(Feedback fb, @PathParam("taskid") long id) {
        FeedbackDAO dao = new FeedbackDAO();
        Feedback feedback = dao.updateFeedback(fb, id);
        return feedback;
    }
}
