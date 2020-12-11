package com.qualitychemicals.qciss.group.service;

import com.qualitychemicals.qciss.group.dto.GroupDto;
import com.qualitychemicals.qciss.group.dto.MemberDto;

public interface GroupService {
    GroupDto addGroup(GroupDto groupDto);
    GroupDto getGroup(int groupId);
    GroupDto updateGroup(GroupDto groupDto);
    MemberDto addMember(MemberDto memberDto);
    void removeMember(int uid);
}
