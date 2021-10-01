package com.qualitychemicals.qciss.transaction.service.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.service.LoanService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.saccoData.dto.DeductionScheduleDTO;
import com.qualitychemicals.qciss.saccoData.dto.ScheduleLoanDto;
import com.qualitychemicals.qciss.saccoData.service.DeductionScheduleService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import com.qualitychemicals.qciss.transaction.dto.*;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
import com.qualitychemicals.qciss.transaction.service.SavingTService;
import com.qualitychemicals.qciss.transaction.service.ShareTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired UserService userService;
    @Autowired LoanService loanService;
    @Autowired RestTemplate restTemplate;
    @Autowired
    LoanTService loanTService;
    @Autowired
    SavingTService savingTService;
    @Autowired ShareTService shareTService;
    @Autowired DeductionScheduleService deductionScheduleService;
    @Autowired
    MyUserDetailsService myUserDetailsService;

    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);


    @Override
    public AllTransactions myAll() {
        String user =myUserDetailsService.currentUser();
        String wallet ="WAL"+user;
        return allByWallet(wallet);
    }

    @Override
    public AllTransactions myRecent() {
        String user =myUserDetailsService.currentUser();
        String wallet ="WAL"+user;
        return last5ByWallet(wallet);
    }

    @Override
    public AllTransactions allTransactions(Date date1, Date date2) {
        String url ="http://localhost:8082/transaction/getAll/"+date1+"/"+date2;
        try {
            return restTemplate.getForObject(
                    url, AllTransactions.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public AllTransactions allByWallet(String wallet) {
        String url ="http://localhost:8082/transaction/allByWallet/";
        try {
            return restTemplate.getForObject(
                    url + wallet, AllTransactions.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public AllTransactions last5ByWallet(String wallet) {
        String url ="http://localhost:8082/transaction/recentByWallet/";
        try {
            return restTemplate.getForObject(
                    url + wallet, AllTransactions.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }




    /*****-------------------------------------------****/
    @Override
    @Transactional
    public LoanTransactionsDto loanTransactions(int loanId) {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/loanTransactions/" + loanId, LoanTransactionsDto.class);
        }catch (RestClientException e) {
                throw new ResourceNotFoundException("Transaction Service down " );
            }
    }

    @Override
    @Transactional
    public UserTransactionsDto userTransactions(String userName) {

        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/userTransactions/"+userName, UserTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }

    @Override
    public UserTransactionsDto myTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        return userTransactions(userName);
    }

    @Override
    public LoanTransactionsDto myLoanTransactions(int loanId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Loan loan=loanService.getLoan(loanId);
        if(userName.equals(loan.getBorrower())){
            return loanTransactions(loanId);
        }
        throw new InvalidValuesException("invalid loan Id " );
    }

    @Override
    public AllTransactions allTransactions() {

        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/getAll", AllTransactions.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public LoanTransactionsDto loanTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/loan/getAll/"+userName, LoanTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public SavingsTransactionsDto savingTransactions() {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/saving/getAll", SavingsTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public MembershipTransactionsDto membershipTransactions() {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/membership/getAll", MembershipTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public SharesTransactionsDto shareTransactions() {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/shares/getAll", SharesTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public AllTransactions allByType(TransactionType transactionType) {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/getAll/" +transactionType, AllTransactions.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    @Transactional
    public List<TransactionDto> scheduleRepayment(DeductionScheduleDTO deductionScheduleDTO) {
        logger.info("code to be edited....");
        //Profile profile=userService.getProfile(deductionScheduleDTO.getEmployee().getUid());
        List<TransactionDto> transactionDtos=new ArrayList<>();
        SavingTDto savingTDto=new SavingTDto();
        savingTDto.setDate(new Date());
        savingTDto.setAmount(deductionScheduleDTO.getEmployee().getPayrollSavings());
        savingTDto.setStatus(TransactionStatus.PENDING);
        savingTService.systemSaving(savingTDto);
        transactionDtos.add(savingTDto);
        //savingTDto.setUserName(userName);

        ShareTDto shareTDto=new ShareTDto();
        double amount=deductionScheduleDTO.getEmployee().getPayrollShares();
        shareTDto.setAmount(amount);
        shareTDto.setShares(amount/20000);
        shareTDto.setDate(new Date());
        shareTDto.setStatus(TransactionStatus.PENDING);
        shareTService.systemShares(shareTDto);
        transactionDtos.add(shareTDto);

        List<ScheduleLoanDto> scheduleLoans=deductionScheduleDTO.getDueLoans();
        for(ScheduleLoanDto scheduleLoan:scheduleLoans) {
            LoanTDto loanTDto = new LoanTDto();
            loanTDto.setDate(new Date());
            loanTDto.setLoanId(scheduleLoan.getLoanId());
            loanTDto.setAmount(scheduleLoan.getDue());
            //loanTService.repay(loanTDto);
            transactionDtos.add(loanTDto);
        }

        deductionScheduleService.settleSchedule(deductionScheduleDTO.getId());
        return transactionDtos;
    }

    @Override
    public List<TransactionDto> scheduleRepayment(List<DeductionScheduleDTO> deductionScheduleDTOs) {
        List<TransactionDto> transactionDtos=new ArrayList<>();
        for (DeductionScheduleDTO deductionScheduleDTO:deductionScheduleDTOs){
            List<TransactionDto> transactions =scheduleRepayment(deductionScheduleDTO);
            transactionDtos= Stream.concat(transactionDtos.stream(), transactions
            .stream()).collect(Collectors.toList());

        }
        return transactionDtos;
    }

    /*private ResponseEntity<TransactionDto> saveTransaction(TransactionDto transactionDto) {
        logger.info("transacting...");

            logger.info("connecting to payment service...");
            final String uri="http://localhost:8082/transaction/save";
            HttpEntity<TransactionDto> request = new HttpEntity<>(transactionDto);
            return restTemplate.exchange(uri, HttpMethod.POST,request,TransactionDto.class);

    }*/

    }
