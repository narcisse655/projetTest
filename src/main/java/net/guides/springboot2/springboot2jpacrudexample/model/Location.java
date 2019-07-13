/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.guides.springboot2.springboot2jpacrudexample.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Nas
 */
@Entity
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="location_id")
    Long locationId;

    @Column(name = "qte_liv")
    private Double qteLiv;

    //@Id
    @ManyToOne
    @JoinColumn(name="num_contrat")
    private Contrat contrat;

    //@Id
    @ManyToOne
    @JoinColumn(name="ref_materiel")
    private Materiel materiel;
    
    
    public Location() {
    }

    public Location(Contrat contrat, Materiel materiel, double qteLiv) {
        this.contrat = contrat;
        this.materiel = materiel;
        this.qteLiv = qteLiv;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }


    public Double getQteLiv() {
        return qteLiv;
    }

    public void setQteLiv(Double qteLiv) {
        this.qteLiv = qteLiv;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }
    
}
