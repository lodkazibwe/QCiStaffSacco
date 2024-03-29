package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.DayMembership;

import java.util.Date;
import java.util.List;

public interface DayMembershipService {


    DayMembership getDayMembership(Date date);
    List<DayMembership> getDayMemberships(Date dateFrom, Date dateTo);
}
