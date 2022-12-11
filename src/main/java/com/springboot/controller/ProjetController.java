package com.springboot.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.FileUploadUtil;
import com.springboot.model.Comment;
import com.springboot.model.Contribution;
import com.springboot.model.Projet;
import com.springboot.model.User;
import com.springboot.repository.ProjetRepository;
import com.springboot.repository.UserRepository;
import com.springboot.repository.VerificationTokenRepository;
import com.springboot.service.AuthService;
import com.springboot.service.ProjetService;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
@AllArgsConstructor
public class ProjetController {
	
    private static final Logger logger = LoggerFactory.getLogger(ProjetController.class);

	@Autowired
    private ProjetService projetService;
	
	
    @GetMapping("/formproject")
    public String formProjet(Model model, HttpServletRequest request) {
    	HttpSession session= request.getSession();
    	long id= Long.parseLong(session.getAttribute("userId").toString());
    	Projet projet = new Projet();
    	User user = new User();
    	user.setUserId(id);
    	projet.setUser(user);
    	
		model.addAttribute("projet",projet);
	//	model.addAttribute("user",userRepo.get);

		return "formproject";
	}
    @PostMapping("/saveProject")
    public String save(@Valid Projet projet, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, 
    		@RequestParam("image") MultipartFile multipartFile) throws IOException{
    	
    	if( bindingResult.hasErrors()){
            model.addAttribute("post", projet);
            return "formproject";
        } else {
       String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
       projet.setPhotos(fileName);
       
    	//System.out.println(projet.getUser().getUserId());
        Projet saveProjet = projetService.save(projet);
        String uploadDir="images/"+saveProjet.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
       

        return "redirect:/";
    }}
    @PostMapping("/save")
    public String editprojet(@Valid @ModelAttribute("projet") Projet projet, BindingResult result){
        if(result.hasErrors()){
            return "editproject";
        }
        projetService.save(projet);
        return "redirect:/UserProjets";
    }
    
    @GetMapping("/discoverprojects")
    public String list(Model model) {
        model.addAttribute("projets", projetService.getAll());
        return "discoverprojects";
    }
   
    
    @GetMapping("/projet/{id}")
    public String projet(@PathVariable Long id, Model model, HttpServletRequest request) {
        Projet projet = projetService.get(id);
		/*
		 * HttpSession session= request.getSession(); long idd=
		 * Long.parseLong(session.getAttribute("userId").toString()); User user = new
		 * User(); user.setUserId(idd);
		 */
			if(projet != null) {

        Comment comment = new Comment();
        comment.setProjet(projet);
           model.addAttribute("comment", comment);
            model.addAttribute("projet", projet);
}
            model.addAttribute("success", model.containsAttribute("success"));
            return "viewproject";
		
        
    }
   
    
    @GetMapping("/editprojet/{id}")
    public String formedit(@PathVariable Long id, Model model, HttpServletRequest request) {
        Projet projet = projetService.get(id);
		
		
            model.addAttribute("projet", projet);
            model.addAttribute("user", projet.getUser());

            model.addAttribute("success", model.containsAttribute("success"));
            return "editproject";

        
    }
    @PostMapping("/saveprojet/{id}")
    public String editprojet(@PathVariable Long id,
			@ModelAttribute("projet") Projet projet,
			Model model,BindingResult result){
    	
    	Projet existingprojet = projetService.get(id);
    	existingprojet.setId(id);
    	existingprojet.setAdresse(projet.getAdresse());
    	existingprojet.setNomProjet(projet.getNomProjet());
    	existingprojet.setDescriptionProjet(projet.getDescriptionProjet());
    	existingprojet.setNatureProjet(projet.getNatureProjet());
    	existingprojet.setDelaiProjet(projet.getDelaiProjet());
    	existingprojet.setDateDebut(projet.getDateDebut());
    	existingprojet.setFondsALever(projet.getFondsALever());
    	
        projetService.save(existingprojet);
        return "redirect:/UserProjets";
    }
    
    @GetMapping("/deleteprojet/{id}")
	public String deleteProjet(@PathVariable Long id) {
		projetService.delete(id);
		return "redirect:/UserProjets";
	}
    @GetMapping("/UserProjets")
    public String userprojet( Model model, HttpServletRequest request) {
		
		
		  HttpSession session= request.getSession(); 
		  long idd= Long.parseLong(session.getAttribute("userId").toString());
		  User user = new User(); 
		  user.setUserId(idd);
		  
		  
            model.addAttribute("projets", projetService.getAllByUser(user));
            model.addAttribute("success", model.containsAttribute("success"));
            return "userprojects";

        
    }
    
   
    
    
   

}
