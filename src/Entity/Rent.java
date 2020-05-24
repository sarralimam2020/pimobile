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
public class Rent{
    private Integer id;
    private Integer  owner;
    private String matricule;
    public double dailyPrice;
    public String puissance;
    private String marque;
    private String model;
    private String category;
    private String imageId;

    public Rent(Integer id, Integer owner, String matricule, double dailyPrice, String puissance, String marque, String model, String category, String imageId) {
        this.id = id;
        this.owner = owner;
        this.matricule = matricule;
        this.dailyPrice = dailyPrice;
        this.puissance = puissance;
        this.marque = marque;
        this.model = model;
        this.category = category;
        this.imageId = imageId;
    }

    
    
    
    
    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Rent() {
    }

    public Rent(Integer id, Integer owner, String matricule, double dailyPrice, String puissance, String marque, String model, String category) {
        this.id = id;
        this.owner = owner;
        this.matricule = matricule;
        this.dailyPrice = dailyPrice;
        this.puissance = puissance;
        this.marque = marque;
        this.model = model;
        this.category = category;
    }

    public Rent(String matricule, double dailyPrice, String puissance, String marque, String model) {
        this.matricule = matricule;
        this.dailyPrice = dailyPrice;
        this.puissance = puissance;
        this.marque = marque;
        this.model = model;
    }


    
    
    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public String getPuissance() {
        return puissance;
    }

    public void setPuissance(String puissance) {
        this.puissance = puissance;
    }



    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
    
    



    
}
