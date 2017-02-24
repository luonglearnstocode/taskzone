/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lwown
 */

@XmlRootElement
@Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue
    private Long id; 
    private String text;
    private Boolean isDone;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @ManyToOne
    @JoinColumn(name="USERNAME")
    private User user;
//    private Map<Long, Comment> comments = new HashMap<>();
    
    public Task() {}

    public Task(String text, Date dueDate) {
        isDone = false;
        this.text = text;
        this.created = new Date();
        this.dueDate = dueDate;
        user = null;
    }
    
    @XmlElement
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @XmlElement
    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }
    
    @XmlElement
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
//    @XmlTransient
//    public Map<Long, Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(Map<Long, Comment> comments) {
//        this.comments = comments;
//    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    @XmlTransient
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void assignTask(User user) {
        this.user = user;
        this.user.getTasks().add(this);
    }
      
}
