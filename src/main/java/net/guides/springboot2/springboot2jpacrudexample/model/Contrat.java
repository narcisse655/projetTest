/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.guides.springboot2.springboot2jpacrudexample.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nas
 */
@Entity
@Table(name = "contrat")
public class Contrat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_contrat")
    private Integer numContrat;
    @Column(name = "date_contrat")
    @Temporal(TemporalType.DATE)
    private Date dateContrat;
    @Column(name = "duree_contrat")
    private Integer dureeContrat;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant")
    private Double montant;
    @JoinColumn(name = "num_agence", referencedColumnName = "num_agence")
    @ManyToOne
    private Agence numAgence;
    @JoinColumn(name = "num_client", referencedColumnName = "num_client")
    @ManyToOne
    private Client numClient;

    public Contrat() {
    }

    public Contrat(Integer numContrat) {
        this.numContrat = numContrat;
    }

    public Integer getNumContrat() {
        return numContrat;
    }

    public void setNumContrat(Integer numContrat) {
        this.numContrat = numContrat;
    }

    public Date getDateContrat() {
        return dateContrat;
    }

    public void setDateContrat(Date dateContrat) {
        this.dateContrat = dateContrat;
    }

    public Integer getDureeContrat() {
        return dureeContrat;
    }

    public void setDureeContrat(Integer dureeContrat) {
        this.dureeContrat = dureeContrat;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Agence getNumAgence() {
        return numAgence;
    }

    public void setNumAgence(Agence numAgence) {
        this.numAgence = numAgence;
    }

    public Client getNumClient() {
        return numClient;
    }

    public void setNumClient(Client numClient) {
        this.numClient = numClient;
    }

    
}
