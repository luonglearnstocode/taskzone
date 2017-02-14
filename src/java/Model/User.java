/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lwown
 */
@XmlRootElement
@Entity
public class User implements Serializable {
    @Id
    private String userName;
    private String fullName;
    private String password;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();
    
    public User() {}
    
    public User(String userName) {
        this.userName = userName;
        this.fullName = userName;
        password = "qwerty";
    }
    
    @XmlElement
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @XmlElement
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @XmlElementWrapper
    @XmlElement(name="task")
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    
}
