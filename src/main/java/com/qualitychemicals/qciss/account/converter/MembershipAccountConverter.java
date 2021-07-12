package com.qualitychemicals.qciss.account.converter;

import com.qualitychemicals.qciss.account.dto.MembershipAccountDto;
import com.qualitychemicals.qciss.account.model.MembershipAccount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MembershipAccountConverter {
    public MembershipAccountDto entityToDto(MembershipAccount membershipAccount){
        MembershipAccountDto membershipAccountDto =new MembershipAccountDto();
        membershipAccountDto.setId(membershipAccount.getId());
        membershipAccountDto.setName(membershipAccount.getName());
        membershipAccountDto.setAccountRef(membershipAccount.getAccountRef());
        membershipAccountDto.setAmount(membershipAccount.getAmount());
        membershipAccountDto.setBalance(membershipAccount.getBalance());
        membershipAccountDto.setLastTransaction(membershipAccount.getLastTransaction());
        membershipAccountDto.setSurplus(membershipAccount.getSurplus());
        membershipAccountDto.setUser(membershipAccount.getUserName());
        return membershipAccountDto;

    }

    public MembershipAccount dtoToEntity(MembershipAccountDto membershipAccountDto){
        MembershipAccount membershipAccount =new MembershipAccount();
        membershipAccount.setUserName(membershipAccountDto.getUser());
        membershipAccount.setName(membershipAccountDto.getName());
        membershipAccount.setSurplus(membershipAccountDto.getSurplus());
        membershipAccount.setBalance(membershipAccount.getBalance());
        membershipAccount.setAccountRef(membershipAccountDto.getAccountRef());
        membershipAccount.setAmount(membershipAccount.getAmount());
        membershipAccount.setLastTransaction(membershipAccount.getLastTransaction());
        return membershipAccount;
    }

    public List<MembershipAccountDto> entityToDto(List<MembershipAccount> membershipAccountList){
        return membershipAccountList.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<MembershipAccount> dtoToEntity(List<MembershipAccountDto> membershipAccountDtoList){
        return membershipAccountDtoList.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
