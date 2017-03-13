/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Feedback;
import Model.Task;
import Model.User;
import Util.HibernateStuff;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author lwown
 */
public class FeedbackDAOTest {
    
    public FeedbackDAOTest() {
    }
    
    @Before
    public void initialize() throws ParseException {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        session.createSQLQuery("truncate table Feedback").executeUpdate();
        session.createSQLQuery("truncate table Task").executeUpdate();
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date1 = sdf.parse("2017-03-12 12:00:00");
        Date date2 = sdf.parse("2017-03-13 23:59:59");
        
        Task t1 = new Task("task 1", date1);
        Task t2 = new Task("task 2", date2);
        Task t3 = new Task("task 3", date1);
        Task t4 = new Task("task 4", date2);
             
        t2.setIsDone(true);
        t3.setIsDone(true);
        
        Feedback f1 = new Feedback("first feedback");
        f1.setIsApproved(true);
        t2.setFeedback(f1);
        
        session.saveOrUpdate(t1);
        session.saveOrUpdate(t2);
        session.saveOrUpdate(t3);
        session.saveOrUpdate(t4);
        session.getTransaction().commit();
    }

    /**
     * Test of getFeedBack method, of class FeedbackDAO.
     */
    @Test
    public void testGetFeedBack() {
        System.out.println("getFeedBack1");
        long taskID = 2;
        FeedbackDAO instance = new FeedbackDAO();
        Feedback result = instance.getFeedBack(taskID);
        assertEquals(result.getId().intValue(), 2);
        assertEquals(result.getMessage(), "first feedback");
    }
    
    @Test
    public void testGetFeedBack2() {
        System.out.println("getFeedBack2");
        long taskID = 1;
        FeedbackDAO instance = new FeedbackDAO();
        Feedback result = instance.getFeedBack(taskID);
        assertEquals(result.getId().intValue(), 1);
        assertEquals(result.getMessage(), "N/A");
    }

    /**
     * Test of updateFeedback method, of class FeedbackDAO.
     */
    @Test
    public void testUpdateFeedback1() {
        System.out.println("updateFeedback1");
        Feedback fb = new Feedback("new feedback");
        fb.setIsApproved(true);
        long id = 1;
        FeedbackDAO instance = new FeedbackDAO();
        Feedback result = instance.updateFeedback(fb, id);
        assertEquals(result.getMessage(), "new feedback");
        assertTrue(result.isIsApproved());
    }
    
    @Test
    public void testUpdateFeedback2() {
        System.out.println("updateFeedback2");
        Feedback fb = new Feedback("new feedback");
        fb.setIsApproved(false);
        long id = 2;
        FeedbackDAO instance = new FeedbackDAO();
        Feedback result = instance.updateFeedback(fb, id);
        assertEquals(result.getMessage(), "new feedback");
        assertFalse(result.isIsApproved());
    }
    
}
