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
import java.util.List;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author lwown
 */
public class TaskDAOTest {
    
    public TaskDAOTest() {
    }
    
    @Before
    public void initialize() throws ParseException {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
//        Query q = session.createQuery("delete from Task");
//        q.executeUpdate();
//        q = session.createQuery("delete from User");
//        q.executeUpdate();
        session.createSQLQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        session.createSQLQuery("truncate table Task").executeUpdate();
        session.createSQLQuery("truncate table USER_DETAILS").executeUpdate();
        
        User u1 = new User("abc");
        User u2 = new User("def");
        User u3 = new User("ijk");
        u3.setIsManager(true);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date1 = sdf.parse("2017-03-12 12:00:00");
        Date date2 = sdf.parse("2017-03-13 23:59:59");
        
        Task t1 = new Task("task 1", date1);
        Task t2 = new Task("task 2", date2);
        Task t3 = new Task("task 3", date1);
        Task t4 = new Task("task 4", date2);
        
        u1.getTasks().add(t1);
        u1.getTasks().add(t2);
        u2.getTasks().add(t3);
        u2.getTasks().add(t4);
        
        t1.setUser(u1);
        t2.setUser(u1);
        t3.setUser(u2);
        t4.setUser(u2);
        
        t2.setIsDone(true);
        t3.setIsDone(true);
        
        Feedback f1 = new Feedback("first feedback");
        f1.setIsApproved(true);
        t2.setFeedback(f1);
        
        session.saveOrUpdate(t1);
        session.saveOrUpdate(t2);
        session.saveOrUpdate(t3);
        session.saveOrUpdate(t4);
        session.saveOrUpdate(u1);
        session.saveOrUpdate(u2);
        session.saveOrUpdate(u3);
        session.getTransaction().commit();
    }

    /**
     * Test of getTasks method, of class TaskDAO.
     */
    @Test
    public void testGetTasksNotManager() {
        System.out.println("testGetTasksNotManager");
        String username = "abc";
        TaskDAO instance = new TaskDAO();
        List<Task> tasks = instance.getTasks(username);
        assertEquals(tasks.size(), 2);
        assertEquals(tasks.get(0).getId().intValue(), 1);
    }
    
    @Test
    public void testGetTasksManager() {
        System.out.println("testGetTasksManager");
        String username = "ijk";
        TaskDAO instance = new TaskDAO();
        List<Task> tasks = instance.getTasks(username);
        assertEquals(tasks.size(), 4);
        assertEquals(tasks.get(2).getId().intValue(), 3);
    }

    /**
     * Test of addTask method, of class TaskDAO.
     */
    @Test
    public void testAddTask() {
        System.out.println("addTask");
        String username = "def";
        Task newTask = new Task();
        TaskDAO instance = new TaskDAO();
        Task result = instance.addTask(username, newTask);
        assertEquals(result.getId().intValue(), 5);
        List<Task> tasks = instance.getTasks(username);
        assertEquals(tasks.size(), 3);
    }

    /**
     * Test of getTaskWithId method, of class TaskDAO.
     */
    @Test
    public void testGetTaskWithId() {
        System.out.println("getTaskWithId");
        long id = 1;
        TaskDAO instance = new TaskDAO();
        Task result = instance.getTaskWithId(id);
        assertEquals(result.getText(), "task 1");
    }

    /**
     * Test of updateTask method, of class TaskDAO.
     */
    @Test
    public void testUpdateTask() {
        System.out.println("updateTask");
        Task newTask = new Task();
        newTask.setTitle("updated title");
        long id = 1;
        newTask.setId(id);
        TaskDAO instance = new TaskDAO();
        Task result = instance.updateTask(newTask, id);
        assertEquals(result.getTitle(), "updated title");
    }

    /**
     * Test of deleteTask method, of class TaskDAO.
     */
    @Test
    public void testDeleteTask() {
        System.out.println("deleteTask");
        String username = "abc";
        int id = 1;
        TaskDAO instance = new TaskDAO();
        boolean result = instance.deleteTask(username, id);
        assertTrue(result);
        List<Task> tasks = instance.getTasks(username);
        assertEquals(tasks.size(), 1);
        assertEquals(tasks.get(0).getId().intValue(), 2);
    }

