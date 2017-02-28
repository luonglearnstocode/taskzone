/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Feedback;
import Model.Task;
import Util.HibernateStuff;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author lwown
 */
public class FeedbackDAO {
    public Feedback getFeedBack(long taskID) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from Task where Id = " + taskID);
        Task task = (Task) q.uniqueResult();
        Feedback fb = task.getFeedback();
        
        session.getTransaction().commit();
        return fb;
    }
    
    public Feedback updateFeedback(Feedback fb, long id) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from Task where Id = " + id);
        Task task = (Task) q.uniqueResult();
        task.getFeedback().setMessage(fb.getMessage());
        task.getFeedback().setIsApproved(fb.isIsApproved());
        session.saveOrUpdate(task);
        session.getTransaction().commit();
        return task.getFeedback();
    }
}
