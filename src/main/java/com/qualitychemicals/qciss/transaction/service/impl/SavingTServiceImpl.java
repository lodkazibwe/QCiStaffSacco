package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.account.model.SavingsAccount;
import com.qualitychemicals.qciss.account.model.UserAccount;
import com.qualitychemicals.qciss.account.model.Wallet;
import com.qualitychemicals.qciss.account.service.SavingsAccountService;
import com.qualitychemicals.qciss.account.service.WalletService;
import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.saccoData.service.SavingService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import com.qualitychemicals.qciss.transaction.dto.*;
import com.qualitychemicals.qciss.transaction.service.SavingTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


@Service
public class SavingTServiceImpl implements SavingTService {
    @Autowired UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @Autowired RestTemplate restTemplate;
    @Autowired MyUserDetailsService myUserDetailsService;
    @Autowired WalletService walletService;
    @Autowired SavingsAccountService savingsAccountService;
    @Autowired SavingService savingService;

    private final Logger logger = LoggerFactory.getLogger(SavingTServiceImpl.class);

    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE)
    public SavingTDto mobileSaving(double amount) {
        String user =myUserDetailsService.currentUser();
        Wallet wallet =walletService.getWallet("WAL"+user);
        SavingsAccount savingsAccount =savingsAccountService.getSavingsAccount("SAV"+user);
        if(amount>wallet.getAmount()){
            throw new InvalidValuesException("low wallet bal");
        }else{
            logger.info("preparing transaction...");
            SavingTDto savingTDto=new SavingTDto();
            savingTDto.setDate(new Date());
            savingTDto.setAmount(amount);
            savingTDto.setStatus(TransactionStatus.SUCCESS);
            savingTDto.setTransactionType(TransactionType.INTERNAL);
            savingTDto.setUserName(user);
            savingTDto.setAccountId(savingsAccount.getId());
            savingTDto.setCreationDateTime(new Date());
            savingTDto.setNarrative("Wallet savings ");
            savingTDto.setClassification("saving");
            savingTDto.setAccount(savingsAccount.getAccountRef());
            savingTDto.setWallet(wallet.getAccountRef());
            SavingTDto response =saveSavingT(savingTDto).getBody();
            assert response != null;
            if(response.getStatus().equals(TransactionStatus.SUCCESS)){
                UserAccount userAccount =new UserAccount();
                userAccount.setLastTransaction(response.getId());
                logger.info(("updating wallet..."));
                userAccount.setAmount(amount*-1);
                userAccount.setAccountRef(wallet.getAccountRef());
                walletService.transact(userAccount);

                logger.info(("updating user savingsAccount..."));
                userAccount.setAccountRef("SAV"+user);
                userAccount.setAmount(amount);
                savingsAccountService.transact(userAccount);

                logger.info(("updating sacco saving account..."));
                savingService.updateSaving(amount);
            }
            return response;

        }

    }

    @Override
    public SavingTDto withdrawRequest(double amount) {
        String user =myUserDetailsService.currentUser();
        Wallet wallet =walletService.getWallet("WAL"+user);
        SavingsAccount savingsAccount =savingsAccountService.getSavingsAccount("SAV"+user);
        if(amount>savingsAccount.getAmount()){
            throw new InvalidValuesException("low savings bal");
        }else{
            logger.info("preparing transaction...");
            SavingTDto savingTDto=new SavingTDto();
            savingTDto.setDate(new Date());
            savingTDto.setAmount(amount*-1);
            savingTDto.setStatus(TransactionStatus.PENDING);
            savingTDto.setTransactionType(TransactionType.INTERNAL);
            savingTDto.setUserName(user);
            savingTDto.setAccountId(savingsAccount.getId());
            savingTDto.setCreationDateTime(new Date());
            savingTDto.setNarrative("withdraw request");
            savingTDto.setAccount(savingsAccount.getAccountRef());
            savingTDto.setWallet(wallet.getAccountRef());
            SavingTDto response =saveSavingT(savingTDto).getBody();
            savingTDto.setClassification("saving");
            assert response != null;
            if(response.getStatus().equals(TransactionStatus.PENDING)){
                UserAccount userAccount =new UserAccount();
                userAccount.setLastTransaction(response.getId());
                logger.info(("updating user savingsAccount..."));
                userAccount.setAccountRef("SAV"+user);
                userAccount.setAmount(amount*-1);
                savingsAccountService.transact(userAccount);

            }
            return response;
        }

    }

    @Override
    public SavingTDto saveSavingTransaction(SavingTDto savingTDto) {
        return saveSavingT(savingTDto).getBody();
    }

    private ResponseEntity<SavingTDto> saveSavingT(SavingTDto savingTDto) {
        logger.info("transacting...");
        try {
            logger.info("connecting to payment service...");
            final String uri="http://localhost:8082/transaction/saving/save";
            HttpEntity<SavingTDto> request = new HttpEntity<>(savingTDto);
            return restTemplate.exchange(uri, HttpMethod.POST,request,SavingTDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }


    @Override
    public SavingsTransactionsDto myAll() {
        String user =myUserDetailsService.currentUser();
        String wallet ="WAL"+user;
        return allByWallet(wallet);

    }

    @Override
    @Transactional
    public List<CumulativeSavingT> myAllCumulative() {
        //SavingsTransactionsDto savingsTransactionsDto =new SavingsTransactionsDto();
        List<SavingTDto> savingTDtoList=myAll().getSavingTransactions();
        savingTDtoList.sort(Comparator.comparing(SavingTDto::getId));
        List<CumulativeSavingT> myList =new ArrayList<>();
        double amount =0;
        for(SavingTDto savingTDto: savingTDtoList){
            amount+=savingTDto.getAmount();
            CumulativeSavingT cumulativeSavingT =new CumulativeSavingT();
            cumulativeSavingT.setAccount(savingTDto.getAccount());
            cumulativeSavingT.setAmount(savingTDto.getAmount());
            cumulativeSavingT.setCreationDateTime(savingTDto.getCreationDateTime());
            cumulativeSavingT.setNarrative(savingTDto.getNarrative());
            cumulativeSavingT.setCumulativeAmount(amount);
            myList.add(cumulativeSavingT);
        }

       return myList;
    }

    @Override
    public SavingsTransactionsDto myRecent() {
        String user =myUserDetailsService.currentUser();
        String wallet ="WAL"+user;
        return last5ByWallet(wallet);
    }

    @Override
    public SavingsTransactionsDto allByWallet(String wallet) {
        String url ="http://localhost:8082/transaction/saving/allByWallet/";
        try {
            return restTemplate.getForObject(
                    url + wallet, SavingsTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public SavingsTransactionsDto last5ByWallet(String wallet) {
        String url ="http://localhost:8082/transaction/saving/recentByWallet/";
        try {
            return restTemplate.getForObject(
                    url + wallet, SavingsTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }





    @Transactional
    @Override
    public SavingTDto systemSaving(SavingTDto savingTDto) {
        logger.info("updating saving...");
        accountService.updateSaving(savingTDto.getAmount(), "savingTDto.getAcctFrom()");
        logger.info("saving transaction...");
        ResponseEntity<SavingTDto> response=saveSavingT(savingTDto);
        return response.getBody();
    }

    /*public void initialSaving(double amount, String userName) {
        if(amount>0) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();
            SavingTDto savingTDto = new SavingTDto();
            savingTDto.setUserName(user);
            savingTDto.setTransactionType(TransactionType.CHEQUE);
            savingTDto.setStatus(TransactionStatus.SUCCESS);
            savingTDto.setCategory(TransactionCat.SAVING);
            savingTDto.setAmount(amount);
            savingTDto.setAcctTo(appConfigReader.getSaccoAccount());
            savingTDto.setAcctFrom(userName);
            savingTDto.setSavingType(SavingType.DIRECT);
            savingTDto.setDate(new Date());
            saveSavingT(savingTDto);
        }

    }*/

    @Override
    public double totalSaving(Date date) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String myDate=jsn.format(date);
        final String uri="http://localhost:8082/transaction/saving/totalSaving/" + myDate;
        try {

          final Double total= restTemplate.getForObject(uri, Double.class);
            return total;
      }catch (RestClientException e) {
          throw new ResourceNotFoundException("Transaction Service down " );
      }

    }

    @Override
    public double totalSaving(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/saving/totalSaving/"+dateFrom+"/"+dateTo;
        try {

            final Double total= restTemplate.getForObject(uri, Double.class);
            return total;
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public DateTransactions dateSaving(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/saving/dateSaving/"+dateFrom+"/"+dateTo;
        try {

            DateTransactions dateTransactions= restTemplate.getForObject(uri, DateTransactions.class);
            return dateTransactions;
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }

    @Override
    public SavingsTransactionsDto savingTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/saving/savingTransactions/"+user, SavingsTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }


}
