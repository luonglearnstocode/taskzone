/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
    private Date dueDate;
    
    public Task() {}

    public Task(String text, Date dueDate) {
        isDone = false;
        this.text = text;
        this.dueDate = dueDate;
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
    
      
}
