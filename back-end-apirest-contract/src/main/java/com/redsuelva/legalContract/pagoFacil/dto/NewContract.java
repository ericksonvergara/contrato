package com.redsuelva.legalContract.pagoFacil.dto;

import java.util.Date;

public class NewContract {

     private String dni;
     private String pathImgDniFront;
     private String pathImgDniLater;
     private String pathImgPeson;

     private String dateAccept;

     private Date dateExpedition;





     public NewContract(String dni, String pathImgDniFront, String pathImgDniLater, String pathImgPeson, String  dateAccept ) {
          this.dni = dni;
          this.pathImgDniFront = pathImgDniFront;
          this.pathImgDniLater = pathImgDniLater;
          this.pathImgPeson = pathImgPeson;
          this.dateAccept =dateAccept;
     }

     public String getDni() {
          return dni;
     }

     public void setDni(String dni) {
          this.dni = dni;
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

     public String getPathImgPeson() {
          return pathImgPeson;
     }

     public void setPathImgPeson(String pathImgPeson) {
          this.pathImgPeson = pathImgPeson;
     }


     public String getDateAccept() {
          return dateAccept;
     }

     public void setDateAccept(String dateAccept) {
          this.dateAccept = dateAccept;
     }

     @Override
     public String toString() {
          return "NewContract{" +
                  "dni='" + dni + '\'' +
                  ", pathImgDniFront='" + pathImgDniFront + '\'' +
                  ", pathImgDniLater='" + pathImgDniLater + '\'' +
                  ", pathImgPeson='" + pathImgPeson + '\'' +
                  ", dateAccept=" + dateAccept +
                  '}';
     }
}
