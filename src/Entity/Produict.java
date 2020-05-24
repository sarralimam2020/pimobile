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
public class Produict{
    private Integer id;
    private String imageId;
    public int quantity;
    public int prix;
    private Date date;
    private String nom;
    private String description;

    public Produict(Integer id, String imageId, int quantity, int prix, Date date, String nom, String description) {
        this.id = id;
        this.imageId = imageId;
        this.quantity = quantity;
        this.prix = prix;
        this.date = date;
        this.nom = nom;
        this.description = description;
    }

    public Produict() {
    }

    public Produict(String imageId, int quantity, int prix, Date date, String nom, String description) {
        this.imageId = imageId;
        this.quantity = quantity;
        this.prix = prix;
        this.date = date;
        this.nom = nom;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    
}
