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
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author lwown
 */
public class UserDAOTest {
    
    public UserDAOTest() {
    }
    
    @Before
    public void initialize() {
        Session session = HibernateStuff.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("delete from User");
        q.executeUpdate();
        User u1 = new User("abc");
        User u2 = new User("def");
        User u3 = new User("ijk");
        u3.setIsManager(true);
        session.saveOrUpdate(u1);
        session.saveOrUpdate(u2);
        session.saveOrUpdate(u3);
        session.getTransaction().commit();
    }

    /**
     * Test of getUsers method, of class UserDAO.
     */
    @Test
    public void testGetUsers() {
        System.out.println("getUsers");
        UserDAO instance = new UserDAO();
        List<User> result = instance.getUsers();
        assertEquals(result.size(), 3);
        assertEquals(result.get(0).getUserName(), "abc");
    }

    /**
     * Test of addUser method, of class UserDAO.
     */
    @Test
    public void testAddUser() {
        System.out.println("addUser");
        User newUser = new User("xyz");
        UserDAO instance = new UserDAO();
        instance.addUser(newUser);
        List<User> users = instance.getUsers();
        assertEquals(users.size(), 4);
        assertEquals(users.get(3).getUserName(), "xyz");
    }

    /**
     * Test of getUser method, of class UserDAO.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        String username = "ijk";
        UserDAO instance = new UserDAO();
        User result = instance.getUser(username);
        assertEquals(username, result.getUserName());
        assertTrue(result.isIsManager());
    }

    /**
     * Test of updateUser method, of class UserDAO.
     */
    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        String username = "abc";
        User newUser = new User(username);
        newUser.setFullName("new abc");
        newUser.setPassword("updatedQwerty");
        UserDAO instance = new UserDAO();
        User result = instance.updateUser(newUser, username);
        assertEquals(result.getFullName(), "new abc");
        assertEquals(result.getPassword(), "updatedQwerty");
    }

    /**
     * Test of deleteUser method, of class UserDAO.
     */
    @Test
    public void testDeleteUser() {
        System.out.println("deleteUser");
        String username = "abc";
        UserDAO instance = new UserDAO();
        boolean result = instance.deleteUser(username);
        assertTrue(result);
        List<User> users = instance.getUsers();
        assertEquals(users.size(), 2);
    }

    /**
     * Test of isUserAuthenticated method, of class UserDAO.
     */
    @Test
    public void testIsUserAuthenticatedAllRight() {
        System.out.println("testIsUserAuthenticatedAllRight");
        String username = "abc";
        String password = "qwerty";
        String path = ".../users/abc/tasks";
        UserDAO instance = new UserDAO();
        boolean result = instance.isUserAuthenticated(username, password, path);
        assertTrue(result);
    }
    
    @Test
    public void testIsUserAuthenticatedWrongUsername() {
        System.out.println("testIsUserAuthenticatedWrongUsername");
        String username = "abcd";
        String password = "qwerty";
        String path = ".../users/abc/tasks";
        UserDAO instance = new UserDAO();
        boolean result = instance.isUserAuthenticated(username, password, path);
        assertFalse(result);
    }
    
    @Test
    public void testIsUserAuthenticatedWrongPassword() {
        System.out.println("testIsUserAuthenticatedWrongPassword");
        String username = "abc";
        String password = "qwerty123";
        String path = ".../users/abc/tasks";
        UserDAO instance = new UserDAO();
        boolean result = instance.isUserAuthenticated(username, password, path);
        assertFalse(result);
    }
    
    @Test
    public void testIsUserAuthenticatedWrongPath() {
        System.out.println("testIsUserAuthenticatedWrongPath");
        String username = "abc";
        String password = "qwerty";
        String path = ".../users/def/tasks";
        UserDAO instance = new UserDAO();
        boolean result = instance.isUserAuthenticated(username, password, path);
        assertFalse(result);
    }
}
