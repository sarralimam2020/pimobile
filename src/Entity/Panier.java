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

public class Panier {

    private Integer id;
    private Integer prix;
    private Integer quantite;
    private Integer produitId ;
    private Date dateP ;
    private Integer idUser;

    public Panier() {
    }

    public Panier(Integer id, Integer prix, Integer quantite, Integer produitId, Date dateP, Integer idUser) {
        this.id = id;
        this.prix = prix;
        this.quantite = quantite;
        this.produitId = produitId;
        this.dateP = dateP;
        this.idUser = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Integer getProduitId() {
        return produitId;
    }

    public void setProduitId(Integer produitId) {
        this.produitId = produitId;
    }

    public Date getDateP() {
        return dateP;
    }

    public void setDateP(Date dateP) {
        this.dateP = dateP;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    
    
    
}
