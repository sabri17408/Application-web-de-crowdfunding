package com.springboot.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contribution")

public class Contribution {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "montant_invistissement")
	    private Integer montantInvistissement;


	    @Column(name = "data_invistissement")
	    @Temporal(TemporalType.DATE) 
	    private Date dataInvistissement = new Date();

	
	    @ManyToOne
	    private Projet projet;
	    
	    @ManyToOne
	    private User user;
	    
	    
	    
	    

	   


	   

}
