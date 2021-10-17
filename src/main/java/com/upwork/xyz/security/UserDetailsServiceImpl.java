package com.upwork.xyz.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import com.upwork.xyz.model.User;
import com.upwork.xyz.repository.UserRepository;

/**
 * 
 * @author ali
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {    	
    	System.out.println(username);
        User user = userRepository.getUserByUsername(username);
        System.out.println(user);
         
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new MyUserDetails(user);
    }
 
}