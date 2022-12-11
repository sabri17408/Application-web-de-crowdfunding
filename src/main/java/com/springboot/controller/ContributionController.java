package com.springboot.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.model.Contribution;
import com.springboot.model.Projet;
import com.springboot.model.User;
import com.springboot.repository.ContributionRepository;
import com.springboot.service.ContributionService;
import com.springboot.service.ProjetService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor

public class ContributionController {
	
	@Autowired

    ContributionRepository contribRepository;
	@Autowired
    private ProjetService projetService;
	@Autowired
    private ContributionService contribService;
	
	 @GetMapping("/projet/contribution/{id}")
	    public String contribute(@PathVariable Long id, Model model, HttpServletRequest request) {
	        Projet projet = projetService.get(id);
	        HttpSession session= request.getSession();
	        long idd= Long.parseLong(session.getAttribute("userId").toString());
	    	User user = new User();
	    	user.setUserId(idd);

	            Contribution contribution = new Contribution();
	            contribution.setProjet(projet);
	            contribution.setUser(user);

	            model.addAttribute("contribution", contribution);
	            model.addAttribute("projet", projet);
	            model.addAttribute("user", user);

	            model.addAttribute("success", model.containsAttribute("success"));
	            return "contribute";
	    	
	        
	    }
	
	
	 @PostMapping("/saveContribution/{id}")
	    public String saveContribution(@PathVariable Long id,@Valid Contribution contribution, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws IOException{
	    	
	    	if( bindingResult.hasErrors()){
	            return "contribute";
	        } else {
	            contribService.save(contribution);



	        return "redirect:/projet/{id}";
	    }}
	 
	 @GetMapping("/UserContrib")
	    public String usercontrib( Model model, HttpServletRequest request) {
	        HttpSession session= request.getSession();
	        long idd= Long.parseLong(session.getAttribute("userId").toString());
	    	User user = new User();
	    	user.setUserId(idd);

            List<Contribution> contrib = contribService.getAllByUser(user);
	            model.addAttribute("contributions",contrib );
	            model.addAttribute("user", user);
	            model.addAttribute("success", model.containsAttribute("success"));
	            return "usercontrib";
	    	
	        
	    }
	


}
