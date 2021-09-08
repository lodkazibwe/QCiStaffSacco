package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.dto.EmployeeDto;
import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<Profile, Integer> {

    @Query("SELECT new com.qualitychemicals.qciss.profile.dto.EmployeeDto(" +
            "p.id,p.person.lastName,p.person.firstName,p.person.mobile,w.employeeId,w.payrollSaving,w.payrollShares)"+
            "FROM Profile p JOIN p.work w WHERE w.companyName=?1")
    List<EmployeeDto> getEmployees(String company);

    boolean existsByUserName(String userName);
    Profile findByUserName(String userName);

    List<Profile> findByStatus(Status status);

    @Query("SELECT new com.qualitychemicals.qciss.profile.dto.PersonDto(" +
            "p.id,p.person.firstName,p.person.lastName,p.person.nin,p.person.mobile,p.person.email,p.person.dob,p.person.gender,p.person.residence,p.person.passport)"+
            "FROM Profile p JOIN p.role r WHERE r.role=?1")
    List<PersonDto> getRoleUser(String role);

}
