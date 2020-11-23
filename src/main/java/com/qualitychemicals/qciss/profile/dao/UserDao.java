package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.dto.EmployeeDto;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.model.Profile;
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

}
