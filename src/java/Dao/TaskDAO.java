/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Task;
import Model.User;
import Util.HibernateStuff;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author lwown
 */
public class TaskDAO {
    public List<Task> getTasks(String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        // check if the username is the manager
        Query q1 = session.createQuery("select isManager from User where userName = :username");
        q1.setParameter("username", username);
        Boolean isManager = (Boolean) q1.uniqueResult();
        Query q;
        // if is manager, query all tasks, otherwise get tasks of the employee 
        if (isManager) {
           q = session.createQuery("from Task");
        } else {
            q = session.createQuery("from Task where user.userName = :username");
            q.setParameter("username", username);
        }
        List<Task> tasks = q.list();
        
        session.getTransaction().commit();
        return tasks;
    }
    
    public Task addTask(String username, Task newTask) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from User where userName = :username");
        q.setParameter("username", username);
        User user = (User) q.uniqueResult();
        newTask.assignTask(user);
        session.save(newTask);
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        return newTask;
    }
    
    public Task getTaskWithId(long id) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from Task where Id = " + id);
        Task task = (Task) q.uniqueResult();
        
        session.getTransaction().commit();
        return task;
    }
    
    public Task updateTask(Task newTask, long id) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from Task where Id = " + id);
        Task task = (Task) q.uniqueResult();
        task.setDueDate(newTask.getDueDate());
        task.setIsDone(newTask.isIsDone());
        task.setText(newTask.getText());
        session.saveOrUpdate(task);
        
        session.getTransaction().commit();
        return task;
    }
    
    public boolean deleteTask(String username, int id) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q1 = session.createQuery("from User where userName = :username");
        q1.setParameter("username", username);
        User user = (User) q1.uniqueResult();
        Query q2 = session.createQuery("from Task where id = " + id);
        Task task = (Task) q2.uniqueResult();
        user.getTasks().remove(task);

        session.saveOrUpdate(user);
        session.delete(task);
        session.getTransaction().commit();
        return task != null;
    }
    
    public List<Task> getFinishedTask(String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        // check if the username is the manager
        Query q1 = session.createQuery("select isManager from User where userName = :username");
        q1.setParameter("username", username);
        Boolean isManager = (Boolean) q1.uniqueResult();
        Query q;
        // if is manager, query all tasks, otherwise get tasks of the employee 
        if (isManager) {
           q = session.createQuery("from Task where isDone = True");
        } else {
            q = session.createQuery("from Task where user.userName = :username and isDone = True");
            q.setParameter("username", username);
        }
        List<Task> tasks = q.list();
        
        session.getTransaction().commit();
        return tasks;
    }
    
    public List<Task> getUnfinishedTask(String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        // check if the username is the manager
        Query q1 = session.createQuery("select isManager from User where userName = :username");
        q1.setParameter("username", username);
        Boolean isManager = (Boolean) q1.uniqueResult();
        Query q;
        // if is manager, query all tasks, otherwise get tasks of the employee 
        if (isManager) {
           q = session.createQuery("from Task where isDone = False");
        } else {
            q = session.createQuery("from Task where user.userName = :username and isDone = False");
            q.setParameter("username", username);
        }
        List<Task> tasks = q.list();
        
        session.getTransaction().commit();
        return tasks;
    }
}
