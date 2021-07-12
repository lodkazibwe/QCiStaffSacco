package com.qualitychemicals.qciss.account.service.ServiceImpl;

import com.qualitychemicals.qciss.account.converter.WalletConverter;
import com.qualitychemicals.qciss.account.dao.WalletDao;
import com.qualitychemicals.qciss.account.dto.WalletDto;
import com.qualitychemicals.qciss.account.model.UserAccount;
import com.qualitychemicals.qciss.account.model.Wallet;
import com.qualitychemicals.qciss.account.service.UserAccountService;
import com.qualitychemicals.qciss.account.service.WalletService;
import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.saccoData.model.SaccoAccount;
import com.qualitychemicals.qciss.saccoData.service.SaccoAccountService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import com.qualitychemicals.qciss.transaction.dto.AllTransactions;
import com.qualitychemicals.qciss.transaction.dto.TransactionDto;
import com.qualitychemicals.qciss.transaction.dto.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired WalletDao walletDao;
    @Autowired WalletConverter walletConverter;
    @Autowired UserAccountService userAccountService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired SaccoAccountService saccoAccountService;

    private final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Override
    public Wallet addWallet(WalletDto walletDto) {
        boolean bool =userAccountService.existsByRef(walletDto.getAccountRef());
        if(bool){
            throw new InvalidValuesException("Wallet already Exists");
        }
        Wallet wallet =walletConverter.dtoToEntity(walletDto);
        return walletDao.save(wallet);
    }

    @Override
    public Wallet getMyWallet() {
        return null;
    }

    @Transactional
    @Override
    public String deposit(double amount) {
        String userName = myUserDetailsService.currentUser();
        logger.info("getting wallet...");
        Wallet wallet =getWallet("WAL"+userName);
        logger.info("generating transacting ...");
        TransactionDto transactionDto =new TransactionDto();
        transactionDto.setStatus(TransactionStatus.PENDING);
        transactionDto.setDate(new Date());
        transactionDto.setAmount(amount);
        logger.info("setting deposit contact  ...");
        transactionDto.setAccount(wallet.getContact());
        transactionDto.setCreationDateTime(new Date());
        transactionDto.setNarrative("deposit from "+wallet.getContact());
        transactionDto.setUserName(userName);
        transactionDto.setWallet(wallet.getAccountRef());
        transactionDto.setTransactionType("deposit");
        logger.info("transacting deposit...");
        try {
                   logger.info("connecting to payment service...");
                 final String uri="http://localhost:8082/yo/deposit";
              HttpEntity<TransactionDto> request = new HttpEntity<>(transactionDto);
            HttpEntity<String> message =restTemplate.exchange(uri, HttpMethod.POST,request,String.class);
               return message.getBody();
            }catch (RestClientException e) {
              throw new ResourceNotFoundException("Deposit request not submitted..." );
                   }

    }

    @Override
    @Transactional
    public Wallet refresh() {
        String userName = myUserDetailsService.currentUser();
        logger.info("getting wallet...");
        Wallet wallet =getWallet("WAL"+userName);
        logger.info("getting successful deposits...");
        List<TransactionDto> transactions= refreshTransactions(wallet.getAccountRef());
        for(TransactionDto transactionDto: transactions){
            logger.info("updating wallet...");
            UserAccount userAccount =new UserAccount();
            userAccount.setAccountRef(transactionDto.getWallet());
            userAccount.setLastTransaction(transactionDto.getId());
            userAccount.setAmount(transactionDto.getAmount()*-1);
            transact(userAccount);
            logger.info("update sacco account...");
            SaccoAccount saccoAccount =new SaccoAccount();
            saccoAccount.setAmount(transactionDto.getAmount()*-1);
            saccoAccount.setDescription("nnn");
            saccoAccount.setName("YO-ACCOUNT");
            saccoAccountService.updateAccount(saccoAccount);
        }

        return getWallet("WAL"+userName);
    }

    private List<TransactionDto> refreshTransactions(String walletRef){
        logger.info("refreshing...");
        try {
            logger.info("connecting to payment service...");
            final String uri="http://localhost:8082/yo/refresh/";
            return Objects.requireNonNull(restTemplate.getForObject(uri + walletRef, AllTransactions.class))
                    .getTransactions();
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("refresh failed..." );
        }

    }

    @Override
    public Wallet getWallet(String accountRef) {
        return walletDao.findByAccountRef(accountRef);
    }

    @Override
    public Wallet transact(UserAccount userAccount) {
        Wallet wallet =getWallet(userAccount.getAccountRef());
        if(wallet!=null){
            wallet.setAmount(userAccount.getAmount()+wallet.getAmount());
            wallet.setLastTransaction(userAccount.getLastTransaction());
            return walletDao.save(wallet);
        }
        throw new ResourceNotFoundException("No such wallet: "+ userAccount.getAccountRef());
    }

    @Override
    public List<Wallet> getAll() {
        return walletDao.findAll();
    }

    @Override
    public List<Wallet> getByAmountLess(double amount) {
        return walletDao.findByAmountLessThan(amount);
    }

    @Override
    public List<Wallet> getByAmountGreater(double amount) {
        return walletDao.findByAmountGreaterThan(amount);

    }

    @Override
    public Wallet updateWallet(WalletDto walletDto) {
        return null;
    }

}