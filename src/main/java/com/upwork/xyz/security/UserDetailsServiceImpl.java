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
    	
    	System.out.println("111111111111111111111111111");
    	System.out.println(username);
        User user = userRepository.getUserByUsername(username);
        System.out.println("22222222222222222222222222222222222");
        System.out.println(user);
         
        if (user == null) {
        	System.out.println("333333333333333333333333333333333");
            throw new UsernameNotFoundException("Could not find user");
        }
        System.out.println("444444444444444444444444444444444");
        return new MyUserDetails(user);
    }
 
}