package com.upwork.xyz.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.upwork.xyz.exception.UsernameAlreadyUsedException;
import com.upwork.xyz.model.Authority;
import com.upwork.xyz.model.Store;
import com.upwork.xyz.model.User;
import com.upwork.xyz.repository.AuthorityRepository;
import com.upwork.xyz.repository.StoreRpository;
import com.upwork.xyz.repository.UserRepository;
import com.upwork.xyz.security.AuthoritiesConstants;
import com.upwork.xyz.service.UserService;
import com.upwork.xyz.service.dto.UserDTO;
import com.upwork.xyz.utils.Constants;

@Service
public class UserServiceImpl implements UserService{
	
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    
    private final StoreRpository storeRpository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthorityRepository authorityRepository;
    
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthorityRepository authorityRepository,
            StoreRpository storeRpository
        ) {
            this.userRepository = userRepository;
            this.storeRpository = storeRpository;
            this.passwordEncoder = passwordEncoder;
            this.authorityRepository = authorityRepository;
        }
    
	public User registerUser(UserDTO userDTO) {
        userRepository
            .findByUsername(userDTO.getUsername().toLowerCase())
            .ifPresent(
                existingUser -> {
                    boolean removed = checkUser(existingUser);
                    if (!removed) {
                        throw new UsernameAlreadyUsedException(Constants._USER_ALREADY_EXISTS);
                    }
                }
            );

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        newUser.setPassword(encryptedPassword);
        newUser.setEnabled(true);
        Optional<Store> store=storeRpository.findById(userDTO.getStore_id());
        newUser.setStore(store.get());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.EMPLOYEE).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }
	
    private boolean checkUser(User existingUser) {
        if (!existingUser.equals(null)) {
            return false;
        }
        return true;
    }

	@Override
	public User getUser(String userName) {
		User user=userRepository.getUserByUsername(userName);
		return user;
	}

}
