package com.redsuelva.legalContract.pagoFacil.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Contract {

    @Id
    @Column(nullable = false, updatable = false)
    private String dni;

    private String contract;
    private String names;
    private String surNames;
    private String phone;
    private String pathImgDniFront;
    private String pathImgDniLater;
    private String pathImgPerson;
    private boolean isAccept;
    private String dateAccept;

    private Date dateCheck;

    private String expeditionDate;

    public Contract() {
        this.isAccept = false;
    }

    public Contract(String dni, String contract, String names, String surNames, String phone, String pathImgDniFront, String pathImgDniLater, String pathImgPerson, String dateAccept, String  expeditionDate) {
        this.dni = dni;
        this.names = names;
        this.surNames = surNames;
        this.phone = phone;
        this.pathImgDniFront = pathImgDniFront;
        this.pathImgDniLater = pathImgDniLater;
        this.pathImgPerson = pathImgPerson;
        this.contract = contract;
        this.dateAccept = dateAccept;
        this.isAccept = false;
        this.dateCheck=null;
        this.expeditionDate= expeditionDate;


    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurNames() {
        return surNames;
    }

    public void setSurNames(String surNames) {
        this.surNames = surNames;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPathImgDniFront() {
        return pathImgDniFront;
    }

    public void setPathImgDniFront(String pathImgDniFront) {
        this.pathImgDniFront = pathImgDniFront;
    }

    public String getPathImgDniLater() {
        return pathImgDniLater;
    }

    public void setPathImgDniLater(String pathImgDniLater) {
        this.pathImgDniLater = pathImgDniLater;
    }

    public String getPathImgPerson() {
        return pathImgPerson;
    }

    public void setPathImgPerson(String pathImgPerson) {
        this.pathImgPerson = pathImgPerson;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }


    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getDateAccept() {
        return dateAccept;
    }

    public void setDateAccept(String dateAccept) {
        this.dateAccept = dateAccept;
    }

    public Date getDateCheck() {
        return dateCheck;
    }

    public void setDateCheck(Date dateCheck) {
        this.dateCheck = dateCheck;
    }

    public String getExpeditionDate() {
        return expeditionDate;
    }

    public void setExpeditionDate(String expeditionDate) {
        this.expeditionDate = expeditionDate;
    }
}


