package com.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.model.Projet;
import com.springboot.model.User;
import com.springboot.repository.ProjetRepository;
import com.springboot.repository.UserRepository;

@Service
public class ProjetService {
	
	
	 @Autowired
	    private ProjetRepository projetRepo;


	 public ProjetService(ProjetRepository projetrepo) {
	        this.projetRepo = projetrepo;
	    }
	    public List<Projet> getAll(){
	        return projetRepo.findAll();
	    }
	    public List<Projet> getAllByUser(User user){
	    	
	        List<Projet> projets = projetRepo.findAllByUser(user);
	        return projets;
	    }

	    public  Projet save(Projet projet){
	        return projetRepo.save(projet);
	    }


	    public Projet get(long id) {
	        return projetRepo.findById(id).get();
	    }

	    public void delete(long id) {
	        projetRepo.deleteById(id);
	    }

}
