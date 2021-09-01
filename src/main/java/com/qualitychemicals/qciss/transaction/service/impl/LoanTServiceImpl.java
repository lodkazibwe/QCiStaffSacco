package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.account.model.UserAccount;
import com.qualitychemicals.qciss.account.model.Wallet;
import com.qualitychemicals.qciss.account.service.WalletService;
import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.firebase.notification.NotificationService;
import com.qualitychemicals.qciss.firebase.notification.Subject;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.model.LoanStatus;
import com.qualitychemicals.qciss.loan.service.LoanService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.saccoData.model.LoanAccount;
import com.qualitychemicals.qciss.saccoData.service.LoanAccountService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import com.qualitychemicals.qciss.transaction.dto.*;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
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
import java.util.Date;

@Service
public class LoanTServiceImpl implements LoanTService {
    @Autowired LoanService loanService;
    @Autowired UserService userService;
    @Autowired
    TransactionService transactionService;
    @Autowired RestTemplate restTemplate;
    @Autowired NotificationService notificationService;
    @Autowired
    WalletService walletService;
    @Autowired LoanAccountService loanAccountService;

    @Autowired MyUserDetailsService myUserDetailsService;
    private final Logger logger = LoggerFactory.getLogger(LoanTServiceImpl.class);

    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE)
    public LoanTDto release(LoanPayDto loanPayDto) {
        LoanAccount acct=loanAccountService.getLoanAccount();
        if(acct.getAmount()<loanPayDto.getAmount()){
            throw new InvalidValuesException("low loan account bal");
        }

        logger.info("getting loan...");
        Loan loan=loanService.getLoan(loanPayDto.getLoanId());
        Wallet wallet =walletService.getWallet("WAL"+loan.getBorrower());
        logger.info("getting admin user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("checking loan...");
        if(loan.getStatus()== LoanStatus.APPROVED){
            int id=loan.getTopUpLoanId();
            if(!(id==0)){
                logger.info("getting top up loan...");
                Loan topUpLoan=loanService.getLoan(id);
                loan.setTopUpLoanBalance(topUpLoan.getTotalDue());
                LoanTDto loanTDto=new LoanTDto();
                loanTDto.setLoanId(topUpLoan.getId());
                //loanTDto.setLoanRef(topUpLoan.getLoanNumber());
                loanTDto.setAmount(topUpLoan.getTotalDue());
                loanTDto.setUserName(userName);
                loanTDto.setDate(new Date());
                loanTDto.setAccount(topUpLoan.getLoanNumber());
                loanTDto.setTransactionType("loan");
                loanTDto.setStatus(TransactionStatus.PENDING);
                loanTDto.setNarrative("loan topped Up");
                loanTDto.setWallet(wallet.getAccountRef());
                loanTDto.setCreationDateTime(new Date());
                logger.info("closing top up loan...");
                loanService.repay(topUpLoan.getId(), topUpLoan.getTotalDue());
                saveLoanT(loanTDto);
                logger.info("updating sacco loan account...");
                LoanAccount loanAccount =new LoanAccount();
                loanAccount.setAmount(topUpLoan.getTotalDue());
                double rate =topUpLoan.getPrincipal()/topUpLoan.getInterest();
                double interestIn=topUpLoan.getTotalDue()/rate;
                double principalIn=topUpLoan.getTotalDue()-interestIn;
                loanAccount.setInterestReceivable(interestIn*-1);
                loanAccount.setInterestIn(interestIn);
                loanAccount.setPrincipalIn(principalIn);
                loanAccount.setPrincipalOutstanding(principalIn*-1);
                loanAccountService.updateLoanAccount(loanAccount);
            }
            logger.info("setting transaction...");
            LoanTDto loanTDto=new LoanTDto();
            loanTDto.setLoanId(loanPayDto.getLoanId());
            loanTDto.setDate(new Date());
            loanTDto.setCreationDateTime(new Date());
            loanTDto.setStatus(TransactionStatus.PENDING);
            loanTDto.setWallet(wallet.getAccountRef());
            loanTDto.setNarrative("loan charges");
            loanTDto.setAccount(loan.getLoanNumber());
            loanTDto.setUserName(userName);
            //loanTDto.setLoanRef(loan.getLoanNumber());
            loanTDto.setTransactionType("loan");
            loanTDto.setLoanId(loan.getId());
            logger.info("setting transaction charges and adding transaction...");
            double totalCharge=getTotalCharge(loan);
            loanTDto.setAmount(totalCharge);
            saveLoanT(loanTDto);
            logger.info("setting loan amount and saving...");
            loanTDto.setNarrative("loan Release");
            loanTDto.setAmount(loan.getPrincipal()*-1);

        double amount=loan.getPrincipal()-totalCharge-loan.getTopUpLoanBalance();

            LoanTDto response=saveLoanT(loanTDto).getBody();
            assert response != null;

            if(response.getStatus().equals(TransactionStatus.SUCCESS)){
                logger.info("updating loan...");
                loanService.changeStatus(loan, LoanStatus.OPEN);
                logger.info("updating accounts...");
                UserAccount userAccount =new UserAccount();
                userAccount.setLastTransaction(response.getId());
                logger.info(("updating wallet..."));
                userAccount.setAmount(amount);
                userAccount.setAccountRef(wallet.getAccountRef());
                walletService.transact(userAccount);
                logger.info("updating sacco loan account...");
                LoanAccount loanAccount =new LoanAccount();
                loanAccount.setAmount(loan.getPrincipal()*-1);
                loanAccount.setTransferCharge(loan.getTransferCharge());
                loanAccount.setInsuranceFee(loan.getInsuranceFee());
                loanAccount.setHandlingCharge(loan.getHandlingCharge());
                loanAccount.setEarlyTopUpCharge(loan.getEarlyTopUpCharge());
                loanAccount.setExpressHandling(loan.getExpressHandling());
                loanAccount.setInterestReceivable(loan.getInterest());
                loanAccount.setPrincipalOutstanding(loan.getPrincipal());
                loanAccount.setPrincipalOut(loan.getPrincipal());
                loanAccountService.updateLoanAccount(loanAccount);
                String message="You have received your loan money, "+response.getAmount();
                notificationService.sendNotification(loan.getBorrower(),message,Subject.loanRelease);

            }
            return response;

        }
        else{
            logger.info("invalid loan...");
            throw new InvalidValuesException("Only approved LOAN can be released");
        }


    }

    @Override
    @Transactional
    public LoanTDto saveLoanTransaction(LoanTDto loanTDto) {
        return saveLoanT(loanTDto).getBody();
    }

    @Transactional
    public ResponseEntity<LoanTDto> saveLoanT(LoanTDto loanTDto) {
        logger.info("transacting...");

        try {
            logger.info("connecting to payment service...");
            final String uri="http://localhost:8082/transaction/loan/saveLoanT";
            HttpEntity<LoanTDto> request = new HttpEntity<>(loanTDto);
            return restTemplate.exchange(uri, HttpMethod.POST,request,LoanTDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }

    private double getTotalCharge(Loan loan) {
        logger.info("getting total charge...");
        return loan.getHandlingCharge()+loan.getInsuranceFee()
                +loan.getEarlyTopUpCharge()+loan.getExpressHandling()+loan.getPenalty();
    }


    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE)
    public LoanTDto walletRepay(LoanPayDto loanPayDto) {

        logger.info("getting user...");
        String userName =myUserDetailsService.currentUser();
        Wallet wallet =walletService.getWallet("WAL"+userName);
        logger.info("getting loan...");
        Loan loan=loanService.getLoan(loanPayDto.getLoanId());
        logger.info("checking loan...");
        if(loan.getStatus()==LoanStatus.OPEN || loan.getStatus()==LoanStatus.PASSED_MATURITY ){
            logger.info("checking user...");
        if(loan.getBorrower().equals(userName)){//***********************
            logger.info("comparing amounts...");
            if(loan.getTotalDue()<loanPayDto.getAmount() || loanPayDto.getAmount()>wallet.getAmount()){
                logger.error("invalid amount...");
                throw new InvalidValuesException("invalid Amount...");
            }

            logger.info("preparing transaction...");
            LoanTDto loanTDto=new LoanTDto();
            loanTDto.setLoanId(loan.getId());
            loanTDto.setLoanRef(loan.getLoanNumber());
            loanTDto.setAmount(loanPayDto.getAmount());
            loanTDto.setUserName(userName);
            loanTDto.setDate(new Date());
            loanTDto.setAccount(loan.getLoanNumber());
            loanTDto.setTransactionType("loan");
            loanTDto.setStatus(TransactionStatus.PENDING);
            loanTDto.setNarrative("loan Repayment");
            loanTDto.setWallet(wallet.getAccountRef());
            loanTDto.setCreationDateTime(new Date());

            logger.info("updating loan...");
            loanService.repay(loanPayDto.getLoanId(), loanPayDto.getAmount());
            logger.info("saving transaction...");
            LoanTDto response=saveLoanT(loanTDto).getBody();
            assert response != null;
            if(response.getStatus().equals(TransactionStatus.SUCCESS)){
                logger.info("updating accounts...");
                UserAccount userAccount =new UserAccount();
                userAccount.setLastTransaction(response.getId());
                logger.info(("updating wallet..."));
                userAccount.setAmount(loanPayDto.getAmount()*-1);
                userAccount.setAccountRef(wallet.getAccountRef());
                walletService.transact(userAccount);
                logger.info("updating sacco loan account...");
                LoanAccount loanAccount =new LoanAccount();
                loanAccount.setAmount(loanPayDto.getAmount());
                double rate =loan.getPrincipal()/loan.getInterest();
                double interestIn=loan.getTotalDue()/rate;
                double principalIn=loan.getTotalDue()-interestIn;
                loanAccount.setInterestReceivable(interestIn*-1);
                loanAccount.setPrincipalOutstanding(principalIn*-1);
                loanAccount.setInterestIn(interestIn);
                loanAccount.setPrincipalIn(principalIn);
                loanAccountService.updateLoanAccount(loanAccount);
            }
            return response;

        }else {
            logger.error("invalid profile or loan...");
            throw new InvalidValuesException("invalid profile or loan...");
        }}
        logger.error("cant repay Loan...");
        throw new InvalidValuesException(" cant repay Loan ..."+ loan.getId());
    }

    @Override
    public LoanTransactionsDto myAll(String loanRef) {
        String user =myUserDetailsService.currentUser();
        String wallet ="WAL"+user;

        String url ="http://localhost:8082/transaction/loan/allByWallet/";
        try {
            return restTemplate.getForObject(
                    url + wallet+"/"+ loanRef, LoanTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public LoanTransactionsDto myAll() {
        String user =myUserDetailsService.currentUser();
        String wallet ="WAL"+user;

        String url ="http://localhost:8082/transaction/loan/allByWallet/";
        try {
            return restTemplate.getForObject(
                    url + wallet, LoanTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public LoanTransactionsDto myRecent() {
        String user =myUserDetailsService.currentUser();
        String wallet ="WAL"+user;

        String url ="http://localhost:8082/transaction/loan/recentByWallet/";
        try {
            return restTemplate.getForObject(
                    url + wallet, LoanTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public LoanTransactionsDto adminAll(String loanRef) {
        String url ="http://localhost:8082/transaction/loan/allByLoanRef/";
        try {
            return restTemplate.getForObject(
                    url + loanRef, LoanTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }


    /* @Transactional
    public LoanTDto repay(LoanTDto loanTDto, String owner) {
        logger.info("getting admin user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting loan...");
        Loan loan=loanService.getLoan(loanTDto.getLoanId());
        logger.info("checking loan...");
        if(loan.getStatus()==LoanStatus.OPEN || loan.getStatus()==LoanStatus.PASSED_MATURITY ){
            logger.info("preparing loan transaction...");
       // LoanT loanT=loanTConverter.dtoToEntity(loanTDto);
        loanTDto.setDate(new Date());
            loanTDto.setUserName(userName);

            logger.info("getting borrower profile...");
        Profile profile =userService.getProfile(owner);
            logger.info("checking borrower and loan...");
        if(loan.getBorrower().equals(profile.getUserName())){
            logger.info("checking amount...");
            if(loan.getTotalDue()<loanTDto.getAmount()){
                logger.error("invalid Amount...");
                throw new InvalidValuesException("invalid Amount...");
            }
            logger.info("transacting...");
            LoanTDto response=saveLoanT(loanTDto).getBody();
            assert response != null;

            if(response.getStatus().equals(TransactionStatus.SUCCESS)){
                logger.info("updating loan...");
                loanService.repay(loanTDto.getLoanId(), loanTDto.getAmount());
                logger.info("updating wallet...");



            }return response;

        }else{
            logger.error("invalid Profile or Loan...");
            throw new InvalidValuesException("invalid Profile or Loan...");
        }}
        logger.error("invalid Loan...");
        throw new InvalidValuesException("invalid Loan...");
    }*/


    @Override
    public double totalRepay(Date date) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String myDate=jsn.format(date);
        final String uri="http://localhost:8082/transaction/loan/totalLoanPayments/" + myDate;
        try {

            return restTemplate.getForObject(uri, Double.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public double totalRepay(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/loan/totalLoanPayments/"+dateFrom+"/"+dateTo;
        try {

            return restTemplate.getForObject(uri, Double.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public DateTransactions dateRepay(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/loan/dateLoanPayments/"+dateFrom+"/"+dateTo;
        try {

            return restTemplate.getForObject(uri, DateTransactions.class);

        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    @Transactional
    public LoanTDto payPenalty(LoanTDto loanTDto) {
        return null;
    }

}

