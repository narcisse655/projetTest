/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.guides.springboot2.springboot2jpacrudexample.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Nas
 */
@Entity
@Table(name = "materiel")
public class Materiel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ref_materiel")
    private Integer refMateriel;
    @Size(max = 50)
    @Column(name = "design_materiel")
    private String designMateriel;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pu_materiel")
    private Double puMateriel;

    public Materiel() {
    }

    public Materiel(Integer refMateriel) {
        this.refMateriel = refMateriel;
    }

    public Integer getRefMateriel() {
        return refMateriel;
    }

    public void setRefMateriel(Integer refMateriel) {
        this.refMateriel = refMateriel;
    }

    public String getDesignMateriel() {
        return designMateriel;
    }

    public void setDesignMateriel(String designMateriel) {
        this.designMateriel = designMateriel;
    }

    public Double getPuMateriel() {
        return puMateriel;
    }

    public void setPuMateriel(Double puMateriel) {
        this.puMateriel = puMateriel;
    }

   
    
}
