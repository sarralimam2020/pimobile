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
public class Blog{
    private Integer id;
    private String image;
    public int repliesnumber;
    private Date dateCreation;
    private String title;
    private String categorie;
    private int likesnumber;
    private String content;

    public Blog()  {
    }

    public Blog(Integer id, String image, int repliesnumber, Date dateCreation, String title, String categorie, int likesnumber) {
        this.id = id;
        this.image = image;
        this.repliesnumber = repliesnumber;
        this.dateCreation = dateCreation;
        this.title = title;
        this.categorie = categorie;
        this.likesnumber = likesnumber;
    }
    
    

    public Blog(String image, int repliesnumber, Date dateCreation, String title, String categorie, int likesnumber, String content) {
        this.image = image;
        this.repliesnumber = repliesnumber;
        this.dateCreation = dateCreation;
        this.title = title;
        this.categorie = categorie;
        this.likesnumber = likesnumber;
        this.content = content;
    }
    

    public Blog(Integer id, String image, int repliesnumber, Date dateCreation, String title, String categorie, int likesnumber, String content) {
        this.id = id;
        this.image = image;
        this.repliesnumber = repliesnumber;
        this.dateCreation = dateCreation;
        this.title = title;
        this.categorie = categorie;
        this.likesnumber = likesnumber;
        this.content = content;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRepliesnumber() {
        return repliesnumber;
    }

    public void setRepliesnumber(int repliesnumber) {
        this.repliesnumber = repliesnumber;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getLikesnumber() {
        return likesnumber;
    }

    public void setLikesnumber(int likesnumber) {
        this.likesnumber = likesnumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
    
}