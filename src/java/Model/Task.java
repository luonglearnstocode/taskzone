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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    private String title;
    private String text;
    private boolean isDone;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @ManyToOne
    @JoinColumn(name="USERNAME")
    private User user;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Feedback feedback;
//    private Map<Long, Comment> comments = new HashMap<>();
    
    public Task() {
        this("NA", new Date());
    }

    public Task(String text, Date dueDate) {
        isDone = false;
        this.title = "task title ";
        this.text = text;
        this.created = new Date();
        this.dueDate = dueDate;
        user = null;
        feedback = new Feedback("N/A");
    }
    
    @XmlElement
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @XmlElement
    public boolean isIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
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
    
    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
      
}