    /**
     * Test of getFinishedTasks method, of class TaskDAO.
     */
    @Test
    public void testGetFinishedTasksManager() {
        System.out.println("testGetFinishedTasksManager");
        String username = "ijk";
        TaskDAO instance = new TaskDAO();
        List<Task> result = instance.getFinishedTasks(username);
        assertEquals(result.size(), 2);
    }
    
    @Test
    public void testGetFinishedTasksNotManager() {
        System.out.println("testGetFinishedTasksNotManager");
        String username = "abc";
        TaskDAO instance = new TaskDAO();
        List<Task> result = instance.getFinishedTasks(username);
        assertEquals(result.size(), 1);
    }

    /**
     * Test of getUnfinishedTasks method, of class TaskDAO.
     */
    @Test
    public void testGetUnfinishedTasksManager() {
        System.out.println("testGetUnfinishedTasksManager");
        String username = "ijk";
        TaskDAO instance = new TaskDAO();
        List<Task> result = instance.getUnfinishedTasks(username);
        assertEquals(result.size(), 2);
    }
    
    /**
     * Test of getUnfinishedTasks method, of class TaskDAO.
     */
    @Test
    public void testGetUnfinishedTasksNotManager() {
        System.out.println("testGetUnfinishedTasksNotManager");
        String username = "def";
        TaskDAO instance = new TaskDAO();
        List<Task> result = instance.getUnfinishedTasks(username);
        assertEquals(result.size(), 1);
    }

    /**
     * Test of getTasksDueToday method, of class TaskDAO.
     */
    @Test
    public void testGetTasksDueTodayManager() {
        System.out.println("testGetTasksDueTodayManager");
        String username = "ijk";
        TaskDAO instance = new TaskDAO();
        List<Task> result = instance.getTasksDueToday(username);
        assertEquals(result.size(), 1);
    }
    
    @Test
    public void testGetTasksDueTodayNotManager() {
        System.out.println("testGetTasksDueTodayNotManager");
        String username = "abc";
        TaskDAO instance = new TaskDAO();
        List<Task> result = instance.getTasksDueToday(username);
        assertEquals(result.size(), 0);
    }

    /**
     * Test of getOverdueTasks method, of class TaskDAO.
     */
    @Test
    public void testGetOverdueTasksManager() {
        System.out.println("testGetOverdueTasksManager");
        String username = "ijk";
        TaskDAO instance = new TaskDAO();
        List<Task> result = instance.getOverdueTasks(username);
        assertEquals(result.size(), 1);
    }
    
    @Test
    public void testGetOverdueTasksNotManager() {
        System.out.println("testGetOverdueTasksNotManager");
        String username = "def";
        TaskDAO instance = new TaskDAO();
        List<Task> result = instance.getOverdueTasks(username);
        assertEquals(result.size(), 0);
    }

    /**
     * Test of getFinishedButNotYetApprovedTasks method, of class TaskDAO.
     */
    @Test
    public void testGetFinishedButNotYetApprovedTasks() {
        System.out.println("getFinishedButNotYetApprovedTasks");
        String username = "def";
        TaskDAO instance = new TaskDAO();
        List<Task> result = instance.getFinishedButNotYetApprovedTasks(username);
        assertEquals(result.size(), 1);
    }
    
    @Test
    public void testGetFinishedButNotYetApprovedTasks2() {
        System.out.println("getFinishedButNotYetApprovedTasks2");
        String username = "abc";
        TaskDAO instance = new TaskDAO();
        List<Task> result = instance.getFinishedButNotYetApprovedTasks(username);
        assertEquals(result.size(), 0);
    }
    
    @Test
    public void testGetFinishedButNotYetApprovedTasksManager() {
        System.out.println("getFinishedButNotYetApprovedTasksManager");
        String username = "ijk";
        TaskDAO instance = new TaskDAO();
        List<Task> result = instance.getFinishedButNotYetApprovedTasks(username);
        assertEquals(result.size(), 1);
    }
}
