package com.qualitychemicals.qciss.group.rest.v1;

import com.qualitychemicals.qciss.group.dto.GroupDto;
import com.qualitychemicals.qciss.group.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupService groupService;

    @PostMapping("/add")
    public ResponseEntity<GroupDto> addGroup(@Valid @RequestBody GroupDto groupDto){
        return new ResponseEntity<>(groupService.addGroup(groupDto), HttpStatus.OK);
    }

    @PostMapping("/get/{groupId}")
    public ResponseEntity<GroupDto> getGroup(@PathVariable int groupId){
        return new ResponseEntity<>(groupService.getGroup(groupId), HttpStatus.OK);
    }



}
