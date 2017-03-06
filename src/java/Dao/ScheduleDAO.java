/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Schedule;
import Model.User;
import Util.HibernateStuff;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author lwown
 */
public class ScheduleDAO {
    public List<Schedule> getSchedules() {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from Schedule");
        List<Schedule> schedules = q.list();
        session.getTransaction().commit();
        return schedules;
    }

    public Schedule getScheduleWithId(long id) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from Schedule where id = " + id);
        Schedule schedule = (Schedule) q.uniqueResult();
        session.getTransaction().commit();
        return schedule;
    }
    
    public void addSchedule(Schedule newSchedule) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        session.save(newSchedule);
        session.getTransaction().commit();    
    }
    
    public Schedule updateSchedule(Schedule newSchedule, long id) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from Schedule where id = " + id);
        Schedule schedule = (Schedule) q.uniqueResult();
        schedule.setUsers(newSchedule.getUsers());
        schedule.setWorkDate(newSchedule.getWorkDate());
        for (User user : schedule.getUsers()) {
            user.getWorkSchedule().add(schedule);
            session.saveOrUpdate(user);
        }
        session.saveOrUpdate(schedule);
        session.getTransaction().commit();
        return schedule;
    }
    
    public boolean deleteSchedule(long id) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from Schedule where id = " + id);
        Schedule schedule = (Schedule) q.uniqueResult();
        session.delete(schedule);
        session.getTransaction().commit();
        return schedule != null;
    }
    
    public List<Schedule> getScheduleOfUser(String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from User where userName = :username");
        q.setParameter("username", username);
        User user = (User) q.uniqueResult();
        List<Schedule> schedules = user.getWorkSchedule();
        session.getTransaction().commit();
        return schedules;
    } 

    public void addUserToSchedule(long id, String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q1 = session.createQuery("from User where userName = :username");
        q1.setParameter("username", username);
        User user = (User) q1.uniqueResult();
        Query q2 = session.createQuery("from Schedule where id = " + id);
        Schedule schedule = (Schedule) q2.uniqueResult();
        user.getWorkSchedule().add(schedule);
        schedule.getUsers().add(user);
        session.saveOrUpdate(schedule);
        session.saveOrUpdate(user);
        session.getTransaction().commit();
    }
    
    public void deleteUserFromSchedule(long id, String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q1 = session.createQuery("from User where userName = :username");
        q1.setParameter("username", username);
        User user = (User) q1.uniqueResult();
        Query q2 = session.createQuery("from Schedule where id = " + id);
        Schedule schedule = (Schedule) q2.uniqueResult();
        user.getWorkSchedule().remove(schedule);
        schedule.getUsers().remove(user);
        session.saveOrUpdate(schedule);
        session.saveOrUpdate(user);
        session.getTransaction().commit();
    }
    
}
