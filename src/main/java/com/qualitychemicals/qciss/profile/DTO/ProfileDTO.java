package com.qualitychemicals.qciss.profile.DTO;

import com.qualitychemicals.qciss.profile.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProfileDTO {
    private int id;
    @NotNull
    @Valid
    private UserDTO userDetail;
    @NotNull(message = "invalid  details...")
    @Valid
    private PersonalDTO personalInfo;

}
