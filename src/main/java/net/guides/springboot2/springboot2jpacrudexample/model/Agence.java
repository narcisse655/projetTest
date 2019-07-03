/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.guides.springboot2.springboot2jpacrudexample.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nas
 */
@Entity
@Table(name = "agence")
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_agence")
    private Integer numAgence;
    @Size(max = 30)
    @Column(name = "nom_agence")
    private String nomAgence;
    @OneToMany(mappedBy = "numAgence")
    private List<Contrat> contratList;

    public Agence() {
    }

    public Agence(Integer numAgence) {
        this.numAgence = numAgence;
    }

    public Integer getNumAgence() {
        return numAgence;
    }

    public void setNumAgence(Integer numAgence) {
        this.numAgence = numAgence;
    }

    public String getNomAgence() {
        return nomAgence;
    }

    public void setNomAgence(String nomAgence) {
        this.nomAgence = nomAgence;
    }

    @XmlTransient
    public List<Contrat> getContratList() {
        return contratList;
    }

    public void setContratList(List<Contrat> contratList) {
        this.contratList = contratList;
    }

    
}
