package com.qualitychemicals.qciss.security;

import com.qualitychemicals.qciss.profile.dao.UserDao;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Profile profile =userDao.findByUserName(userName);
        MyUserDetails userDetails=new MyUserDetails(profile);
        if(profile !=null){
            return userDetails;

        }else{
            throw new UsernameNotFoundException("Profile not exist with name : " + userName);
        }
    }

    public String currentUser (){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();

    }

    public Profile getUser(String userName){
        return userDao.findByUserName(userName);
    }

    public AuthResp getResponse(String userName){
        AuthResp authResponse= new AuthResp("","","");
        Profile profile =getUser(userName);
        Set<Role> roles =profile.getRole();

        if(roles.size()>1){
            authResponse.setRole("ROOT");
        }else{authResponse.setRole("ADMIN");}
        authResponse.setName(profile.getPerson().getFirstName()+" "+profile.getPerson().getLastName());
        return authResponse;
    }
}
