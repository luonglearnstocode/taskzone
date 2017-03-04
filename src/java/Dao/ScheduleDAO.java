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
//        schedule.setUsers(newSchedule.getUsers());
        schedule.setWorkDate(newSchedule.getWorkDate());
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
    
}
