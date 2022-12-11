package com.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.model.Contribution;
import com.springboot.model.Projet;
import com.springboot.model.User;
import com.springboot.repository.ContributionRepository;
import com.springboot.repository.ProjetRepository;

@Service

public class ContributionService {
	
	@Autowired
    private ContributionRepository contribRepo;


 public ContributionService(ContributionRepository contribRepo) {
        this.contribRepo = contribRepo;
    }
    public List<Contribution> getAll(){
        return contribRepo.findAll();
    }
    public List<Contribution> getAllByUser(User user){
    	
        List<Contribution> c = contribRepo.findAllByUser(user);
        return c;
    }
    public  Contribution save(Contribution contrib){
        return contribRepo.save(contrib);
    }


    public Contribution get(long id) {
        return contribRepo.findById(id).get();
    }

    public void delete(long id) {
    	contribRepo.deleteById(id);
    }

}
