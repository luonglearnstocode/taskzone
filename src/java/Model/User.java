/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author lwown
 */
@XmlRootElement
@Table(name = "USER_DETAILS")
@Entity
public class User implements Serializable {
    @Id
    private String userName;
    private String fullName;
    private String password;
    private boolean isManager;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();
    @ManyToMany
    private List<Schedule> workSchedule = new ArrayList<Schedule>();
    
    public User() {}
    
    public User(String userName) {
        this.userName = userName;
        this.fullName = userName;
        this.isManager = false;
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
    
//    @XmlElementWrapper
//    @XmlElement(name="task")
    @XmlTransient
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }
    
    @XmlTransient
    public List<Schedule> getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(List<Schedule> workSchedule) {
        this.workSchedule = workSchedule;
    }
    
    
    
}
