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
public class Contract{
    private Integer id;
    private Date date_debut_location;
    private Date date_fin_location;
    private Date Date;

    public Contract(Integer id, Date date_debut_location, Date date_fin_location, Date Date) {
        this.id = id;
        this.date_debut_location = date_debut_location;
        this.date_fin_location = date_fin_location;
        this.Date = Date;
    }

    public Contract(Date date_debut_location, Date date_fin_location, Date Date) {
        this.date_debut_location = date_debut_location;
        this.date_fin_location = date_fin_location;
        this.Date = Date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate_debut_location() {
        return date_debut_location;
    }

    public void setDate_debut_location(Date date_debut_location) {
        this.date_debut_location = date_debut_location;
    }

    public Date getDate_fin_location() {
        return date_fin_location;
    }

    public void setDate_fin_location(Date date_fin_location) {
        this.date_fin_location = date_fin_location;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public Contract() {
    }




    
}
