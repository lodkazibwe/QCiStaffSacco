package com.qualitychemicals.qciss.account.service;

import com.qualitychemicals.qciss.account.model.UserAccount;

import java.util.List;

public interface UserAccountService {
    List<UserAccount> getMyAll();
    boolean existsByRef(String ref);
}
