/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.guides.springboot2.springboot2jpacrudexample.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 *
 * @author Nas
 */
@Entity
@Table(name = "users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_users")
    private Integer idUsers;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "username", unique = true)
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "password")
    private String password;
    
    /* @ManyToMany(mappedBy = "usersList", fetch = FetchType.EAGER)//EAGER veut dire que chaque fois que tu vas charger un users charge aussi ses roles
    private List<Role> roleList = new ArrayList<>(); */
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roleList = new ArrayList<>();


    public Users() {
    }

    public Users(Integer idUsers) {
        this.idUsers = idUsers;
    }

    public Users(Integer idUsers, String username, String password) {
        this.idUsers = idUsers;
        this.username = username;
        this.password = password;
    }

    public Users(Integer idUsers, String username,String password, List<Role> roleList) {
        this.idUsers = idUsers;
        this.username = username;
        this.password = password;
        this.roleList = roleList;
    }

    public Integer getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Integer idUsers) {
        this.idUsers = idUsers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonSetter
    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
    
}
