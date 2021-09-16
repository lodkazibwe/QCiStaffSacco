package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.account.model.UserAccount;
import com.qualitychemicals.qciss.account.model.Wallet;
import com.qualitychemicals.qciss.account.service.SharesAccountService;
import com.qualitychemicals.qciss.account.service.WalletService;
import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.saccoData.model.Share;
import com.qualitychemicals.qciss.saccoData.service.ShareService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import com.qualitychemicals.qciss.transaction.dto.*;
import com.qualitychemicals.qciss.transaction.service.ShareTService;
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
public class ShareTServiceImpl implements ShareTService {

    @Autowired
    TransactionService transactionService;
    @Autowired AccountService accountService;
    @Autowired RestTemplate restTemplate;
    @Autowired MyUserDetailsService myUserDetailsService;
    @Autowired WalletService walletService;
    @Autowired ShareService shareService;
    @Autowired SharesAccountService sharesAccountService;

    private final Logger logger = LoggerFactory.getLogger(ShareTServiceImpl.class);

    @Transactional(isolation= Isolation.SERIALIZABLE)
    @Override
    public ShareTDto buyShares(double amount) {
        String user =myUserDetailsService.currentUser();
        Wallet wallet =walletService.getWallet("WAL"+user);
        Share share =shareService.getShareInfo();
        if(amount>wallet.getAmount()){
            throw new InvalidValuesException("low wallet bal");
        }else{
            double shares =amount/(share.getShareValue());
            logger.info("preparing transaction...");
            ShareTDto shareTDto =new ShareTDto();
            shareTDto.setShares(shares);
            shareTDto.setUserName(user);
            shareTDto.setStatus(TransactionStatus.SUCCESS);
            shareTDto.setDate(new Date());
            shareTDto.setCreationDateTime(new Date());
            shareTDto.setAmount(amount);
            shareTDto.setTransactionType(TransactionType.INTERNAL);
            shareTDto.setShareValue(share.getShareValue());
            shareTDto.setAccount("SHA"+user);
            shareTDto.setNarrative("Bought shares");
            shareTDto.setClassification("share");
            shareTDto.setWallet(wallet.getAccountRef());
            ShareTDto response =saveShareT(shareTDto).getBody();
            assert response != null;
            if(response.getStatus().equals(TransactionStatus.SUCCESS)){
                UserAccount userAccount =new UserAccount();
                userAccount.setLastTransaction(response.getId());
                logger.info(("updating wallet..."));
                userAccount.setAmount(amount*-1);
                userAccount.setAccountRef(wallet.getAccountRef());
                walletService.transact(userAccount);

                logger.info(("updating shareAccount..."));
                userAccount.setAccountRef("SHA"+user);
                userAccount.setAmount(amount);
                sharesAccountService.transact(userAccount);

                logger.info(("updating sacco share account..."));
                Share update =new Share();
                update.setAmount(amount);
                update.setSharesSold(shares);
                update.setDayShares(shares);
                update.setDayShareAmount(amount);
                update.setSharesAvailable(shares*-1);
                update.setSharesSold(shares);
                shareService.updateShare(update);

            }
            return  response;

        }

    }

    @Override
    public ShareTDto saveShareTransaction(ShareTDto shareTDto) {
        return saveShareT(shareTDto).getBody();
    }

    private ResponseEntity<ShareTDto> saveShareT(ShareTDto shareTDto) {
        logger.info("transacting...");

        try {
            logger.info("connecting to payment service...");
            final String uri="http://localhost:8082/transaction/shares/save";
            HttpEntity<ShareTDto> request = new HttpEntity<>(shareTDto);
            return restTemplate.exchange(uri, HttpMethod.POST,request,ShareTDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }

    @Override
    public SharesTransactionsDto myAll() {
        String user =myUserDetailsService.currentUser();
        String wallet ="WAL"+user;
        return allByWallet(wallet);
    }

    @Transactional
    @Override
    public List<CumulativeShareT> myAllCumulative() {
        List<ShareTDto> shareTDtoList =myAll().getSharesTransactions();
        shareTDtoList.sort(Comparator.comparing(ShareTDto::getId));
        List<CumulativeShareT> myList =new ArrayList<>();
        double amount =0;
        double shares =0;
        for(ShareTDto shareTDto:shareTDtoList){
            amount+=shareTDto.getAmount();
            shares+=shareTDto.getShares();
            CumulativeShareT cumulativeShareT =new CumulativeShareT();
            cumulativeShareT.setAccount(shareTDto.getAccount());
            cumulativeShareT.setAmount(shareTDto.getAmount());
            cumulativeShareT.setCreationDateTime(shareTDto.getCreationDateTime());
            cumulativeShareT.setNarrative(shareTDto.getNarrative());
            cumulativeShareT.setShares(shareTDto.getShares());
            cumulativeShareT.setShareValue(shareTDto.getShareValue());
            cumulativeShareT.setCumulativeAmount(amount);
            cumulativeShareT.setCumulativeShares(shares);
            myList.add(cumulativeShareT);
        }
       return  myList;
    }

    @Override
    public SharesTransactionsDto myRecent() {
        String user =myUserDetailsService.currentUser();
        String wallet ="WAL"+user;
        return last5ByWallet(wallet);
    }

    @Override
    public SharesTransactionsDto allByWallet(String wallet) {
        String url ="http://localhost:8082/transaction/shares/allByWallet/";
        try {
            return restTemplate.getForObject(
                    url + wallet, SharesTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public SharesTransactionsDto last5ByWallet(String wallet) {
        String url ="http://localhost:8082/transaction/shares/recentByWallet/";
        try {
            return restTemplate.getForObject(
                    url + wallet, SharesTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Transactional
    @Override
    public ShareTDto systemShares(ShareTDto shareTDto) {
        logger.info("updating shares...");
        double shareCost=20000;
        accountService.updateShares(shareTDto.getAmount()/shareCost, "shareTDto.getAcctFrom()");
        logger.info("saving transaction...");
        ResponseEntity<ShareTDto> response=saveShareT(shareTDto);
        return response.getBody();
    }

    /*public void initialShares(double qtty, String userName) {
        if(qtty>0) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();
            double unitCost =20000;
            ShareTDto shareTDto = new ShareTDto();
            shareTDto.setUserName(user);
            shareTDto.setTransactionType(TransactionType.CHEQUE);
            shareTDto.setStatus(TransactionStatus.SUCCESS);
            shareTDto.setCategory(TransactionCat.MEMBERSHIP);
            shareTDto.setAmount(unitCost*qtty);
            shareTDto.setAcctTo(appConfigReader.getSaccoAccount());
            shareTDto.setAcctFrom(userName);
            shareTDto.setUnitCost(unitCost);
            shareTDto.setShares(qtty);
            shareTDto.setDate(new Date());
            saveShareT(shareTDto);
        }

    }*/

    @Override
    public SharesTransactionsDto shareTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/shares/shareTransactions/"+user, SharesTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public double totalShares(Date date) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String myDate=jsn.format(date);
        final String uri="http://localhost:8082/transaction/shares/totalShares/" + myDate;
        try {

            return restTemplate.getForObject(uri, Double.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public double totalShares(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/shares/totalShares/"+dateFrom+"/"+dateTo;
        try {

            return restTemplate.getForObject(uri, Double.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }

    @Override
    public DateTransactions dateShares(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/shares/dateShares/"+dateFrom+"/"+dateTo;
        try {

            return restTemplate.getForObject(uri, DateTransactions.class);

        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }
}
