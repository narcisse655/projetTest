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
@Table(name = "stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="stock_id")
    Long stockId;

    @Column(name = "qte_dispo")
    private Double qteDispo;

    //@Id
    @ManyToOne
    @JoinColumn(name="num_agence")
    private Agence agence;

    //@Id
    @ManyToOne
    @JoinColumn(name="ref_materiel")
    private Materiel materiel;

    public Stock() {
    }

    public Stock(Agence agence, Materiel materiel, double qteDispo) {
        this.agence = agence;
        this.materiel = materiel;
        this.qteDispo = qteDispo;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }


    public Double getQteDispo() {
        return qteDispo;
    }

    public void setQteDispo(Double qteDispo) {
        this.qteDispo = qteDispo;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    
}
