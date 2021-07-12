package com.qualitychemicals.qciss.account.service.ServiceImpl;

import com.qualitychemicals.qciss.account.dao.UserAccountDao;
import com.qualitychemicals.qciss.account.model.UserAccount;
import com.qualitychemicals.qciss.account.service.UserAccountService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {
   @Autowired
   UserAccountDao userAccountDao;
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    public List<UserAccount> getMyAll() {
        String userName =myUserDetailsService.currentUser();
        return userAccountDao.findByUserName(userName);
    }

    @Override
    public boolean existsByRef(String ref) {
        return userAccountDao.existsByAccountRef(ref);
    }
}
