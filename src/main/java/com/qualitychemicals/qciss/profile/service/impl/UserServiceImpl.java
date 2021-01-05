package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.firebase.message.ChatService;
import com.qualitychemicals.qciss.firebase.notification.Notification;
import com.qualitychemicals.qciss.firebase.notification.NotificationService;
import com.qualitychemicals.qciss.firebase.notification.NotificationStatus;
import com.qualitychemicals.qciss.firebase.notification.Subject;
import com.qualitychemicals.qciss.loan.dto.DueLoanDto;
import com.qualitychemicals.qciss.loan.model.RepaymentMode;
import com.qualitychemicals.qciss.loan.service.LoanService;
import com.qualitychemicals.qciss.profile.dao.UserDao;
import com.qualitychemicals.qciss.profile.dto.*;
import com.qualitychemicals.qciss.profile.converter.UserConverter;
import com.qualitychemicals.qciss.profile.model.*;
import com.qualitychemicals.qciss.profile.service.CompanyService;
import com.qualitychemicals.qciss.profile.service.EmailService;
import com.qualitychemicals.qciss.profile.service.PersonService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.service.MembershipTService;
import com.qualitychemicals.qciss.transaction.service.SavingTService;
import com.qualitychemicals.qciss.transaction.service.ShareTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDAO;
    @Autowired
    UserConverter userConverter;
    @Autowired BCryptPasswordEncoder passwordEncoder;
    private final Logger logger =LoggerFactory.getLogger(UserService.class);
    @Autowired
    CompanyService companyService;
    @Autowired EmailService emailService;
    @Autowired PersonService personService;
    @Autowired
    LoanService loanService;
    @Autowired NotificationService notificationService;
    @Autowired
    SavingTService savingTService;
    @Autowired MembershipTService membershipTService;
    @Autowired ShareTService shareTService;
    @Autowired ChatService chatService;





    @Transactional
    @Override
    public Profile addProfile(UserDto userDTO, String rol, Status status) {
        logger.info("checking profile...");
        String userName= userDTO.getPersonDto().getMobile();
        int  val=personService.userExists(userDTO.getPersonDto().getEmail(), userDTO.getPersonDto().getMobile());
        if(val==1){
            logger.error("Email already used...");
            throw new ResourceNotFoundException("Email already used... No.: "+userDTO.getPersonDto().getEmail());
        }else if(val==2){
            logger.error("Contact already used...");
            throw new ResourceNotFoundException("Contact already used... No.: "+userDTO.getPersonDto().getMobile());
        }
        else {
            logger.info("setting company...");
           String company= verifyCompany(userDTO.getWorkDto().getCompanyName());
           userDTO.getWorkDto().setCompanyName(company);
            logger.info("converting...");
            Profile profile = userConverter.dtoToEntity(userDTO);
            logger.info("profile validated...");
            String email=userDTO.getPersonDto().getEmail();

            logger.info("updating...");
            Set<Role> roles=getRoles(rol);
            profile.setRole(roles);
            profile.setUserName(userName);
            profile.setStatus(status);
            Random random = new Random();
            String rand = String.format("%04d", random.nextInt(10000));
            String pin ="QC-"+rand;
            profile.setPassword(passwordEncoder.encode(pin));

            String message="use this pin to login "+pin;
            profile.getPerson().setImage("default.png");
            //generate profile/accountNumber********************************************************
            logger.info("generating member number...");
            String memberNo=getMemberNo(profile.getId());
            profile.getAccount().setMemberNo(memberNo);
            logger.info("profile values updated...");
            logger.info("saving...");
            Profile savedProfile = userDAO.save(profile);
            logger.info("initial transactions...");
            double membership =20000-profile.getAccount().getPendingFee();
            savingTService.initialSaving(profile.getAccount().getSavings(), userName);
            membershipTService.initialMembership(membership, userName);
            shareTService.initialShares(profile.getAccount().getShares(), userName);
            logger.info("subscribing to chat and notifications...");
            chatService.createChat(userName);

            logger.info("sending email...");
            emailService.sendSimpleMessage(email,"PRIVATE-QCi-CODE",message);
            logger.info(message+" for  "+ userName);

            logger.info("profile created...");

            //initialTransactions(userDTO.getAccountDto(), userName);
            return savedProfile;

        }

    }



    private String getMemberNo(int id) {
        Random random = new Random();
        String rand = String.format("%04d", random.nextInt(100));
        return  "QCS/SS/"+rand+"/"+id;
    }

    @Override
    @Transactional
    public Profile signUp(PersonDto personDto) {
        logger.info("converting...");
        UserDto userDto=new UserDto();
        userDto.setPersonDto(personDto);
        WorkDto workDto=new WorkDto();
        AccountDto accountDto=new AccountDto();
        accountDto.setPendingFee(20000);
        accountDto.setTotalSavings(0);
        accountDto.setTotalShares(0);
        accountDto.setPosition("member");
        userDto.setAccountDto(accountDto);
        userDto.setWorkDto(workDto);
        logger.info("saving...");
        return addProfile(userDto,"USER", Status.PENDING);
    }

    private String verifyCompany(String companyName) {
        logger.info("checking company...");
       boolean bull=companyService.checkCompany(companyName);
        if(bull){
            logger.info("valid company...");
                return companyName;
        }else {
            logger.info("default company...");
            return "DEFAULT";
        }

    }



    @Override
    public Profile updateProfile(Profile profile) {
        return userDAO.save(profile);
    }

    @Override
    public String createPass(String pass) {
        logger.info("getting profile...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        return updatePass(userName, pass);
    }

    @Override
    @Transactional
    public String updatePass(String userName, String pass) {
        logger.info("updating password...");
        Profile profile =getProfile(userName);
        profile.setPassword(passwordEncoder.encode(pass));
        userDAO.save(profile);logger.info("updated...");
        return"success";

    }

    @Transactional
    @Override
    public Work getWorkInfo(int id) {
        Profile profile = userDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No Profile With ID: " +id));
        return profile.getWork();
    }

    @Override
    public Work getWorkInfo(String userName) {
        Profile profile = userDAO.findByUserName(userName);
        return profile.getWork();
    }

    @Override
    public Account getSummary(int id) {
        Profile profile = userDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No Profile With ID: " +id));
        return profile.getAccount();
    }



    @Override
    public String requestPin(String contact) {
        logger.info("generating pin...");
        Random random = new Random();
        String pin = String.format("%04d", random.nextInt(10000));
        logger.info("sending message...");
        logger.info(pin+" one time login pin QCiSS");
        String message=pin+" one time login pin QCiSS";
        logger.info(message+" for  "+ contact);
        Profile profile =getProfile(contact);
        String email= profile.getPerson().getEmail();
        emailService.sendSimpleMessage(email,"PRIVATE-QCi-CODE",message);
        return updatePass(contact, pin);

    }

    @Override
    @Transactional
    public String verifyAccount(String accountNumber, String userName) {
        /*Profile profile =getProfile(userName);
        String mobile= profile.getPerson().getMobile();

        if(accountNumber.equals(mobile)){
            return accountNumber;
        }
        throw new InvalidValuesException("invalid profile accountNumber: "+accountNumber);*/
        return null;

    }

    @Override
    public Profile getProfile(int id) {
        return userDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No Profile With ID: "+id));
    }

    @Override
    public Profile getProfile(String userName) {
        logger.info("getting profile....");
        Profile profile =userDAO.findByUserName(userName);
        if (profile ==null){
            logger.info("invalid user name....");
            throw new ResourceNotFoundException("No Profile With Name: " +userName);
        }
        logger.info("success  profile fetched....");
        return profile;

    }

    @Override
    public Profile verifyUser(int userId) {
        Profile profile =getProfile(userId);
        profile.setStatus(Status.OPEN);
        logger.info("sending notification...");
        Notification notification=new Notification();
        notification.setDate(new Date());
        notification.setSentTo(profile.getUserName());
        notification.setStatus(NotificationStatus.RECEIVED);
        notification.setSubject(Subject.AccountVerify);
        notificationService.sendNotification(notification);
        return userDAO.save(profile);
    }

    @Override
    public Profile closeUser(int userId) {
        Profile profile =getProfile(userId);
        profile.setStatus(Status.PENDING);
        return userDAO.save(profile);
    }

    @Override
    public Account getAccount(String userName) {
        Profile profile =getProfile(userName);
        return profile.getAccount();
    }

    @Override
    public List<Profile> getAll() {
        return userDAO.findAll();
    }

    @Override
    public boolean isUserOpen(String userName) {
        Profile profile =getProfile(userName);
        return profile.getStatus() == Status.OPEN;
    }

    @Override
    public boolean isUserClosed(String userName) {
        Profile profile =getProfile(userName);
        return profile.getStatus() == Status.CLOSED;
    }

    @Override
    public void deleteProfile(int id) {

    }
    private Set<Role> getRoles(String rol){
    Role role = new Role(rol);
        return new HashSet<>(Collections.singletonList(role));
    }

    @Override
    public List<EmployeeDto> getEmployees(String company) {
        return userDAO.getEmployees(company);
    }

    @Override
    @Transactional
    public List<DeductionScheduleDTO> deductionSchedule(String company) {
        List<DeductionScheduleDTO> deductionSchedules= new ArrayList<>();
        logger.info("getting date (next month 1st)...");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        Date date=cal.getTime();
        logger.info("getting company employees...");
        List<EmployeeDto> employees=getEmployees(company);
        logger.info("generating...");
        for(EmployeeDto employee:employees){
            DeductionScheduleDTO deductionSchedule=new DeductionScheduleDTO();
            List<DueLoanDto> dueLoans=loanService.dueLoans(date,employee.getMobile());
            List<DueLoanDto> dueLoanSalary=new ArrayList<>();
            deductionSchedule.setEmployee(employee);
            double total=employee.getPayrollSavings()+employee.getPayrollShares();
            for(DueLoanDto dueLoan:dueLoans){
                if(dueLoan.getRepaymentMode()== RepaymentMode.SALARY){

                    total+=dueLoan.getDue();
                    dueLoanSalary.add(dueLoan);
                }
            }
            deductionSchedule.setDueLoans(dueLoanSalary);
            deductionSchedule.setTotal(total);
            deductionSchedules.add(deductionSchedule);

        }

        return deductionSchedules;
    }




    public void addRole(String userName, String role) {
        Profile profile=getProfile(userName);
        profile.getRole().add(new Role("role"));
        updateProfile(profile);
    }

    @Override
    public List<Profile> getAllOpen() {
        return userDAO.findByStatus(Status.OPEN);
    }

    @Override
    public List<Profile> getAllPending() {
        return userDAO.findByStatus(Status.PENDING);
    }

    @Override
    public List<Profile> getAllClosed() {
        return userDAO.findByStatus(Status.CLOSED);
    }
}
