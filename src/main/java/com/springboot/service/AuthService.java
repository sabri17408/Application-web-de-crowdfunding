package com.springboot.service;


import com.springboot.model.NotificationEmail;
import com.springboot.model.User;
import com.springboot.model.VerificationToken;
import com.springboot.repository.UserRepository;
import com.springboot.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
	@Autowired
    private  PasswordEncoder passwordEncoder;
	@Autowired
    private  UserRepository userRepository;
	@Autowired
    private  VerificationTokenRepository verificationTokenRepository;
	@Autowired
    private  MailService mailService;
	
    //private final JwtProvider jwtProvider;

    public void signup(User user) {
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail(" Activate your Account",
                user.getEmail(), "Merci pour votre inscription à notre plateforme FundMe," +
                "Veuillez cliquer sur le lien suivant pour activer votre compte : " +
                "http://localhost:8090/accountVerification?token=" + token));
    }

	/*
	 * @Transactional(readOnly = true) public User getCurrentUser() { Jwt principal
	 * = (Jwt) SecurityContextHolder.
	 * getContext().getAuthentication().getPrincipal(); return
	 * userRepository.findByUsername(principal.getSubject()) .orElseThrow(() -> new
	 * UsernameNotFoundException("User name not found - " +
	 * principal.getSubject())); }
	 * 
	 * private void fetchUserAndEnable(VerificationToken verificationToken) { String
	 * username = verificationToken.getUser().getUsername(); User user =
	 * userRepository.findByUsername(username).orElseThrow(() -> new
	 * SpringRedditException("User not found with name - " + username));
	 * user.setEnabled(true); userRepository.save(user); }
	 */

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setCreatedDate(new Date());

        verificationTokenRepository.save(verificationToken);
        return token;
    }

	/*
	 * public void verifyAccount(String token) { Optional<VerificationToken>
	 * verificationToken = verificationTokenRepository.findByToken(token);
	 * fetchUserAndEnable(verificationToken.orElseThrow(() -> new
	 * SpringRedditException("Invalid Token"))); }
	 * 
	 * public AuthenticationResponse login(LoginRequest loginRequest) {
	 * Authentication authenticate = authenticationManager.authenticate(new
	 * UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
	 * loginRequest.getPassword()));
	 * SecurityContextHolder.getContext().setAuthentication(authenticate); String
	 * token = jwtProvider.generateToken(authenticate); return
	 * AuthenticationResponse.builder() .authenticationToken(token)
	 * .refreshToken(refreshTokenService.generateRefreshToken().getToken())
	 * .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
	 * .username(loginRequest.getUsername()) .build(); }
	 * 
	 * public AuthenticationResponse refreshToken(RefreshTokenRequest
	 * refreshTokenRequest) {
	 * refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken(
	 * )); String token =
	 * jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
	 * return AuthenticationResponse.builder() .authenticationToken(token)
	 * .refreshToken(refreshTokenRequest.getRefreshToken())
	 * .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
	 * .username(refreshTokenRequest.getUsername()) .build(); }
	 * 
	 * public boolean isLoggedIn() { Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); return
	 * !(authentication instanceof AnonymousAuthenticationToken) &&
	 * authentication.isAuthenticated(); }
	 */
}