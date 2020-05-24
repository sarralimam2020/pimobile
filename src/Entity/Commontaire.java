/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;


/**
 *
 * @author Nacef
 */
public class Commontaire{
    private int id;
    private int user;
    public int blog;
    private Date publishdate;
    private String content;

    public Commontaire() {
    }

    @Override
    public String toString() {
        return "Commontaire{" + "id=" + id + ", user=" + user + ", blog=" + blog + ", publishdate=" + publishdate + ", content=" + content + '}';
    }

    public Commontaire(int id, int user, int blog, Date publishdate, String content) {
        this.id = id;
        this.user = user;
        this.blog = blog;
        this.publishdate = publishdate;
        this.content = content;
    }

    public Commontaire(int user, int blog, Date publishdate, String content) {
        this.user = user;
        this.blog = blog;
        this.publishdate = publishdate;
        this.content = content;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getBlog() {
        return blog;
    }

    public void setBlog(int blog) {
        this.blog = blog;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
    
}
