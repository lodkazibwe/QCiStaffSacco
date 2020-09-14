package com.qualitychemicals.qciss.profile.DTO;

import com.qualitychemicals.qciss.profile.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private int id;
    @NotNull
    private User userDetail;
    @NotNull
    private Personal personalInfo;
    private Work workInfo;
    private List<Account> accounts;
    private Summary summary;

}
