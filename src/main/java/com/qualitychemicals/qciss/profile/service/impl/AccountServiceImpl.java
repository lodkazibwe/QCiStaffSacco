package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.profile.DAO.AccountDAO;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDAO accountDAO;

    @Override
    public Account addAccount(Account account) {
        return accountDAO.save(account);
    }

    @Override
    public Account getAccount(int id) {
        return accountDAO.findById(id).orElse(null);
    }

    @Override
    public List<Account> getByProfile(int id) {
        return null;
    }

    @Override
    public List<Account> getByCategory(String category) {
        return accountDAO.findByCategory(category);
    }

    @Override
    public List<Account> getAll() {
        return accountDAO.findAll();
    }

    @Override
    public Account updateAccount(Account account) {
        return null;
    }

    @Override
    public void deleteAccount(int id) {

    }
}
