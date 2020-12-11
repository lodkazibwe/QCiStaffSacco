package com.qualitychemicals.qciss.group.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.group.dto.GroupDto;
import com.qualitychemicals.qciss.group.dto.MemberDto;
import com.qualitychemicals.qciss.group.service.GroupService;
import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;

@Service
public class GroupServiceImpl  implements GroupService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired AccountService accountService;
    private final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Override
    public GroupDto addGroup(GroupDto groupDto) {
        //check if groupName is unique(rest)
        boolean bool=checkGroup(groupDto.getName());

        if(bool){
            //create account()
            AccountDto accountDto=new AccountDto();
            accountDto.setPosition("GROUP");
            //accountDto.setMemberNo();
            accountDto.setTotalShares(0);
            accountDto.setTotalSavings(0);
            accountDto.setPendingFee(0);
            Account account=accountService.createAccount(accountDto);
            groupDto.setAccountId(account.getId());
            try {
                logger.info("connecting to group service...");
                final String uri = "http://localhost:8083/group/add";
                HttpEntity<GroupDto> request = new HttpEntity<>(groupDto);
                ResponseEntity<GroupDto> response = restTemplate.exchange(uri, HttpMethod.POST, request, GroupDto.class);
                HttpStatus httpStatus = response.getStatusCode();
                if (httpStatus == HttpStatus.OK) {
                    return response.getBody();
                } else {
                    throw new InvalidValuesException("external application error " + httpStatus);
                }
            }catch (RestClientException e) {
                if (e.getCause() instanceof ConnectException) {

                    throw new ResourceNotFoundException("Group Service down:ConnectException");
                }
                throw new ResourceNotFoundException("external application down " );
            }
        }else{
            throw new ResourceNotFoundException("group name already used");
        }


    }

    private boolean checkGroup(String name) {
        return restTemplate.getForObject("http://localhost:8083/group/add/" + name, Boolean.class);


    }

    @Override
    public GroupDto getGroup(int groupId) {
        return null;
    }

    @Override
    public GroupDto updateGroup(GroupDto groupDto) {
        return null;
    }

    @Override
    public MemberDto addMember(MemberDto memberDto) {
        return null;
    }

    @Override
    public void removeMember(int uid) {

    }
}
