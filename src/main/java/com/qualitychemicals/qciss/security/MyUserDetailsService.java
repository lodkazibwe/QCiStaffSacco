package com.qualitychemicals.qciss.security;

import com.qualitychemicals.qciss.profile.dao.UserDao;
import com.qualitychemicals.qciss.profile.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user=userDao.findByUserName(userName);
        MyUserDetails userDetails=new MyUserDetails(user);
        if(user!=null){
            return userDetails;

        }else{
            throw new UsernameNotFoundException("User not exist with name : " + userName);
        }
    }
}
