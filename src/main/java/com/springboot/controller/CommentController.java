package com.springboot.controller;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.model.Comment;
import com.springboot.model.Projet;
import com.springboot.model.User;
import com.springboot.repository.CommentRepository;
import com.springboot.repository.ContributionRepository;
import com.springboot.repository.ProjetRepository;
import com.springboot.service.ContributionService;
import com.springboot.service.ProjetService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class CommentController {
	
	@Autowired

    CommentRepository commentRepository;
	@Autowired

    ProjetRepository projetRepository;
	
	@Autowired
    private ProjetService projetService;
	
	/*
	 * @GetMapping("/formcomment/{id}") public String formProjet(@PathVariable Long
	 * id, Model model, HttpServletRequest request) { Projet projet =
	 * projetService.get(id); HttpSession session= request.getSession(); long idd=
	 * Long.parseLong(session.getAttribute("userId").toString()); User user = new
	 * User(); user.setUserId(idd); if(projet != null) {
	 * 
	 * Comment comment = new Comment(); comment.setProjet(projet);
	 * comment.setUser(user); model.addAttribute("comment", comment);
	 * model.addAttribute("projet", projet); } model.addAttribute("success",
	 * model.containsAttribute("success")); return "viewproject"; }
	 */
	
	@PostMapping("/addcomment")
    public String addComment(@RequestParam ("projet") Long projet,@Valid Comment comment, BindingResult bindingResult,HttpServletRequest request){
		   HttpSession session= request.getSession(); 
		   long idd= Long.parseLong(session.getAttribute("userId").toString());
		   Projet currentprojet = projetService.get(projet);
		   User user = new User(); user.setUserId(idd);
            comment.setUser(user);
            comment.setProjet(currentprojet);
            if(bindingResult.hasErrors()) {
            return "redirect:/" + comment.getProjet().getId();
        } else {
            commentRepository.save(comment);
        }
        return "redirect:/projet/" + comment.getProjet().getId();
    }

}
