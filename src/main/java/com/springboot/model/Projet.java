package com.springboot.model;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projet")

public class Projet {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_projet")
    private String nomProjet;

    @Column(name = "description_projet")
    private String descriptionProjet;

    @Column(name = "delai_projet")
    private String delaiProjet;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_debut")
    private Date dateDebut;

    @Column(name = "nature_projet")
    private String natureProjet;

    @Column(name = "adresse")
    private String adresse;

  

    

    @Column(name = "fonds_a_lever")
    private Double fondsALever;
    
    @OneToMany(mappedBy = "projet",cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
    private List<Contribution> contributions;

    @OneToOne
   @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    
 // Comments. Mapping: One to Many. Post -> Comments
    @OneToMany(mappedBy = "projet",cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }
    
    @Column(nullable = true,length=64 )
    private String photos;
    
    @Transient
    public String getPhotosImagePath() {
    	if(photos==null) return null;
    	return"/images/"+id+"/"+photos;
    }
    @Transient
    public String setPhotosImagePath() {
    	if(photos==null) return null;
    	return"/images/"+id+"/"+photos;
    }
    @Transient
    private double pourcentage;

    @Transient
    private double montantInvestissemnt;

    public double getMontantInvestissemnt() {
        double montant = this.getMontantContrib(this.id);
        return   montant;
    }

    public double getPourcentage() {
        double montant = this.getMontantContrib(this.id);
        return ( montant ) / ( this.fondsALever)* 100;
    }

    private double getMontantContrib(Long id){
        double montant = 0;
        for (Contribution c: this.contributions){
            montant += c.getMontantInvistissement();
        }
        return montant;
    }






	
}
