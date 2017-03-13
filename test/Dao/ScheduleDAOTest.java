/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Schedule;
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
public class ScheduleDAOTest {
    
    public ScheduleDAOTest() {
    }
    
    @Before
    public void initialize() throws ParseException {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();

        session.createSQLQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        session.createSQLQuery("truncate table USER_DETAILS_Schedule").executeUpdate();
        session.createSQLQuery("truncate table Schedule").executeUpdate();
        session.createSQLQuery("truncate table USER_DETAILS").executeUpdate();
        
        User u1 = new User("abc");
        User u2 = new User("def");
        User u3 = new User("ijk");
        u3.setIsManager(true);
        
        Schedule s1 = new Schedule();
        Schedule s2 = new Schedule();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2017-03-12");
        Date date2 = sdf.parse("2017-03-13");
        
        s1.setWorkDate(date1);
        s2.setWorkDate(date2);
        
        s1.getUsers().add(u1);
        s1.getUsers().add(u2);
        s2.getUsers().add(u3);
        u1.getWorkSchedule().add(s1);
        u2.getWorkSchedule().add(s1);
        u3.getWorkSchedule().add(s2);

        session.saveOrUpdate(u1);
        session.saveOrUpdate(u2);
        session.saveOrUpdate(u3);
        session.saveOrUpdate(s1);
        session.saveOrUpdate(s2);
        session.getTransaction().commit();
    }

    /**
     * Test of getSchedules method, of class ScheduleDAO.
     */
    @Test
    public void testGetSchedules() {
        System.out.println("getSchedules");
        ScheduleDAO instance = new ScheduleDAO();
        List<Schedule> result = instance.getSchedules();
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getId().intValue(), 1);
    }

    /**
     * Test of getScheduleWithId method, of class ScheduleDAO.
     */
    @Test
    public void testGetScheduleWithId() {
        System.out.println("getScheduleWithId");
        long id = 1;
        ScheduleDAO instance = new ScheduleDAO();
        Schedule result = instance.getScheduleWithId(id);
        assertEquals(result.getUsers().size(), 2);
    }
    
    @Test
    public void testGetScheduleWithId2() {
        System.out.println("getScheduleWithId2");
        long id = 2;
        ScheduleDAO instance = new ScheduleDAO();
        Schedule result = instance.getScheduleWithId(id);
        assertEquals(result.getUsers().size(), 1);
    }

    /**
     * Test of addSchedule method, of class ScheduleDAO.
     */
    @Test
    public void testAddSchedule() {
        System.out.println("addSchedule");
        Schedule newSchedule = new Schedule();
        ScheduleDAO instance = new ScheduleDAO();
        instance.addSchedule(newSchedule);
        List<Schedule> result = instance.getSchedules();
        assertEquals(result.size(), 3);
    }

    /**
     * Test of updateSchedule method, of class ScheduleDAO.
     */
    @Test
    public void testUpdateSchedule() throws ParseException {
        System.out.println("updateSchedule");
        Schedule newSchedule = new Schedule();
        long id = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date2 = sdf.parse("2017-03-13 00:00:00");
        newSchedule.setWorkDate(date2);
        ScheduleDAO instance = new ScheduleDAO();
        Schedule result = instance.updateSchedule(newSchedule, id);
        assertEquals(result.getWorkDate(), date2);
        assertEquals(result.getUsers().size(), 0);
    }
    
    @Test
    public void testUpdateSchedule2() throws ParseException {
        System.out.println("updateSchedule2");
        Schedule newSchedule = new Schedule();
        long id = 1;
        newSchedule.getUsers().add(new User("xyz"));
        ScheduleDAO instance = new ScheduleDAO();
        Schedule result = instance.updateSchedule(newSchedule, id);
        assertEquals(result.getUsers().size(), 1);
    }

    /**
     * Test of deleteSchedule method, of class ScheduleDAO.
     */
    @Test
    public void testDeleteSchedule() {
        System.out.println("deleteSchedule");
        long id = 1;
        ScheduleDAO instance = new ScheduleDAO();
        boolean result = instance.deleteSchedule(id);
        assertTrue(result);
        List<Schedule> schedules = instance.getSchedules();
        assertEquals(schedules.size(), 1);
    }

    /**
     * Test of getScheduleOfUser method, of class ScheduleDAO.
     */
    @Test
    public void testGetScheduleOfUser() {
        System.out.println("getScheduleOfUser");
        String username = "abc";
        ScheduleDAO instance = new ScheduleDAO();
        List<Schedule> result = instance.getScheduleOfUser(username);
        assertEquals(result.size(), 1);
    }
    
    @Test
    public void testGetScheduleOfUser2() {
        System.out.println("getScheduleOfUser2");
        String username = "ijk";
        ScheduleDAO instance = new ScheduleDAO();
        List<Schedule> result = instance.getScheduleOfUser(username);
        assertEquals(result.size(), 1);
    }

    /**
     * Test of addUserToSchedule method, of class ScheduleDAO.
     */
    @Test
    public void testAddUserToSchedule1() {
        System.out.println("addUserToSchedule1");
        long id = 1;
        String username = "ijk";
        ScheduleDAO instance = new ScheduleDAO();
        instance.addUserToSchedule(id, username);
        Schedule result = instance.getScheduleWithId(id);
        assertEquals(result.getUsers().size(), 3);
    }
    
    @Test
    public void testAddUserToSchedule2() {
        System.out.println("addUserToSchedule2");
        long id = 2;
        String username = "abc";
        ScheduleDAO instance = new ScheduleDAO();
        instance.addUserToSchedule(id, username);
        Schedule result = instance.getScheduleWithId(id);
        assertEquals(result.getUsers().size(), 2);
    }
    
    @Test
    public void testAddUserToSchedule3() {
        System.out.println("addUserToSchedule3");
        long id = 2;
        String username = "def";
        ScheduleDAO instance = new ScheduleDAO();
        instance.addUserToSchedule(id, username);
        Schedule result = instance.getScheduleWithId(id);
        assertEquals(result.getUsers().size(), 2);
    }

    /**
     * Test of deleteUserFromSchedule method, of class ScheduleDAO.
     */
    @Test
    public void testDeleteUserFromSchedule() {
        System.out.println("deleteUserFromSchedule");
        long id = 1;
        String username = "abc";
        ScheduleDAO instance = new ScheduleDAO();
        instance.deleteUserFromSchedule(id, username);
        Schedule result = instance.getScheduleWithId(id);
        assertEquals(result.getUsers().size(), 1);
    }
    
    @Test
    public void testDeleteUserFromSchedule2() {
        System.out.println("deleteUserFromSchedule2");
        long id = 1;
        String username = "def";
        ScheduleDAO instance = new ScheduleDAO();
        instance.deleteUserFromSchedule(id, username);
        Schedule result = instance.getScheduleWithId(id);
        assertEquals(result.getUsers().size(), 1);
    }
    
    @Test
    public void testDeleteUserFromSchedule3() {
        System.out.println("deleteUserFromSchedule3");
        long id = 2;
        String username = "ijk";
        ScheduleDAO instance = new ScheduleDAO();
        instance.deleteUserFromSchedule(id, username);
        Schedule result = instance.getScheduleWithId(id);
        assertEquals(result.getUsers().size(), 0);
    }
}
