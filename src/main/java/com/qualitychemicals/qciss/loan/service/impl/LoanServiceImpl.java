package com.qualitychemicals.qciss.loan.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.firebase.notification.NotificationService;
import com.qualitychemicals.qciss.firebase.notification.Subject;
import com.qualitychemicals.qciss.loan.converter.LoanConverter;
import com.qualitychemicals.qciss.loan.dao.LoanDao;
import com.qualitychemicals.qciss.loan.dto.*;
import com.qualitychemicals.qciss.loan.model.*;
import com.qualitychemicals.qciss.loan.service.LoanService;
import com.qualitychemicals.qciss.profile.converter.UserConverter;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.saccoData.service.LoanAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl  implements LoanService {
    @Autowired LoanConverter loanConverter;
    @Autowired UserConverter userConverter;
    @Autowired LoanDao loanDao;
    @Autowired UserService userService;
    @Autowired
    NotificationService notificationService;
    @Autowired LoanAccountService loanAccountService;
    private final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE)
    public Loan request(LoanRequestDto requestDto) {
        LoanDto loanDto=requestToLoanDto(requestDto);
        logger.info("Checking Eligibility...");
        boolean bool= isEligible(loanDto);
        if(bool){
            logger.info("Eligible...");
            logger.info("checking current loans...");
            List<Loan> loans=loanDao.findByBorrowerAndStatus(loanDto.getBorrower(), LoanStatus.OPEN);
            List<Integer> productIds=new ArrayList<>();
            for (Loan loan:loans){
                productIds.add(loan.getProduct().getId());
            }
            if(loans.size()<3){
                if(productIds.contains(loanDto.getProductId())){
                    logger.info("have a running loan of this type...");
                    throw new InvalidValuesException("you have an outstanding loan for the product");
                }
                logger.info("processing loan...");
                logger.info("sending notification loan...");
                String text="New loan  Request ";
                notificationService.sendNotification("ADMIN", text, Subject.LoanRequest);
                return processLoan(loanDto);
            }
            logger.error("max loans reached...");
            throw new InvalidValuesException("you can only run a maximum of three loans");
        }
        logger.error("not Eligible...");
        throw new InvalidValuesException("account does not qualify for a loan");

    }

    private LoanDto requestToLoanDto(LoanRequestDto requestDto){
        logger.info("converting loan request to dto...");
        LoanDto loanDto=new LoanDto();
        loanDto.setPrincipal(requestDto.getPrincipal());
        loanDto.setDuration(requestDto.getDuration());
        loanDto.setDisbursedBy(requestDto.getDisbursedBy());
        loanDto.setRepaymentCycle(requestDto.getRepaymentCycle());
        loanDto.setRepaymentMode(requestDto.getRepaymentMode());
        loanDto.setFirstRepaymentDate(requestDto.getFirstRepaymentDate());
        loanDto.setHandlingMode(requestDto.getHandlingMode());
        loanDto.setTopUpMode(requestDto.getTopUpMode());
        loanDto.setProductId(requestDto.getProductId());
        loanDto.setSecurityDto(requestDto.getSecurityDto());
        return loanDto;
    }
    private boolean isEligible(LoanDto loanDto){
        logger.info("getting user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        logger.info("checking user...");
        boolean bool = userService.isUserOpen(userName);
        if (bool) {
            logger.info("pass... User Open...");
            loanDto.setBorrower(userName);
            List<LoanStatus> loanStatuses=new ArrayList<>();
            loanStatuses.add(LoanStatus.PENDING);
            loanStatuses.add(LoanStatus.CHECKED);
            loanStatuses.add(LoanStatus.APPROVED);
            logger.info("Checking current request...");
            List<Loan> loans=loanDao.findByStatusInAndBorrower(loanStatuses, userName);
            if(loans.isEmpty()){
                logger.info("no current requests...");
                return true;
            }else{
                logger.error("pending request exists...");
                throw new ResourceNotFoundException("Profile Does not qualify for a loan(pending request) " + userName);
            }

        }else {
            logger.error("user not open...");
            throw new ResourceNotFoundException("Profile Does not qualify for a loan  " + userName);
        }
    }

    private String loanNumber(){
        int count=loanAccountService.getCount();
        Random random = new Random();
        String rand = String.format("%04d", random.nextInt(100));
        return "QC"+count+""+rand;
    }

    private Loan processLoan(LoanDto loanDto) {
        logger.info("processing loan...");
        Loan loan = loanConverter.dtoToEntity(loanDto);
        double principal = loan.getPrincipal();
        int duration = loan.getDuration();

        double minAmount = loan.getProduct().getMinAmount();
        double maxAmount = loan.getProduct().getMaxAmount();
        int minDuration = loan.getProduct().getMinDuration();
        int maxDuration = loan.getProduct().getMaxDuration();

        if (principal >= minAmount && principal <= maxAmount && duration >= minDuration && duration <= maxDuration) {
            logger.info("getting profile...");

            boolean bool=checkCredits(loan);
            if(bool){
            FeeDto fees = fees(loanDto);
                logger.info("setting loan fees...");
            loan.setHandlingCharge(fees.getHandlingCharge());
            loan.setInterest(fees.getInterest());
            loan.setPenalty(0);
            loan.setTotalDue(loanDto.getPrincipal()+fees.getInterest());
            loan.setInsuranceFee(fees.getInsuranceFee());
            if(loan.getHandlingMode()==HandlingMode.EXPRESS){loan.setExpressHandling(fees.getExpressHandling());}
            if(loan.getTopUpMode()==HandlingMode.EXPRESS){loan.setEarlyTopUpCharge(fees.getEarlyTopUpCharge());}

            loan.setStatus(LoanStatus.PENDING);
            loan.setRepayments(null);
            loan.setLoanNumber(loanNumber());
            loan.setPreparedBy(null);
            loan.setApprovedBy(null);
            loan.setApplicationDate(new Date());
            logger.info("submitting loan...");
            return loanDao.save(loan);
            }else{
                logger.error("Less credits...");
                throw new InvalidValuesException(
                        "you dont have enough credits ");
            }


        } else {
            logger.error("invalid values...");
            if (!(principal >= minAmount) || !(principal <= maxAmount)) {
                logger.error("invalid Principal...");
                throw new InvalidValuesException("Principal should be between " + minAmount + " and " + maxAmount);
            } else {
                logger.error("invalid Duration...");
                throw new InvalidValuesException(
                        "Duration should be between " + minDuration + "Months and " + maxDuration + "Months");
            }
        }

    }

    private boolean checkCredits(Loan loan) {
        logger.info("getting account...");
        Account account=userService.getAccount(loan.getBorrower());
        double saving=account.getSavings();
        double maximum=loan.getProduct().getMaximum();
        double principal=loan.getPrincipal();
        if(principal<=(maximum*saving)){
            logger.info("valid principal amount...");
            return true;
        }else{
            logger.error("Less credits...");
            throw new InvalidValuesException(
                    "you qualify for a maximum of: "+(maximum*saving)); }
    }

    @Override
    @Transactional
    public FeeDto fees(LoanDto loanDto) {
        FeeDto fees=new FeeDto();
        logger.info("converting...");
        Loan loan=loanConverter.dtoToEntity(loanDto);
        logger.info("calculating...");

        Product product=loan.getProduct();
        double rate=product.getInterest();
        double penaltyRate=product.getPenalty();
        double handlingChargeRate=product.getHandlingCharge();
        double insuranceRate=product.getInsuranceRate();
        double earlyTopUp=product.getEarlyTopUp();
        double expressHandling=product.getExpressHandling();

        double principal=loan.getPrincipal();
        int duration=loan.getDuration();
        fees.setInterest(rate*0.01*principal*duration);
        fees.setPossiblePenalty(penaltyRate*0.01*principal);
        fees.setHandlingCharge(handlingChargeRate*0.01*principal);
        fees.setInsuranceFee(insuranceRate*0.01*principal);
        fees.setEarlyTopUpCharge(earlyTopUp);
        fees.setExpressHandling(expressHandling);
        logger.info("success...");
        return fees;

    }

    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE)
    public Loan verify(LoanVerifyDto loanVerifyDto) {
        Date date = new Date();
        logger.info("getting admin user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting loan...");
        Loan loan=getLoan(loanVerifyDto.getId());
        logger.info("checking loan...");
        if(loan.getStatus()== LoanStatus.PENDING){
            logger.info("updating loan...");
        loan.setReleaseDate(date);
        loan.setTransferCharge(loanVerifyDto.getTransferCharge());
        loan.setStatus(LoanStatus.CHECKED);
        loan.setTotalDue(loan.getPrincipal()+loan.getInterest());
        loan.setTotalPaid(0);
        loan.setPreparedBy(userName);
        loan.setRemarks(loanVerifyDto.getRemarks());
            logger.info("verifying loan...");
        return loanDao.save(loan);
        }else{
            logger.error("invalid loan...");
            throw new InvalidValuesException("Only PENDING loans can be verified");
        }
    }
    public Loan approve(int id) {
        Date date = new Date();
        logger.info("getting admin user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting loan...");
        Loan loan=getLoan(id);
        logger.info("checking admin...");
        if(loan.getPreparedBy().equals(userName)){
            logger.error("invalid admin...");
            throw new InvalidValuesException("same admin cannot verify and approve");
        }else{
            logger.info("valid admin...");
            logger.info("checking loan...");
        if(loan.getStatus()== LoanStatus.CHECKED){
            logger.info("updating loan ...");
            loan.setReleaseDate(date);
            loan.setStatus(LoanStatus.APPROVED);
            int duration=loan.getDuration();
            double amount =loan.getTotalDue();
            double principal=loan.getPrincipal();
            Date firstDate=loan.getFirstRepaymentDate();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(firstDate);
            Date releaseDate=loan.getReleaseDate();
            Calendar cal2=Calendar.getInstance();
            cal2.setTime(releaseDate);
            cal2.add(Calendar.MONTH, duration);
            logger.info("setting loan repayments ...");
            loan.setRepayments(repaymentGen(loan.getRepaymentCycle(),cal1,cal2,amount, principal));
            loan.setApprovedBy(userName);
            logger.info("approving loan and sending email...");
            notificationService.sendNotification(loan.getBorrower(), "your Loan has been Approved "+loan.getReleaseDate(),Subject.LoanApprove);
            return loanDao.save(loan);
        }else{
            logger.error("invalid loan ...");
            throw new InvalidValuesException("Only CHECKED loans can be approved");
        }}
    }

    private List<Repayment> repaymentGen(
            Cycle cycle, Calendar firstDate, Calendar lastDate, double totalDue, double principal) {
        List<Repayment> repayments=new ArrayList<>();
        List<Date> dates = new ArrayList<>();
        double percentagePrincipal = Math.round((principal/totalDue) * 10000D) / 10000D;
        double percentageInt=1-percentagePrincipal;
        logger.info("checking cycle ...");
        if(cycle==Cycle.MONTHLY){
            logger.info("setting monthly repayment dates...");
            for(; firstDate.before(lastDate); firstDate.add(Calendar.MONTH,1)){
                dates.add(firstDate.getTime());
            }

        }else if(cycle==Cycle.WEEKLY){
            logger.info("setting weekly repayment dates...");
            for(; firstDate.before(lastDate); firstDate.add(Calendar.WEEK_OF_MONTH,1)){
                dates.add(firstDate.getTime());
            }

        }else if(cycle==Cycle.BIWEEKLY){
            logger.info("setting bi-weekly repayment dates...");
            for(; firstDate.before(lastDate); firstDate.add(Calendar.WEEK_OF_MONTH,2)){
                dates.add(firstDate.getTime());
            }

        }else if(cycle==Cycle.DAILY){
            logger.info("setting daily repayment dates...");
            for(; firstDate.before(lastDate); firstDate.add(Calendar.DAY_OF_MONTH,1)){
                dates.add(firstDate.getTime());
            }

        }else{
            logger.info("setting yearly cycle...");
            for(; firstDate.before(lastDate); firstDate.add(Calendar.YEAR,1)){
                dates.add(firstDate.getTime());
            }
        }
        logger.info("setting amount per repayment...");
        double amount = Math.round(totalDue / dates.size());
        for(Date date:dates){
            if(dates.indexOf(date)==(dates.size()-1)){
                amount=totalDue-amount*(dates.size()-1);
            }
            double principalDue=amount*percentagePrincipal;
            double interestDue=amount*percentageInt;

                repayments.add(new Repayment(2, date, amount, 0, amount,
                        principalDue,interestDue,0,0, RepaymentStatus.PENDING));

        }
        return repayments;
    }

    @Override
    @Transactional
    public void updateStatus(int id, LoanStatus loanStatus) {
        logger.info("getting loan");
        Loan loan=getLoan(id);
        logger.info("setting loan status");
        loan.setStatus(loanStatus);
        logger.info("updating...");
        loanDao.save(loan);
    }

    @Transactional(isolation= Isolation.SERIALIZABLE)
    @Override
    public Loan topUpRequest(LoanRequestDto loanRequestDto, int loanId) {
        logger.info("converting...");
        LoanDto loanDto=requestToLoanDto(loanRequestDto);
        logger.info("getting user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        logger.info("getting top up loan...");
        Loan topUpLoan = getLoan(loanId);
        logger.info("checking top up loan...");
        if (topUpLoan.getStatus()==LoanStatus.OPEN){

            double loanAmount = topUpLoan.getPrincipal() + topUpLoan.getInterest();
            double totalDue = topUpLoan.getTotalDue();
            logger.info("checking due amount...");
            if (totalDue>0 & (loanAmount > 2 * totalDue) & (topUpLoan.getBorrower().equals(userName))) {
                logger.info("checking Eligibility...");
                boolean bool = isEligible(loanDto);

                if (bool) {
                    logger.info("processing loan...");
                    Loan loan = processLoan(loanDto);
                    loan.setTopUpLoanId(loanId);
                    loan.setTopUpLoanBalance(totalDue);
                    logger.info("saving...");
                    return loanDao.save(loan);
                }
            }
        }
        logger.error("Loan does not qualify for top up ...");
        throw new InvalidValuesException("Loan does not qualify for top up: "+loanId);
    }

    @Override
    @Transactional
    public void repay(int loanId, double amount) {
        logger.info("getting Loan ...");
        Loan loan=getLoan(loanId);
        logger.info("updating Loan ...");
        loan.setTotalDue(loan.getTotalDue()-amount);
        loan.setTotalPaid(loan.getTotalPaid()+amount);
        List<Repayment> repayments=loan.getRepayments();
        double amt=amount;
        int i = 0;
        logger.info("updating Repayments...");
           for(Repayment repayment:repayments){
                if(amt>0 && repayment.getBalance()>0){
                    double value=amt-repayment.getBalance();
                    if(value>=0){
                        repayment.setPaid(repayment.getPaid()+repayment.getBalance());
                        repayment.setInterestPaid(repayment.getInterest());
                        repayment.setPrincipalPaid(repayment.getPrincipal());
                        amt=value;
                        repayment.setBalance(0);
                        repayment.setStatus(RepaymentStatus.SETTLED);
                    }else{
                        repayment.setPaid(repayment.getPaid()+amt);
                        repayment.setBalance(repayment.getBalance()-amt);
                        if((repayment.getPaid()-repayment.getPrincipal())>=0){
                            repayment.setPrincipalPaid(repayment.getPrincipal());
                        }else{
                            repayment.setPrincipalPaid(repayment.getPaid());
                        }
                        repayment.setInterestPaid(repayment.getPaid()-repayment.getPrincipalPaid());
                        amt=0;

                    }

                }
                repayments.set(i,repayment);
                i++;

            }
           loan.setRepayments(repayments);
        logger.info("saving Loan ...");
        loanDao.save(loan);

    }

    @Override
    @Transactional
    public Loan getLoan(int id) {
        return loanDao.findById(id).orElseThrow(()->new ResourceNotFoundException("No such loan ID: "+id));
    }

    @Override
    public List<Loan> getAll() {
        return loanDao.findAll();
    }

    @Override
    public List<Loan> findByStatus(LoanStatus status) {

        return loanDao.findByStatus(status);
    }

    @Override
    @Transactional
    public List<DueLoanDto> dueLoans(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        Date myDate= cal.getTime();
        logger.info("getting repayments due ...");
        List<DueLoanDto> dueLoans=loanDao.getDueLoans(myDate);
        return filterDueLoans(dueLoans);

    }

    @Override
    public List<DueLoanDto> dueLoans(Date date, String borrower) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        Date myDate= cal.getTime();
        logger.info("getting repayments due ...");
        List<DueLoanDto> dueLoans=loanDao.getDueLoans(myDate, borrower);
        return filterDueLoans(dueLoans);
    }


    @Override
    @Transactional
    public String deleteMyLoan(int loanId) {
        logger.info("getting user ...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting loan ...");
        Loan loan=getLoan(loanId);
        LoanStatus status=loan.getStatus();
        logger.info("checking loan ...");
        if((status==LoanStatus.PENDING || status==LoanStatus.CHECKED || status==LoanStatus.REJECTED
                ) & (loan.getBorrower().equals(userName))){
            logger.info("deleting loan ...");
            loanDao.delete(loan);
            return "success";

        }
        logger.error("loan cannot be deleted ...");
        throw new InvalidValuesException("loan cannot be deleted");
    }

    @Override
    @Transactional
    public List<Loan> myLoans() {
        logger.info("getting user ...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting loans ...");
        return loanDao.findByBorrower(userName);
    }

    @Override
    public List<DueLoanDto> myDueLoans(Date date) {
        logger.info("getting user ...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        return dueLoans(date,userName);
    }

    @Override
    public double myTotalDue() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        List<Loan> loans=loanDao.findByStatusAndBorrower(LoanStatus.OPEN, userName);
        double total =0;
        for(Loan loan:loans) total+=loan.getTotalDue();
        return total;
    }

    @Override
    public double totalDue() {
        List<Loan> loans=loanDao.findByStatus(LoanStatus.OPEN);
        double total =0;
        for(Loan loan:loans) total+=loan.getTotalDue();
        return total;
    }

    @Override
    public List<DueLoanDto> myOutstandingLoans() {
        logger.info("getting user ...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        return outstandingLoans(userName);
    }

    @Override
    public List<DueLoanDto> outstandingLoans() {
        logger.info("getting repayments due ...");
        List<DueLoanDto> dueLoans=loanDao.outstandingLoans();
        return filterDueLoans(dueLoans);
    }

    @Override
    public List<DueLoanDto> outstandingLoans(String borrower) {
        logger.info("getting repayments due ...");
        List<DueLoanDto> dueLoans=loanDao.outstandingLoans(borrower);
        return filterDueLoans(dueLoans);

    }

    private List<DueLoanDto> filterDueLoans(List<DueLoanDto> dueLoans){
        logger.info("filtering and merging...");
        return dueLoans.stream().collect(Collectors.collectingAndThen(
                Collectors.toMap(DueLoanDto::getLoanId, Function.identity(), (left, right) -> {
                    left.setDue(left.getDue()+right.getDue());
                    left.setLastDueDate(right.getLastDueDate());
                    return left;
                }), m -> new ArrayList<>(m.values())));
    }

    @Override
    @Transactional
    public Loan changeStatus(Loan ln, LoanStatus loanStatus) {
        logger.info("getting loan ...");
        Loan loan=getLoan(ln.getId());
        logger.info("setting status ...");
        loan.setStatus(loanStatus);
        return loanDao.save(loan);

    }

    @Override
    public List<Loan> getLoan(LoanStatus status) {
        return loanDao.findByStatus(status);
    }

    @Override
    public List<Loan> getLoan(String userName, LoanStatus status) {
        return loanDao.findByBorrowerAndStatus(userName, status);
    }

    @Override
    public AppraisalSheetDto getLoanAppraisal(int loanId) {
        logger.info("getting loan request");
        Loan loan=getLoan(loanId);
        logger.info("getting outstanding loans");
        List<DueLoanDto> outstandingLoans=outstandingLoans(loan.getBorrower());
        logger.info("getting user profile");
        Profile profile=userService.getProfile(loan.getBorrower());
        logger.info("converting...");
        LoanDto loanRequest=loanConverter.entityToDto(loan);
        UserDto userDto=userConverter.entityToDto(profile);
        return new AppraisalSheetDto(loanRequest,outstandingLoans,userDto);

    }

    @Override
    public Loan reject(LoanRejectDto loanRejectDto) {
        logger.info("getting user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        logger.info("getting loan...");
        Loan loan=getLoan(loanRejectDto.getId());
        if(loan.getStatus()== LoanStatus.PENDING || loan.getStatus()== LoanStatus.CHECKED){
            logger.info("rejecting loan");
            loan.setStatus(LoanStatus.REJECTED);
            loan.setPreparedBy(userName);
            notificationService.sendNotification(loan.getBorrower(), loanRejectDto.getReason(), Subject.LoanReject);
            return loanDao.save(loan);
        }
        throw new InvalidValuesException("Loan already approved");
    }

}
