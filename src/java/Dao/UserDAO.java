/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.User;
import Util.HibernateStuff;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author lwown
 */
public class UserDAO {
    public List<User> getUsers() {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from User");
        List<User> users = q.list();
        session.getTransaction().commit();
        return users;
    }
    
    public void addUser(User newUser){
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        session.save(newUser);
        session.getTransaction().commit();      
    }
    
    public User getUser(String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from User where userName = :username");
        q.setParameter("username", username);
        User user = (User) q.uniqueResult();
        session.getTransaction().commit();
        return user;
    }
    
    public User updateUser(User newUser, String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from User where userName = :username");
        q.setParameter("username", username);
        User user = (User) q.uniqueResult();
        user.setFullName(newUser.getFullName());
        user.setPassword(newUser.getPassword());
        user.setIsManager(newUser.isIsManager());
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        return user;
    }
    
    public boolean deleteUser(String username) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();        
        Query q = session.createQuery("from User where userName = :username");
        q.setParameter("username", username);
        User user = (User) q.uniqueResult();
        session.delete(user);
        session.getTransaction().commit();
        return user != null;
    }    
    
    public boolean isUserAuthenticated(String username, String password, String path) {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();        
        Query q = session.createQuery("from User where userName = :username");
        q.setParameter("username", username);
        User user = (User) q.uniqueResult();
//        boolean isUser = false;
        boolean result = false;
        if (user != null) {
            boolean isUser = user.getPassword().equals(password);
            if (isUser) {
                if (!user.isIsManager()) {
                    if (path.contains("users/"+username)) {
                        result = true;
                    }
                } else {
                    result = true;
                }
            }
        }
        session.getTransaction().commit();
        return result;
    }
}
