package com.upwork.xyz.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.upwork.xyz.model.Authority;
import com.upwork.xyz.model.User;
import com.upwork.xyz.repository.AuthorityRepository;
import com.upwork.xyz.repository.UserRepository;
import com.upwork.xyz.security.AuthoritiesConstants;
import com.upwork.xyz.service.UserService;
import com.upwork.xyz.service.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService{
	
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthorityRepository authorityRepository;
    
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthorityRepository authorityRepository
        ) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
            this.authorityRepository = authorityRepository;
        }
    
	public User registerUser(UserDTO userDTO, String password) {
		System.out.println(userDTO);
        userRepository
            .findByEmail(userDTO.getUsername().toLowerCase())
            .ifPresent(
                existingUser -> {
                    boolean removed = checkUser(existingUser);
                    if (!removed) {
                      //  throw new UsernameAlreadyUsedException();
                    }
                }
            );

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encryptedPassword);
        newUser.setEnabled(true);
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }
	
    private boolean checkUser(User existingUser) {
        if (existingUser.equals(null)) {
            return false;
        }
        return true;
    }

}
