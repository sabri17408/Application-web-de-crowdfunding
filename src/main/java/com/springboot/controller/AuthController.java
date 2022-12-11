package com.springboot.controller;


import com.springboot.model.User;
import com.springboot.model.VerificationToken;
import com.springboot.repository.UserRepository;
import com.springboot.repository.VerificationTokenRepository;
import com.springboot.service.AuthService;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@Controller
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenrRepository;

    
   
    
    @GetMapping("/register")
    public String register(Model model){
    	model.addAttribute("user", new User());
    	return "register";
    }

    @PostMapping("/register")
    public ModelAndView saveUser(ModelAndView modelAndView, User user){
    
      User existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser != null){
            modelAndView.addObject("message", "This email already exists!");
            modelAndView.setViewName("error");
        }else{
            authService.signup(user);
            modelAndView.addObject("emailId",user.getEmail());
            modelAndView.setViewName("successfulRegistration");
        }
        return modelAndView;
    }

    @GetMapping("/accountVerification")
    public ModelAndView confirmAccount(ModelAndView modelAndView, @RequestParam("token") String confirmToken){
        VerificationToken token = verificationTokenrRepository.findByToken(confirmToken);
        System.out.println(confirmToken);
        if(token != null){
            User user = userRepository.findByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            verificationTokenrRepository.delete(token);
            modelAndView.setViewName("accountVerified");
        }else{
            modelAndView.addObject("message", "The link is invalid or broken");
            modelAndView.setViewName("error");
        }
            return modelAndView;
    }

    @GetMapping("/login")
    public String login(){
    	return "login";
    }
    @PostMapping("/login")
    public String connect(){
        

    	return "index";
    }
	/*
	 * @GetMapping("/") public String index(){ return "index"; }
	 */
    @GetMapping("/index")
    public String index(){
    	return "index";
    }
   
   
}
