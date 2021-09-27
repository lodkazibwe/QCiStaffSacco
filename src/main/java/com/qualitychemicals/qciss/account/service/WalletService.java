package com.qualitychemicals.qciss.account.service;

import com.qualitychemicals.qciss.account.dto.WalletDto;
import com.qualitychemicals.qciss.account.model.UserAccount;
import com.qualitychemicals.qciss.account.model.Wallet;
import com.qualitychemicals.qciss.transaction.dto.TransactionType;

import java.util.List;

public interface WalletService {
    Wallet addWallet(WalletDto walletDto);
    Wallet transact(UserAccount userAccount);
    Wallet getWallet(String accountRef);
    Wallet getMyWallet();
    String deposit(double amount);
    Wallet refresh();
    List<Wallet> getAll();
    List<Wallet> getByAmountLess(double balance);
    List<Wallet> getByAmountGreater(double balance);
    Wallet updateWallet(WalletDto walletDto);
    Wallet  mobileWithdraw();
    Wallet  bankWithdraw();
    Wallet  eftWithdraw();
    Wallet  chequeWithdraw();




}
