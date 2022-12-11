package com.springboot.service;

import com.springboot.model.MyUserPrincipal;
import com.springboot.model.User;
import com.springboot.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static java.util.Collections.singletonList;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
    private final UserRepository userRepository;
    HttpServletRequest request;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
       User user = userRepository.findByUsername(username);
       
       if(user==null || !user.isEnabled())
        throw new UsernameNotFoundException("No user " +
                        "Found with username : " + username);
	/*
	 * List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	 * 
	 * authorities.add(new SimpleGrantedAuthority("user"));
	 */
       HttpSession hs = request.getSession();
       hs.setAttribute("userId", user.getUserId() );
     return new MyUserPrincipal(user);
        
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
